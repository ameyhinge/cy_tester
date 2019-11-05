package com.cy.tester.main;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
			for (Class<?> cls : parameterTypes) {
				ParameterDTO pd = new ParameterDTO();
				pd.setParameterType(cls.toString());
				System.out.println("parameter type: " + cls);
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

	public void invokeFunction() {

	}
}
