package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

public interface NoiseProducer {
	
	public void makeNoise(BufferedImage image, float factorOne, float factorTwo, float factorThree, float factorFour);
	
}