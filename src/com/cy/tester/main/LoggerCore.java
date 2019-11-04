package com.cy.tester.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.tester.dtos.CommandDTO;
import com.cy.tester.dtos.LoggerDTO;
import com.cy.tester.interfaces.ILoggerCore;

public class LoggerCore implements ILoggerCore {

	// Hash Map to store all commands
	static Map<String, Character> COMMANDS = new HashMap<String, Character>();
	static {
		COMMANDS.put("START FILE", '0');
		COMMANDS.put("END CASE", '0');
	}

	@Override
	public void testApp(String directoryPath) {
		pickFiles(directoryPath);
	}

	// Private Functions
	private void pickFiles(String directoryPath) {

		try {
			long a = System.currentTimeMillis();

			Files.newDirectoryStream(Paths.get(directoryPath), path -> path.toFile().isFile())
					.forEach(path -> processFile(path));

			System.out.println(System.currentTimeMillis() - a);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processFile(Path path) {
		try {
			if (path.toString().endsWith(".txt")) {

				// Pass the values in arraylist
				List<String> fileLines = new ArrayList<String>();
				Files.lines(path).forEach(line -> {
					if (COMMANDS.containsKey(line.trim().toUpperCase())) {
						fileLines.add(line.trim().toUpperCase());
					} else {
						fileLines.add(line.trim());
					}
				});

				// Get and verify start point in the file
				int startIndex = fileLines.indexOf("/START FILE/");
				if (startIndex < 0) {
					throw new Exception("Invalid or no start point in the file.");
				}

				List<LoggerDTO> ldArray = new ArrayList<LoggerDTO>();
				for (int i = startIndex + 1; i < fileLines.size(); i++) {
					if (COMMANDS.containsKey(fileLines.get(i))) {
						CommandDTO cd = new CommandDTO();
						cd = processCommands(fileLines.get(i));
					} else {

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CommandDTO processCommands(String command) {
		CommandDTO cd = new CommandDTO();
		if (command.toUpperCase().equals("/START FILE/")) {
			cd.setStartFileFlag(true);
		}
		if (command.toUpperCase().equals("/END CASE/")) {
			cd.setEndCaseFlag(true);
		}
		return cd;
	}

	private void writeLogs(LoggerDTO ld) {

	}

}
