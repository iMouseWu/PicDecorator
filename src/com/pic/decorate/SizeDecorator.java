package com.pic.decorate;

import java.io.File;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.utils.Im4javaImageUtil;
import com.pic.utils.ImageUtils;

/**
 * 图片尺寸装饰器
 * 
 * @author Wuhao
 */
public class SizeDecorator extends Decorator {

	@Override
	public void operation(DecoratorContext decoratorContext) {
		decorator.operation(decoratorContext);
		String path = decoratorContext.getFilePaths();
		File file = new File(path);
		double fileSize = file.length() / 1024.0;
		if (fileSize > decoratorContext.getSize()) {
			try {
				Im4javaImageUtil.changeQuality(85, path, path);
			} catch (Exception e) {
			}
		}
		fileSize = file.length() / 1024.0;
		if (fileSize > decoratorContext.getSize()) {
			ImageInfoBO image = ImageUtils.getPicInfo(path);
			int width = image.getWidth();
			int height = image.getHeigth();
			if (width > decoratorContext.getWidthFloor() && height > decoratorContext.getWidthFloor()) {
				if (width > height) {
					width = (int) (width * decoratorContext.getWidthFloor() / height);
				} else {
					width = decoratorContext.getWidthFloor().intValue();
				}

			}
			try {
				Im4javaImageUtil.cutImage(width, path, path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
