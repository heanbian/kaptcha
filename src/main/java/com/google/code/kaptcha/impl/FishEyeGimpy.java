package com.google.code.kaptcha.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.google.code.kaptcha.GimpyEngine;

public class FishEyeGimpy implements GimpyEngine {
	
	public BufferedImage getDistortedImage(BufferedImage baseImage) {

		Graphics2D graph = (Graphics2D) baseImage.getGraphics();
		int imageHeight = baseImage.getHeight();
		int imageWidth = baseImage.getWidth();

		int horizontalLines = imageHeight / 7;
		int verticalLines = imageWidth / 7;

		int horizontalGaps = imageHeight / (horizontalLines + 1);
		int verticalGaps = imageWidth / (verticalLines + 1);

		for (int i = horizontalGaps; i < imageHeight; i = i + horizontalGaps) {
			graph.setColor(Color.blue);
			graph.drawLine(0, i, imageWidth, i);

		}

		for (int i = verticalGaps; i < imageWidth; i = i + verticalGaps) {
			graph.setColor(Color.red);
			graph.drawLine(i, 0, i, imageHeight);

		}

		int pix[] = new int[imageHeight * imageWidth];
		int j = 0;

		for (int j1 = 0; j1 < imageWidth; j1++) {
			for (int k1 = 0; k1 < imageHeight; k1++) {
				pix[j] = baseImage.getRGB(j1, k1);
				j++;
			}

		}

		double distance = ranInt(imageWidth / 4, imageWidth / 3);

		int widthMiddle = baseImage.getWidth() / 2;
		int heightMiddle = baseImage.getHeight() / 2;

		for (int x = 0; x < baseImage.getWidth(); x++) {
			for (int y = 0; y < baseImage.getHeight(); y++) {

				int relX = x - widthMiddle;
				int relY = y - heightMiddle;

				double d1 = Math.sqrt(relX * relX + relY * relY);
				if (d1 < distance) {

					int j2 = widthMiddle
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (x - widthMiddle));
					int k2 = heightMiddle
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (y - heightMiddle));
					baseImage.setRGB(x, y, pix[j2 * imageHeight + k2]);
				}
			}

		}

		return baseImage;
	}

	private int ranInt(int i, int j) {
		double d = Math.random();
		return (int) ((double) i + (double) ((j - i) + 1) * d);
	}

	private double fishEyeFormula(double s) {
		if (s < 0.0D) {
			return 0.0D;
		}
		if (s > 1.0D) {
			return s;
		} else {
			return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
		}
	}
}
