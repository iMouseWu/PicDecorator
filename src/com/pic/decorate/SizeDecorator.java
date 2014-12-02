package com.pic.decorate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pic.DecoratorContext;
import com.pic.utils.Im4javaImageUtil;

/**
 * 图片尺寸装饰器
 * 
 * @author Wuhao
 */
public class SizeDecorator extends Decorator {

	@Override
	public void operate(DecoratorContext decoratorContext) {
		decorator.operate(decoratorContext);

		List<String> newPath = new ArrayList<String>();
		List<String> paths = decoratorContext.getFilePaths();
		for (String path : paths) {
			File newFile = new File(path);
			double newKb = newFile.length() / 1024.0;
			if (newKb > decoratorContext.getSize()) {
				try {
					Im4javaImageUtil.changeQuality(85, path, path);
					newPath.add(path);
				} catch (Exception e) {

				}
			}
		}
		decoratorContext.setFilePaths(newPath);
	}
}
