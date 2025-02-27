package com.google.code.kaptcha.util;

public abstract class AbstractKaptchaConfig {

	private KaptchaConfig kaptchaConfig = null;

	public KaptchaConfig getKaptchaConfig() {
		return this.kaptchaConfig;
	}

	public void setKaptchaConfig(KaptchaConfig kaptchaConfig) {
		this.kaptchaConfig = kaptchaConfig;
	}
	
}
