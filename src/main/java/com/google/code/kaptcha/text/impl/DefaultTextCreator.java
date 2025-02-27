package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class DefaultTextCreator extends AbstractKaptchaConfig implements TextProducer {
	
	public String getText() {
		int length = getKaptchaConfig().getTextProducerCharLength();
		char[] chars = getKaptchaConfig().getTextProducerCharString();
		Random rand = new SecureRandom();
		StringBuilder tb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			tb.append(chars[rand.nextInt(chars.length)]);
		}
		return tb.toString();
	}
	
}
