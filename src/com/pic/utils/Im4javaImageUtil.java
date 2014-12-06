package com.pic.utils;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import com.pic.bo.ImageInfoBO;

public class Im4javaImageUtil {

	public static String imageMagickPath = null;

	static {
		imageMagickPath = "C:\\Program Files\\ImageMagick-6.8.9-Q16";
	}

	/**
	 * 根据坐标裁剪图片
	 * 
	 * @param srcPath 要裁剪图片的路径
	 * @param newPath 裁剪图片后的路径
	 * @param x 起始横坐标
	 * @param y 起始挫坐标
	 * @param x1 结束横坐标
	 * @param y1 结束挫坐标
	 */
	public static void cutImage(String srcPath, String desPath, int x, int y, int x1, int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.crop(width, height, x, y);
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 根据宽度缩放图片
	 * 
	 * @param width 缩放后的图片宽度
	 * @param srcPath 源图片路径
	 * @param newPath 缩放后图片的路径
	 */
	public static void scaleImageByWidth(int width, String srcPath, String desPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(width, null);
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 根据宽度缩放图片
	 * 
	 * @param width 缩放后的图片宽度
	 * @param srcPath 源图片路径
	 * @param newPath 缩放后图片的路径
	 */
	public static void scaleImageByHeight(int height, String srcPath, String desPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(null, height);
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	public static void scaleImage(int width, int height, String srcPath, String desPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.resize(width, height);
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 改变图片质量
	 * 
	 * @param quality
	 * @param srcPath
	 * @param newPath
	 * @throws Exception
	 */
	public static void changeQuality(double quality, String srcPath, String desPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.addRawArgs("-quality", quality + "");
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);

		convert.run(op);

	}

	public static void changeImgeFormat(String srcPath, String desPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.addImage(desPath);
		ConvertCmd convert = new ConvertCmd();
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	public static void main(String[] args) throws Exception {
		// scaleImage(width, srcPath, desPath);
		ImageInfoBO ima = new ImageInfoBO("D:\\777.jpg");
		System.out.println(ima.getWidth());
		System.out.println(ima.getHeigth());
		scaleImage(300, 250, "D:\\777.jpg", "D:\\777N.jpg");
		ImageInfoBO ima2 = new ImageInfoBO("D:\\777N.jpg");
		System.out.println(ima2.getWidth());
		System.out.println(ima2.getHeigth());
	}

}
