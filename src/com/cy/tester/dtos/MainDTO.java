package com.cy.tester.dtos;

public class MainDTO {
	private int testCaseNo;
	private int testCaseRow;
	private String[] testCaseInput;
	private String[] testCaseOutput;
	private String[] testCaseExpectedOutput;
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

	public String[] getTestCaseOutput() {
		return testCaseOutput;
	}

	public void setTestCaseOutput(String[] testCaseOutput) {
		this.testCaseOutput = testCaseOutput;
	}

	public String[] getTestCaseExpectedOutput() {
		return testCaseExpectedOutput;
	}

	public void setTestCaseExpectedOutput(String[] testCaseExpectedOutput) {
		this.testCaseExpectedOutput = testCaseExpectedOutput;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
