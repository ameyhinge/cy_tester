package com.cy.tester.dtos;

public class CommandDTO {
	private boolean startFileFlag;
	private boolean endCaseFlag;
	private boolean outputFlag;

	public boolean getStartFileFlag() {
		return startFileFlag;
	}

	public void setStartFileFlag(boolean startFileFlag) {
		this.startFileFlag = startFileFlag;
	}

	public boolean getEndCaseFlag() {
		return endCaseFlag;
	}

	public void setEndCaseFlag(boolean endCaseFlag) {
		this.endCaseFlag = endCaseFlag;
	}

	public boolean getOutputFlag() {
		return outputFlag;
	}

	public void setOutputFlag(boolean outputFlag) {
		this.outputFlag = outputFlag;
	}

}
