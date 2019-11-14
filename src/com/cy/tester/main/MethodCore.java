package com.cy.tester.main;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.cy.tester.dtos.MainDTO;

public class MethodCore {

	public String[] processVariable(String line) {
		String[] variableDetails = new String[2];
		try {
			if (line.startsWith("/VAR CLASS-")) {
				variableDetails[0] = "CLASS";
				variableDetails[1] = line.substring(11, line.length()).trim();
			} else if (line.startsWith("/VAR METHOD-")) {
				variableDetails[0] = "METHOD";
				variableDetails[1] = line.substring(12, line.length()).trim();
			} else {
				variableDetails[0] = "null";
				variableDetails[1] = "null";
			}
			return variableDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return variableDetails;
		}
	}

	public void invokeFunction(ArrayList<MainDTO> mainData, String className, String methodName) {

		for (int i = 0; i < mainData.size(); i++) {

			printMainData(mainData.get(i));

			// Invoke method
			try {
				Class<?> cls = Class.forName(className);
				Method[] methods = cls.getMethods();

				Class<?>[] parameterTypes = null;
				for (Method m : methods) {
					if (m.getName().equals(methodName)) {
						parameterTypes = m.getParameterTypes();
						break;
					}
				}

				Object[] obj = new Object[parameterTypes.length];

				for (int p = 0; p < parameterTypes.length; p++) {
					try {
						if (parameterTypes[p].toString().equals("int")) {
							obj[p] = Integer.parseInt(mainData.get(i).getTestCaseInput()[p]);
							System.out.println("int found at " + p + " in test case " + i);
						}
					} catch (Exception e) {
						System.out.println("ERROR: Can not convert input to a valid function parameter at parameter "
								+ p + " in test case " + i);
						mainData.get(i)
								.setNote("ERROR: Can not convert input to a valid function parameter at parameter " + p
										+ " in test case " + i);
					}
				}

				Method m = cls.getMethod(methodName, parameterTypes);

				System.out.println(m.invoke(cls.newInstance(), obj));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("New file started");
	}

	// Private functions
	private void printMainData(MainDTO md) {
		System.out.println("Test case: " + md.getTestCaseNo());
		System.out.println("Expected output: " + md.getTestCaseExpectedOutput()[0]);
		System.out.println("Row number: " + md.getTestCaseRow());
	}
}
