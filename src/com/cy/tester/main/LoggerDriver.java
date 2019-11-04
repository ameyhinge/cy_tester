package com.cy.tester.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.*;

import com.cy.tester.interfaces.*;

public class LoggerDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Enter the path of test cases directory");
			String directoryPath = br.readLine();

			ILoggerCore lc = new LoggerCore();
			lc.testApp(directoryPath);
			
			Class cls =lc.getClass();
			Method[] method = cls.getDeclaredMethods();
			
			System.out.println(method[0].getName());
			System.out.println(method[0].getParameterCount());

		} catch (Exception e) {

		}
	}

}
