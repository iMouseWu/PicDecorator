package com.pic.decorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.utils.ImageUtils;

/**
 * 长宽比装饰器
 * 
 * @author Wuhao
 */
public class AspectDecorator extends Decorator {

	private Logger log = LoggerFactory.getLogger(AspectDecorator.class);

	@Override
	public void operation(DecoratorContext context) {
		decorator.operation(context);
		String path = context.getFilePaths();
		try {
			ImageInfoBO imageInfo = new ImageInfoBO(path);
			int width = imageInfo.getWidth();
			int height = imageInfo.getHeigth();
			if (judgeScale(width, height, context.getAspectContrast())) {
				return;
			}
			int background = width > height ? width : height;
			ImageUtils.createImgWithBacg(path, path, background);
		} catch (Exception e) {
			log.warn("AspectDecorator operation error", e);
		}
	}

	private boolean judgeScale(double width, double height, double aspectContrast) {
		if (width / height < aspectContrast || height / width < aspectContrast) {
			return false;
		}
		return true;
	}

}
