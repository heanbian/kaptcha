package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class TextProducer456 extends AbstractKaptchaConfig implements TextProducer {

	public String getText() {
		int minute = LocalDateTime.now().getMinute();
		int length = (minute % 2 == 0) ? 5 : ((minute % 3 == 0) ? 6 : 4);
		Random rand = new SecureRandom();
		char[] chars = getKaptchaConfig().getTextProducerCharString();
		StringBuilder tb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			tb.append(chars[rand.nextInt(chars.length)]);
		}
		return tb.toString();
	}

}