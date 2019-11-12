package com.cy.tester.main;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.cy.tester.dtos.MainDTO;
import com.cy.tester.dtos.ParameterDTO;

public class MethodCore {
	public ArrayList<ParameterDTO> parameterList(String className, String methodName) {
		ArrayList<ParameterDTO> apd = new ArrayList<ParameterDTO>();
		try {
			Class<?> cl = Class.forName(className);
			Method[] methods = cl.getMethods();

			Class<?>[] parameterTypes = null;
			for (Method m : methods) {
				if (m.getName().equals(methodName)) {
					parameterTypes = m.getParameterTypes();
					break;
				}
			}

			if (parameterTypes == null) {
				throw new Exception("ERROR: The method specified in file can not be found.");
			}

			// Get parameter details
			for (int p = 0; p < parameterTypes.length; p++) {
				ParameterDTO pd = new ParameterDTO();
				pd.setParameterType(parameterTypes[p].toString());
				System.out.println("parameter type: " + parameterTypes[p]);
				apd.add(pd);
			}
			return apd;
		} catch (ClassNotFoundException e) {
			System.out.println("The class specified in the file can not be found.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apd;
	}

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

	public void invokeFunction(ArrayList<ParameterDTO> parameterList, ArrayList<MainDTO> mainData, String className,
			String methodName) {

		for (int i = 0; i < mainData.size(); i++) {
			Object[] obj = new Object[parameterList.size()];
			for (int p = 0; p < parameterList.size(); p++) {
				try {
					if (parameterList.get(p).getParameterType().equals("int")) {

						obj[p] = Integer.parseInt(mainData.get(i).getTestCaseInput()[p]);
						System.out.println("int found at " + p + " in test case " + i);
					}
				} catch (Exception e) {
					System.out.println("ERROR: Can not convert input to a valid function parameter at parameter " + p
							+ " in test case " + i);
					mainData.get(i).setNote("ERROR: Can not convert input to a valid function parameter at parameter "
							+ p + " in test case " + i);
				}
			}
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

				Method m = cls.getMethod(methodName, parameterTypes);

				System.out.println(m.invoke(cls.newInstance(), obj));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
