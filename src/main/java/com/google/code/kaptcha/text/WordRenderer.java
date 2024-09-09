package com.google.code.kaptcha.text;

import java.awt.image.BufferedImage;

public interface WordRenderer {
	
	public BufferedImage renderWord(String word, int width, int height);
	
}
