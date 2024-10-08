package com.google.code.kaptcha.text.impl;

import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

public class DefaultTextCreator extends Configurable implements TextProducer {
	
	public String getText() {
		int length = getConfig().getTextProducerCharLength();
		char[] chars = getConfig().getTextProducerCharString();
		Random rand = new SecureRandom();
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < length; i++) {
			text.append(chars[rand.nextInt(chars.length)]);
		}

		return text.toString();
	}
	
}
