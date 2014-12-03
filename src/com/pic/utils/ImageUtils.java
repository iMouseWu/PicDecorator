package com.pic.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

import com.pic.bo.ImageInfoBO;

public class ImageUtils {

	private static Logger log = Logger.getLogger(ImageUtils.class);

	public static ImageInfoBO getPicInfo(String path) {
		ImageInfoBO info = null;
		ImageReader reader = null;
		ImageInputStream stream = null;
		try {
			String suffix = getSuffixName(new File(path));
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

	public static String getSuffixName(File file) {
		ImageInputStream iis = null;
		String suffix = null;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = iter.next();
			suffix = reader.getFormatName();
		} catch (IOException e) {
			log.error("getSuffixName error the file path is" + file.getAbsolutePath(), e);
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (IOException e) {
					log.warn("iis close error", e);
				}
			}
		}
		return suffix;
	}

	public static String getSuffixName(String filePath) {
		File file = new File(filePath);
		ImageInputStream iis = null;
		String suffix = null;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = iter.next();
			suffix = reader.getFormatName();
		} catch (IOException e) {
			log.error("getSuffixName error the file path is" + file.getAbsolutePath(), e);
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (IOException e) {
					log.warn("iis close error", e);
				}
			}
		}
		return suffix;
	}
}
