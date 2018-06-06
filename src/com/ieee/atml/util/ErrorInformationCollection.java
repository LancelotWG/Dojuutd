package com.ieee.atml.util;

public class ErrorInformationCollection {
	public enum ErrorType{
		StandardError,
		LogicalError
	}
	private String errorInformation;
	private ErrorType errorType;
	public String getErrorInformation() {
		return errorInformation;
	}
	public void setErrorInformation(String errorInformation) {
		this.errorInformation = errorInformation;
	}
	public ErrorType getErrorType() {
		return errorType;
	}
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	
}
