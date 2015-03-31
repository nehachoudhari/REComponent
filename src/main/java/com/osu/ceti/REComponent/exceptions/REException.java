package com.osu.ceti.REComponent.exceptions;

public class REException extends Exception{

	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public REException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public REException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String toString() {
		return null;
	}

}
