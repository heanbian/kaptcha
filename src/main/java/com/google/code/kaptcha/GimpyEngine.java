package com.google.code.kaptcha;

import java.awt.image.BufferedImage;

public interface GimpyEngine {
	
	public BufferedImage getDistortedImage(BufferedImage baseImage);
	
}