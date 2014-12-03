package com.pic.decorate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.utils.ImageUtils;

/**
 * 长宽比装饰器
 * 
 * @author Wuhao
 */
public class AspectDecorator extends Decorator {

	@Override
	public void operation(DecoratorContext decoratorContext) {
		decorator.operation(decoratorContext);
		String path = decoratorContext.getFilePaths();
		try {
			ImageInfoBO imageInfo = ImageUtils.getPicInfo(path);
			int width = imageInfo.getWidth();
			int height = imageInfo.getHeigth();
			if (judgeScale(width, height, decoratorContext.getAspectContrast())) {
				return;

			}
			int background = width > height ? width : height;
			File file = new File(path);
			BufferedImage small = ImageIO.read(file);
			BufferedImage image = new BufferedImage(background, background, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = image.getGraphics();
			graphics.setColor(new Color(255, 255, 255));
			graphics.fillRect(0, 0, background, background);
			int x = (image.getWidth() - small.getWidth()) / 2;
			int y = (image.getHeight() - small.getHeight()) / 2;
			graphics.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			graphics.dispose();
			ImageIO.write(image, ImageUtils.getSuffixName(path), file);
		} catch (Exception e) {

		}
	}

	private boolean judgeScale(int width, int height, double aspectContrast) {
		if (width / (double) height < aspectContrast || height / (double) width < aspectContrast) {
			return false;
		}
		return true;
	}
}
