package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

public interface Producer {
	
	public BufferedImage createImage(String text);

	public abstract String createText();
	
}