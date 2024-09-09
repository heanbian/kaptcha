package com.google.code.kaptcha.util;

public abstract class Configurable {
	
	private Config config = null;

	public Config getConfig() {
		return this.config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
