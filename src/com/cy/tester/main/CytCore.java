package com.cy.tester.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.cy.tester.dtos.CommandDTO;
import com.cy.tester.dtos.MainDTO;
import com.cy.tester.interfaces.ICytCore;

public class CytCore implements ICytCore {

	// Hash Map to store all commands
	static HashMap<String, Character> COMMANDS = new HashMap<String, Character>();
	static {
		COMMANDS.put("/START FILE/", '0');
		COMMANDS.put("/END CASE/", '0');
		COMMANDS.put("/OUTPUT/", '0');
	}

	@Override
	public void testApp(String directoryPath) {
		pickFiles(directoryPath);
	}

	// Private Functions
	private void pickFiles(String directoryPath) {
		try {
			Files.newDirectoryStream(Paths.get(directoryPath), path -> path.toFile().isFile())
					.forEach(path -> validFiles(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validFiles(Path path) {
		ArrayList<String> fileLines = new ArrayList<String>();
		if (path.toString().endsWith(".txt")) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(path.toString()));
				String tem = null;
				while ((tem = br.readLine()) != null) {
					fileLines.add(tem);
				}
				br.close();

				processFile(fileLines, path);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			return;
		}
	}

	private void processFile(ArrayList<String> fileLines, Path path) {

		try {
			// Variables
			boolean fileStarted = false;
			boolean resultStarted = false;
			String className = null;
			String methodName = null;

			int testCaseNo = 0;
			ArrayList<MainDTO> mainArray = new ArrayList<MainDTO>();
			ArrayList<String> testCaseInput = new ArrayList<String>();
			ArrayList<String> testCaseOutput = new ArrayList<String>();

			for (int i = 0; i < fileLines.size(); i++) {
				if (fileStarted == false && fileLines.get(i).equals("/START FILE/")) {
					fileStarted = true;
					System.out.println("Start found at: " + i);
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
							System.out.println("Class found at: " + i + ". Class Name: " + className);
						} else if (proAtt[0].equals("METHOD")) {
							methodName = proAtt[1];
							System.out.println("Method found at: " + i + ". Method Name: " + methodName);
						}
					}
					// Variable invalid, process as data
					else {
						if (fileStarted == false) {
							continue;
						} else {
							if (COMMANDS.containsKey(fileLines.get(i).trim())) {
								CommandDTO cd = new CommandDTO();
								cd = processCommands(fileLines.get(i));
								// Prepare object for single test case
								if (cd.getEndCaseFlag() == true) {
									testCaseNo++;
									// System.out.println("Test number: " + testCaseNo);
									MainDTO mn = new MainDTO();
									mn.setTestCaseNo(testCaseNo);
									mn.setTestCaseRow(i - testCaseInput.size() - testCaseOutput.size());

									// Convert input to array
									String[] testCaseInputArr = new String[testCaseInput.size()];
									for (int s = 0; s < testCaseInput.size(); s++) {
										testCaseInputArr[s] = testCaseInput.get(s);
										// System.out.println("test case input: " + testCaseInputArr[s]);
									}
									mn.setTestCaseInput(testCaseInputArr);
									testCaseInput.clear();

									// Convert expected output to array
									String[] testCaseOutputArr = new String[testCaseOutput.size()];
									for (int s = 0; s < testCaseOutput.size(); s++) {
										testCaseOutputArr[s] = testCaseOutput.get(s);
										// System.out.println("test case expected output: " + testCaseOutputArr[s]);
									}
									mn.setTestCaseExpectedOutput(testCaseOutputArr);
									testCaseOutput.clear();

									resultStarted = false;

									// Add object to the test case array
									mainArray.add(mn);
								} else if (cd.getOutputFlag() == true) {
									resultStarted = true;
								} else {
									if (resultStarted == false) {
										testCaseInput.add(fileLines.get(i));
									} else {
										testCaseOutput.add(fileLines.get(i));
									}
								}
							} else {
								if (resultStarted == false) {
									testCaseInput.add(fileLines.get(i));
								} else {
									testCaseOutput.add(fileLines.get(i));
								}
							}
						}
					}
				}
			}

			if (className == null) {
				throw new Exception("ERROR: No class name found in the file: " + path.toString());
			}
			if (methodName == null) {
				throw new Exception("ERROR: No method name found in the file.");
			}

			MethodCore mc = new MethodCore();
			mc.invokeFunction(mainArray, className, methodName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CommandDTO processCommands(String command) {
		CommandDTO cd = new CommandDTO();
		if (command.toUpperCase().equals("/END CASE/")) {
			cd.setEndCaseFlag(true);
		} else if (command.toUpperCase().equals("/OUTPUT/")) {
			cd.setOutputFlag(true);
		}
		return cd;
	}

}
