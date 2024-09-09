package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

public interface BackgroundProducer {
	
	public abstract BufferedImage addBackground(BufferedImage image);
	
}
