package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

public class TextProducer456 extends Configurable implements TextProducer {

	public String getText() {
		int length = 4;

		var min = LocalDateTime.now().getMinute();
		if (min % 2 == 0) {
			length = 5;
		}
		if (min % 3 == 0) {
			length = 6;
		}

		char[] chars = getConfig().getTextProducerCharString();
		Random rand = new SecureRandom();
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < length; i++) {
			text.append(chars[rand.nextInt(chars.length)]);
		}

		return text.toString();
	}
}