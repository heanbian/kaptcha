package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class TextProducer456 extends AbstractKaptchaConfig implements TextProducer {

	private static final SecureRandom RAND = new SecureRandom();

	public String getText() {
		int minute = LocalDateTime.now().getMinute();
		int length = (minute % 2 == 0) ? 5 : ((minute % 3 == 0) ? 6 : 4);
		char[] chars = getKaptchaConfig().getTextProducerCharString();
		StringBuilder tb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			tb.append(chars[RAND.nextInt(chars.length)]);
		}
		return tb.toString();
	}

}