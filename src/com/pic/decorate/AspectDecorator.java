package com.pic.decorate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.pic.DecoratorContext;
import com.pic.utils.FileUtils;
import com.pic.utils.ImageUtils;

/**
 * 长宽比装饰器
 * 
 * @author Wuhao
 */
public class AspectDecorator extends Decorator {

	@Override
	public void operate(DecoratorContext decoratorContext) {
		decorator.operate(decoratorContext);
		List<String> newPath = new ArrayList<String>();
		List<String> paths = decoratorContext.getFilePaths();
		for (String path : paths) {
			try {
				Map<String, Integer> map = ImageUtils.getResolution(path);
				int w = map.get("width");
				int h = map.get("height");
				int background = w > h ? h : w;
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
				ImageIO.write(image, FileUtils.getFileSuffix(path), file);
				newPath.add(path);
			} catch (Exception e) {

			}
			decoratorContext.setFilePaths(newPath);
		}

	}
}
