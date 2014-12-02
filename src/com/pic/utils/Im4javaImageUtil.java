package com.pic.utils;

import java.util.ArrayList;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * ImageMagick和im4java处理图片
 * 
 * @author sunlightcs 2011-6-1 http://hi.juziku.com/sunlightcs/
 */
public class Im4javaImageUtil {

	/**
	 * ImageMagick的路径
	 */
	public static String imageMagickPath = null;

	static {
		/**
		 * 获取ImageMagick的路径
		 */
		// Properties prop = new PropertiesFile().getPropertiesFile();
		// linux下不要设置此值，不然会报错
		// imageMagickPath = prop.getProperty("imageMagickPath");
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
	public static void cutImage(String srcPath, String newPath, int x, int y, int x1, int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		/**
		 * width：裁剪的宽度 height：裁剪的高度 x：裁剪的横坐标 y：裁剪的挫坐标
		 */
		op.crop(width, height, x, y);

		op.addImage(newPath);

		ConvertCmd convert = new ConvertCmd();

		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);

		convert.run(op);
	}

	/**
	 * 根据尺寸缩放图片
	 * 
	 * @param width 缩放后的图片宽度
	 * @param height 缩放后的图片高度
	 * @param srcPath 源图片路径
	 * @param newPath 缩放后图片的路径
	 */
	public static void cutImage(int width, int height, String srcPath, String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		op.resize(width, height);
		op.addImage(newPath);

		ConvertCmd convert = new ConvertCmd();

		// linux下不要设置此值，不然会报错
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
	public static void cutImage(int width, String srcPath, String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);

		op.resize(width, null);
		op.addImage(newPath);

		ConvertCmd convert = new ConvertCmd();

		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);

		convert.run(op);
	}

	/**
	 * 给图片加水印
	 * 
	 * @param srcPath 源图片路径
	 */
	public static void addImgText(String srcPath) throws Exception {
		IMOperation op = new IMOperation();
		op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("text 5,5 juziku.com");

		op.addImage();
		op.addImage();
		ConvertCmd convert = new ConvertCmd();

		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);

		convert.run(op, srcPath, srcPath);
	}

	/**
	 * 改变图片质量
	 * 
	 * @param quality
	 * @param srcPath
	 * @param newPath
	 * @throws Exception
	 */
	public static void changeQuality(double quality, String srcPath, String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.addRawArgs("-quality", quality + "");
		// op.addRawArgs("-thumbnail", 500+"x"+500+"!");
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);

		convert.run(op);

	}

	public static void changeImgeFormat(String srcPath, String newPath) throws Exception {
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();
		// linux下不要设置此值，不然会报错
		convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	public static void main(String[] args) throws Exception {

		// Info imageInfo = new Info("D:\\3331.jpg", true);
		// System.out.println(imageInfo.getImageFormat());
		// cutImage("D:\\3331.jpg", "D:\\3331.jpg", 98, 48, 370, 320);
		// cutImage(98, "D:\\1111.jpg", "D:\\1111.jpg");

		changeImgeFormat("D:\\2222.gif", "D:\\1111.jpg");
		// ImageInfoBO imageInfoBO = ImageUtils.getResolutionNew("D:\\1111.jpg");
		// System.out.println(imageInfoBO.getSuffix());
		// cutImage(200,300, "/home/steven/a.jpg", "/home/steven/aa.jpg");
		// addImgText("//home//steven//a.jpg");

		// BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		// File file = new File("D:\\122233.gif");
		// // 获取图片处理对象
		// Graphics graphics = image.getGraphics();
		// // 填充背景色
		// graphics.setColor(new Color(255, 251, 240));
		// graphics.fillRect(0, 0, 100, 100);
		// file.mkdir();
		// ImageIO.write(image, "GIF", file);
		//
		// BufferedImage big = ImageIO.read(new File("D:\\122233.gif"));
		//
		// BufferedImage small = ImageIO.read(new File("D:\\20140903161031.jpg"));
		// Graphics2D g = big.createGraphics();
		// int x = (big.getWidth() - small.getWidth()) / 2;
		// int y = (big.getHeight() - small.getHeight()) / 2;
		// g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
		// g.dispose();
		// ImageIO.write(big, "jpg", new File("D:\\20140903161034443.jpg"));
		// cutImage(11100, 100, "D:\\20140903161031.jpg", "D:\\dsad5.jpg");

	}

	public static int getWidth(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();
			op.format("%w"); // 设置获取宽度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			System.out.println(e);
			line = 0;
			System.out.println("运行指令出错!");
		}
		return line;
	}

	/**
	 * 获得图片的高度
	 * 
	 * @param imagePath 文件路径
	 * @return 图片高度
	 */
	public static int getHeight(String imagePath) {
		int line = 0;
		try {
			IMOperation op = new IMOperation();

			op.format("%h"); // 设置获取高度参数
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();
			assert cmdOutput.size() == 1;
			line = Integer.parseInt(cmdOutput.get(0));
		} catch (Exception e) {
			line = 0;
			System.out.println(e);
			System.out.println("运行指令出错!" + e.toString());
		}
		return line;
	}
}
