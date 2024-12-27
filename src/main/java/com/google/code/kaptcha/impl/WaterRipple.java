package com.google.code.kaptcha.impl;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;
import com.google.code.kaptcha.util.KaptchaException;

public class WaterRipple extends AbstractKaptchaConfig implements GimpyEngine {

	public BufferedImage getDistortedImage(BufferedImage baseImage) {
		throw new KaptchaException("WaterRipple is not supported");
	}
}
