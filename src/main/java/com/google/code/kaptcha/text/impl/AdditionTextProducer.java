package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class AdditionTextProducer extends AbstractKaptchaConfig implements TextProducer {

	private static final SecureRandom RAND = new SecureRandom();

	private int num1;
	private int num2;

	public String getText() {
		num1 = RAND.nextInt(90) + 10;
		num2 = RAND.nextInt(90) + 10;
		return String.format("%d + %d = ?", num1, num2);
	}

	public int getAnswer() {
		return num1 + num2;
	}

}