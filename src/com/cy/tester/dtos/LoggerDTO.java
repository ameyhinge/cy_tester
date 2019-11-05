package com.cy.tester.dtos;

public class LoggerDTO {
	private int testCaseNo;
	private int testCaseRow;
	private String[] testCaseInput;
	private String testCaseOutput;
	private String result;
	private String note;

	public int getTestCaseNo() {
		return testCaseNo;
	}

	public void setTestCaseNo(int testCaseNo) {
		this.testCaseNo = testCaseNo;
	}

	public int getTestCaseRow() {
		return testCaseRow;
	}

	public void setTestCaseRow(int testCaseRow) {
		this.testCaseRow = testCaseRow;
	}

	public String[] getTestCaseInput() {
		return testCaseInput;
	}

	public void setTestCaseInput(String[] testCaseInput) {
		this.testCaseInput = testCaseInput;
	}

	public String getTestCaseOutput() {
		return testCaseOutput;
	}

	public void setTestCaseOutput(String testCaseOutput) {
		this.testCaseOutput = testCaseOutput;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
