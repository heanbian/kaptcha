package com.google.code.kaptcha.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.AbstractKaptchaConfig;

public class DefaultKaptcha extends AbstractKaptchaConfig implements Producer {

	private int width = 200;

	private int height = 50;

	public BufferedImage createImage(String text) {
		int width = getKaptchaConfig().getWidth();
		int height = getKaptchaConfig().getHeight();
		return createImage(text, width, height);
	}

	public BufferedImage createImage(String text, int width, int height) {
		this.width = width;
		this.height = height;

		WordRenderer wordRenderer = getKaptchaConfig().getWordRendererImpl();
		GimpyEngine gimpyEngine = getKaptchaConfig().getObscurificatorImpl();
		BackgroundProducer backgroundProducer = getKaptchaConfig().getBackgroundImpl();
		boolean isBorderDrawn = getKaptchaConfig().isBorderDrawn();

		BufferedImage bi = wordRenderer.renderWord(text, width, height);
		bi = gimpyEngine.getDistortedImage(bi);
		bi = backgroundProducer.addBackground(bi);
		Graphics2D graphics = bi.createGraphics();
		if (isBorderDrawn) {
			drawBox(graphics);
		}
		return bi;
	}

	private void drawBox(Graphics2D graphics) {
		Color borderColor = getKaptchaConfig().getBorderColor();
		int borderThickness = getKaptchaConfig().getBorderThickness();

		graphics.setColor(borderColor);

		if (borderThickness != 1) {
			BasicStroke stroke = new BasicStroke((float) borderThickness);
			graphics.setStroke(stroke);
		}

		Line2D line1 = new Line2D.Double(0, 0, 0, width);
		graphics.draw(line1);
		Line2D line2 = new Line2D.Double(0, 0, width, 0);
		graphics.draw(line2);
		line2 = new Line2D.Double(0, height - 1, width, height - 1);
		graphics.draw(line2);
		line2 = new Line2D.Double(width - 1, height - 1, width - 1, 0);
		graphics.draw(line2);
	}

	public String createText() {
		return getKaptchaConfig().getTextProducerImpl().getText();
	}
}
