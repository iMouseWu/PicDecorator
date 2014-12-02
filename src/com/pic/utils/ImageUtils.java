package com.pic.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import com.pic.bo.ImageInfoBO;
import com.pic.bo.TupleBO;

public class ImageUtils {

	private static Logger log = Logger.getLogger(ImageUtils.class);

	/**
	 * @Title: getResolution
	 * @Description: (获取图片分辨率 by cy)
	 * @param path
	 * @return Map<String,Integer>
	 */
	@Deprecated
	public static Map<String, Integer> getResolution(String path) {
		String suffix = FileUtils.getFileSuffix(path);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				ImageInputStream stream = new FileImageInputStream(new File(path));
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				map.put("width", width);
				int height = reader.getHeight(reader.getMinIndex());
				map.put("height", height);
			} catch (Exception e) {
				map = null;
				log.info("处理读取图片异常", e);
			} finally {
				reader.dispose();
			}
		}
		return map;
	}

	public static ImageInfoBO getResolutionNew(String path) {
		ImageInfoBO info = null;
		ImageReader reader = null;
		ImageInputStream stream = null;
		try {
			String suffix = getFormatInFile(new File(path));
			Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
			if (iter.hasNext()) {
				reader = iter.next();
				stream = new FileImageInputStream(new File(path));
				reader.setInput(stream);

				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				long size = stream.length();
				info = new ImageInfoBO();
				info.setWidth(width);
				info.setHeigth(height);
				info.setSize(size);

				int lastSeq = path.lastIndexOf(File.separator) + 1;
				int lastdot = path.lastIndexOf(".");

				info.setRootPath(path.substring(0, lastSeq));
				info.setName(path.substring(lastSeq, lastdot));
				info.setSuffix(path.substring(lastdot));
			}
		} catch (Exception e) {
			info = null;
			log.info("处理读取图片异常", e);
		} finally {
			if (null != reader) {
				reader.dispose();
				reader = null;
			}
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {
					log.info("关闭流异常", e);
				}
			}

		}
		return info;
	}

	/**
	 * @Title: createTempImageByResolution
	 * @Description: TODO(按分辨率转化图片。 by cy)
	 * @param weight
	 * @param height
	 * @param path
	 * @return boolean
	 */
	@Deprecated
	public static void coverTempImageByResolution(int weight, int height, String srcPath, String destPath) {
		AdvancedResizeOp resampleOp = new ResampleOp(weight, height);
		BufferedImage tomato;
		try {
			tomato = ImageIO.read(new FileInputStream(srcPath));
			BufferedImage rescaledTomato = resampleOp.filter(tomato, null);
			FileUtils.deleteFile(destPath);
			ImageIO.write(rescaledTomato, FileUtils.getFileSuffix(destPath), new File(destPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断图片长高是否符合情况.true表示符合。false表示不符合
	 * 
	 * @param w
	 * @param h
	 * @return
	 */
	private static boolean judgeScale(int w, int h) {
		if (w / (double) h < 9.8 / 10 || h / (double) w < 9.8 / 10) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否符合下限，如果是的话。就返回true。如果不是的话就返回false
	 * 
	 * @param w
	 * @param h
	 * @return
	 */
	private static boolean judgeFloor(int w, int h) {
		if (w < 380 || h < 380) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否符合上限，如果是的话。就返回true。如果不是的话就返回false
	 * 
	 * @param w
	 * @param h
	 * @return
	 */
	private static boolean judgeTop(int w, int h) {
		if (w > 1000 || h > 1000) {
			return false;
		}
		return true;
	}

	/**
	 * 处理长和宽。这里方法默认a大于b返回值。A对应处理后的a。B对应处理后的b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static TupleBO<Integer, Integer> dealwithWH(int a, int b) {
		TupleBO<Integer, Integer> tupleBO = new TupleBO<Integer, Integer>();
		if (a > 1000 && b > 1000) {
			b = 1000 * b / a;
			a = 1000;
		} else if (a > 1000 && b < 1000 && 380 < b) {
			b = 1000 * b / a;
			a = 1000;
		} else if (a > 1000 && b < 380) {
			b = 1000 * b / a;
			a = 1000;
		} else if (a < 1000 && a > 380 && b > 380 && b < 1000) {
			// TODO
		} else if ((380 < a && a < 1000 && b < 380) || (a < 380 && b < 380)) {
			if ((380 / b) > (1000 / a)) {
				b = 1000 * b / a;
				a = 1000;
			} else {
				a = 380 * a / b;
				b = 380;
			}
		}
		tupleBO.setA(a);
		tupleBO.setB(b);
		return tupleBO;
	}

	public static boolean transferImageResolution(String filePath) {
		try {
			// 文件是否存在
			if (!new File(filePath).exists()) {
				return false;
			}
			ImageInfoBO imageInfoBO = ImageUtils.getResolutionNew(filePath);
			// 读取异常
			if (null == imageInfoBO) {
				return false;
			}
			int w = imageInfoBO.getWidth();
			int h = imageInfoBO.getHeigth();
			int background = 0;
			if (judgeScale(w, h) && judgeFloor(w, h) && judgeTop(w, h)) {
				return true;
			}

			if (w > h) {
				TupleBO<Integer, Integer> tupleBO = dealwithWH(w, h);
				w = tupleBO.getA();
				h = tupleBO.getB();
				background = w;
			} else {
				TupleBO<Integer, Integer> tupleBO = dealwithWH(h, w);
				h = tupleBO.getA();
				w = tupleBO.getB();
				background = h;
			}
			Im4javaImageUtil.cutImage(w, filePath, filePath);
			if (!judgeScale(w, h)) {
				File file = new File(filePath);
				BufferedImage small = ImageIO.read(file);
				BufferedImage image = new BufferedImage(background, background, BufferedImage.TYPE_INT_RGB);
				// 获取路径
				// 获取图片处理对象
				Graphics graphics = image.getGraphics();
				// 填充背景色
				graphics.setColor(new Color(255, 255, 255));
				graphics.fillRect(0, 0, background, background);
				int x = (image.getWidth() - small.getWidth()) / 2;
				int y = (image.getHeight() - small.getHeight()) / 2;
				graphics.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
				graphics.dispose();
				ImageIO.write(image, FileUtils.getFileSuffix(filePath), file);
			}
			return true;
		} catch (Exception e) {
			log.error("transferImageResolution 调用错误 路径为" + filePath, e);
		}
		return false;
	}

	private static String transferImage(String filePath, String desPath, float sizeLimit, int bytesLimit, Long upLimit) throws Exception {
		if (null == desPath) {
			desPath = filePath;
		}

		ImageInfoBO imageInfoBO = ImageUtils.getResolutionNew(filePath);
		if (null == imageInfoBO) {
			return null;
		}
		Integer widthId = imageInfoBO.getWidth();

		if (null == widthId) {
			return null;
		}
		int w = widthId;

		Integer heightId = imageInfoBO.getHeigth();
		if (null == heightId) {
			return null;
		}
		int h = heightId;

		File file = new File(filePath);
		double kb = file.length() / 1024.0;
		if (kb > bytesLimit || kb < 1 || w < sizeLimit || h < sizeLimit) {
			if (w > h) {
				w = (int) (sizeLimit / h * w);
				h = (int) sizeLimit;
			} else {
				h = (int) (sizeLimit / w * h);
				w = (int) sizeLimit;
			}
		}

		if (null != upLimit) {
			if (w > upLimit || h > upLimit) {
				if (w > h) {
					w = (int) (sizeLimit / h * w);
					h = (int) sizeLimit;
				} else {
					h = (int) (sizeLimit / w * h);
					w = (int) sizeLimit;
				}
			}
		}

		Im4javaImageUtil.cutImage(w, filePath, desPath);
		File newFile = new File(desPath);
		double newKb = newFile.length() / 1024.0;
		if (newKb > bytesLimit) {
			Im4javaImageUtil.changeQuality(85, desPath, desPath);
			File newnFile = new File(desPath);
			if (newnFile.length() / 1024.0 > 990) {
				log.info(filePath + "图片过于庞大，转换失败");
				return null;
			}
		}
		return desPath;
	}

	public static String transferImageByPP(String filePath) {
		try {
			transferImage(filePath, null, 300, 990, null);
		} catch (Exception e) {
			log.error("transferImageByPP 调用错误 路径为" + filePath, e);
		}
		return filePath;
	}

	public static String transferImageByDD(String filePath, String desPath) throws Exception {
		String retunPath = null;
		try {
			retunPath = transferImage(filePath, desPath, 300, 300, 800L);
			if (null == retunPath) {
				return null;
			}

			ImageInfoBO imageInfoBO = ImageUtils.getResolutionNew(retunPath);
			if (null == imageInfoBO) {
				return null;
			}
			Integer widthId = imageInfoBO.getWidth();

			if (null == widthId) {
				return null;
			}
			int w = widthId;

			Integer heightId = imageInfoBO.getHeigth();
			if (null == heightId) {
				return null;
			}
			int h = heightId;
			if (w > 800 || h > 800 || w < 300 || h < 300) {
				return null;
			}
		} catch (Exception e) {
			log.error("transferImageByPP 调用错误 路径为" + filePath, e);
			throw e;
		}
		return retunPath;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(JSON.toJSONString(getResolutionNew("/Users/peng/Pictures/com.tencent.ScreenCapture/QQ20140924-1@2x.png")));
		// transferImageResolution("D:\\1.jpg");

		System.out.println(transferImageByPP("G:\\drp\\branch\\branch-2014-11-8-item\\drp-test\\1417196003707.jpg"));

	}

	public static String getFormatInFile(File f) {
		return getFormatName(f);
	}

	private static String getFormatName(Object o) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(o);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = iter.next();
			iis.close();
			return reader.getFormatName();
		} catch (IOException e) {
		}
		return null;
	}
}
