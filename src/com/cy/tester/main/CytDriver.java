package com.cy.tester.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cy.tester.interfaces.*;

public class CytDriver {

	public static void main(String[] args) {

		double startTime = System.currentTimeMillis();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter the path of test cases directory");
			String directoryPath = br.readLine();

			ICytCore lc = new CytCore();
			lc.testApp(directoryPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Time to complete (ms): " + (System.currentTimeMillis() - startTime));
	}

}
