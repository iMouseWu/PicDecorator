package com.pic.decorate;

import java.util.List;

import com.pic.DecoratorContext;

@Deprecated
public class CutDecorator extends Decorator {

	private long dftSize;

	public long getDftSize() {
		return dftSize;
	}

	public void setDftSize(long dftSize) {
		this.dftSize = dftSize;
	}

	@Override
	public void operation(DecoratorContext context) {
		// decorator.operation(context);
		// List<String> newPath = new ArrayList<String>();
		// List<String> paths = context.getFilePaths();
		// double maxSize = context.getSize();
		// for (String path : paths) {
		// List<String> newList = cutImg(path, maxSize);
		// }
	}

	public List<String> cutImg(String path, double maxSize) {
		return null;
		// List<String> list = null;
		// try {
		// list = new ArrayList<String>();
		// ImageInfoBO info = ImageUtils.getResolution(path);
		// if (null == info) {
		// list.add(path);
		// return list;
		// }
		// long imgsize = info.getSize();
		// if (imgsize <= maxSize) {
		// list.add(path);
		// return list;
		// }
		// String rootPath = info.getRootPath();
		// String name = info.getName();
		// String suffix = info.getSuffix();
		//
		// int num = (int) (imgsize / dftSize);
		// int heigth = info.getHeigth();
		// int h = heigth / num;
		// int x = 0;
		// int y = 0;
		// for (int i = 0; i < num; i++) {
		// int x_end = x;
		// int y_end = y + h;
		// y_end = y_end > heigth ? heigth : y_end;
		// String newPath = rootPath + name + "_" + i + suffix;
		// try {
		// Im4javaImageUtil.cutImage(path, newPath, x, y, x_end, y_end);
		// list.add(newPath);
		// } catch (Exception e) {
		// }
		//
		// y += h;
		// }
		//
		// } catch (Exception e) {
		// list = null;
		// // log.error("call YhdCutImgImpl cutImg error", e);
		// }
		// return list;
	}

}
