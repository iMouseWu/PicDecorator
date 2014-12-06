package com.pic.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static void createImgWithBacg(String srcPath, String desPath, int bgSize) throws IOException {
		Graphics graphics = null;
		try {
			File file = new File(srcPath);

			BufferedImage small = ImageIO.read(file);
			BufferedImage image = new BufferedImage(bgSize, bgSize, BufferedImage.TYPE_INT_RGB);

			graphics = image.getGraphics();
			graphics.setColor(new Color(255, 255, 255));
			graphics.fillRect(0, 0, bgSize, bgSize);
			int x = (image.getWidth() - small.getWidth()) / 2;
			int y = (image.getHeight() - small.getHeight()) / 2;
			graphics.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);

			String suffix = desPath.substring(desPath.lastIndexOf(".") + 1);
			ImageIO.write(image, suffix, new File(desPath));
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != graphics) {
				graphics.dispose();
			}
		}
	}
}
