package com.google.code.kaptcha.impl;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class NoNoise extends AbstractKaptchaConfig implements NoiseProducer {
	
	public void makeNoise(BufferedImage image, float factorOne, float factorTwo, float factorThree, float factorFour) {
	}
}
