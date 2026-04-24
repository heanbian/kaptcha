package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class TextProducer456 extends AbstractKaptchaConfig implements TextProducer {

	private static final SecureRandom RAND = new SecureRandom();

	@Override
	public String getText() {
		int length = RAND.nextInt(3) + 4; // 4~6
		char[] chars = getKaptchaConfig().getTextProducerCharString();
		if (chars == null || chars.length == 0) {
			throw new IllegalStateException("Captcha char set must not be empty");
		}

		StringBuilder sb = new StringBuilder(length);
		char last = 0;
		for (int i = 0; i < length; i++) {
			char next;
			do {
				next = chars[RAND.nextInt(chars.length)];
			} while (chars.length > 1 && next == last);
			sb.append(next);
			last = next;
		}
		return sb.toString();
	}

}