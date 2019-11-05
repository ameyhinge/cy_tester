package com.cy.tester.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.cy.tester.dtos.CommandDTO;
import com.cy.tester.dtos.LoggerDTO;
import com.cy.tester.interfaces.ILoggerCore;

public class LoggerCore implements ILoggerCore {

	// Hash Map to store all commands
	static HashMap<String, Character> COMMANDS = new HashMap<String, Character>();
	static {
		COMMANDS.put("/START FILE/", '0');
		COMMANDS.put("/END CASE/", '0');
	}

	@Override
	public void testApp(String directoryPath, String className, String methodName) {
		pickFiles(directoryPath);
	}

	// Private Functions
	private void pickFiles(String directoryPath) {
		try {
			Files.newDirectoryStream(Paths.get(directoryPath), path -> path.toFile().isFile())
					.forEach(path -> processFile(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processFile(Path path) {
		try {
			ArrayList<String> fileLines = new ArrayList<String>();
			if (path.toString().endsWith(".txt")) {
				// Pass the values in arraylist
				Files.lines(path).forEach(line -> {
					fileLines.add(line);
				});
			}

			// Variables
			boolean fileStarted = false;
			String className = null;
			String methodName = null;

			int testCaseNo = 0;
			ArrayList<LoggerDTO> ldArray = new ArrayList<LoggerDTO>();
			ArrayList<String> testCaseInput = new ArrayList<String>();

			for (int i = 0; i < fileLines.size(); i++) {
				if (fileStarted == false && fileLines.get(i).equals("/START FILE/")) {
					fileStarted = true;
					System.out.println("start found at: " + i);
				}
				// Read the file data
				else {
					// Check if line is a variable
					if (fileLines.get(i).startsWith("/VAR")) {
						MethodCore mc = new MethodCore();
						String[] proAtt = mc.processVariable(fileLines.get(i));
						// Check variable validity
						if (proAtt[0].equals("CLASS")) {
							className = proAtt[1];
							System.out.println("Class found at: " + i);
						} else if (proAtt[0].equals("METHOD")) {
							methodName = proAtt[1];
							System.out.println("Method found at: " + i);
						}
					}
					// Variable invalid, process as data
					else {
						if (fileStarted == false) {
							continue;
						} else {
							System.out.println("data started at: " + i);
							if (COMMANDS.containsKey(fileLines.get(i).trim())) {
								CommandDTO cd = new CommandDTO();
								cd = processCommands(fileLines.get(i));
								// Prepare object for single test case
								if (cd.getEndCaseFlag() == true) {
									testCaseNo++;
									// System.out.println("Test number: " + testCaseNo);
									LoggerDTO ld = new LoggerDTO();
									ld.setTestCaseNo(testCaseNo);

									// Convert to array
									String[] testCaseInputArr = new String[testCaseInput.size()];
									for (int s = 0; s < testCaseInput.size(); s++) {
										testCaseInputArr[s] = testCaseInput.get(s);
										System.out.println("test case: " + testCaseInputArr[s]);
									}
									ld.setTestCaseInput(testCaseInputArr);
									testCaseInput.clear();

									// Add object to the test case array
									ldArray.add(ld);
								} else {
									testCaseInput.add(fileLines.get(i));
								}
							} else {
								testCaseInput.add(fileLines.get(i));
							}
						}
					}
				}
			}
			MethodCore mc = new MethodCore();
			mc.parameterList(className, methodName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CommandDTO processCommands(String command) {
		CommandDTO cd = new CommandDTO();
		if (command.toUpperCase().equals("/END CASE/")) {
			cd.setEndCaseFlag(true);
		}
		return cd;
	}

}
