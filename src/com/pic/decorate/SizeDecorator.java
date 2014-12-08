package com.pic.decorate;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.utils.Im4javaImageUtil;

/**
 * 图片尺寸装饰器
 * 
 * @author Wuhao
 */
public class SizeDecorator extends Decorator {

	private Logger log = LoggerFactory.getLogger(AspectDecorator.class);
	double standSize;

	@Override
	public void operation(DecoratorContext context) {
		decorator.operation(context);
		standSize = context.getSize();
		String path = context.getFilePaths();
		File file = new File(path);
		double fileSize = file.length() / 1024.0;
		if (fileSize > standSize) {
			try {
				Im4javaImageUtil.changeQuality(85, path, path);
			} catch (Exception e) {
				log.warn("SizeDecorator operation error", e);
			}
		}
		fileSize = file.length() / 1024.0;
		if (fileSize > context.getSize()) {
			try {
				ImageInfoBO image = new ImageInfoBO(path);
				int width = image.getWidth();
				int height = image.getHeigth();
				if (width > context.getWidthFloor() && height > context.getWidthFloor()) {
					if (width > height) {
						height = context.getWidthFloor().intValue();
					} else {
						width = context.getWidthFloor().intValue();
					}
				}
				Im4javaImageUtil.scaleImage(width, height, path, path);
			} catch (Exception e) {
				log.warn("SizeDecorator operation error", e);
			}
		}
	}
}
