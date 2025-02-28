package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class DefaultTextCreator extends AbstractKaptchaConfig implements TextProducer {

	private static final SecureRandom RAND = new SecureRandom();

	public String getText() {
		int length = getKaptchaConfig().getTextProducerCharLength();
		char[] chars = getKaptchaConfig().getTextProducerCharString();
		StringBuilder tb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			tb.append(chars[RAND.nextInt(chars.length)]);
		}
		return tb.toString();
	}

}
