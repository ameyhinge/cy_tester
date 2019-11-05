package com.cy.tester.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cy.tester.interfaces.*;

public class LoggerDriver {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter the path of test cases directory");
			String directoryPath = br.readLine();

			ILoggerCore lc = new LoggerCore();
			lc.testApp(directoryPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
