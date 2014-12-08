package com.pic.decorate;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.DecoratorContext;
import com.pic.utils.FileUtils;
import com.pic.utils.Im4javaImageUtil;

/**
 * 图片类型转换装饰器。目前不支持gif的转换哦。哎原来的图片都没有删除
 * 
 * @author wuhao
 */
public class TypeDectator extends Decorator {

	private Logger log = LoggerFactory.getLogger(AspectDecorator.class);

	@Override
	public void operation(DecoratorContext context) {
		decorator.operation(context);
		String desPath = context.getRootPath() + File.separator + context.getPicName() + "." + context.getDesType();
		try {
			Im4javaImageUtil.changeImgeFormat(context.getFilePaths(), desPath);
		} catch (Exception e) {
			log.warn("SizeDecorator operation error", e);
		}
		FileUtils.deleteFile(context.getFilePaths());
		context.setFilePaths(desPath);

	}

}
