package com.google.code.kaptcha.util;

public class KaptchaException extends RuntimeException {

	private static final long serialVersionUID = 6937416954897707291L;

	public KaptchaException(String message) {
		super(message);
	}

	public KaptchaException(String paramName, String paramValue, Throwable cause) {
		super("Invalid value '" + paramValue + "' for config parameter '" + paramName + "'.", cause);
	}

	public KaptchaException(String paramName, String paramValue, String message) {
		super("Invalid value '" + paramValue + "' for config parameter '" + paramName + "'. " + message);
	}
	
}
