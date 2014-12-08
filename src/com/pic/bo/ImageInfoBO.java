package com.pic.bo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.exception.PicException;

/**
 * 图片信息类
 * 
 * @author wuhao
 */
public class ImageInfoBO {

	private Logger log = LoggerFactory.getLogger(ImageInfoBO.class);

	private int width;
	private int heigth;
	private long size;
	// 根路径
	private String rootPath;
	// 名称
	private String name;
	// 后缀
	private String suffix;

	public ImageInfoBO(String path) throws PicException{
		File file = new File(path);
		ImageInputStream imgeIS = null;
		try {
			imgeIS = ImageIO.createImageInputStream(new File(path));
			Iterator<ImageReader> imageIter = ImageIO.getImageReaders(imgeIS);
			if (imageIter.hasNext()) {
				ImageReader reader = imageIter.next();
				suffix = reader.getFormatName();
			}
			imageIter = ImageIO.getImageReadersByFormatName(suffix);
			if (imageIter.hasNext()) {
				ImageReader reader = imageIter.next();
				reader.setInput(imgeIS, true);
				width = reader.getWidth(reader.getMinIndex());
				heigth = reader.getHeight(reader.getMinIndex());
			}

		} catch (IOException e) {
			throw new PicException("create imageInfoBO error", e);
		} finally {
			if (null != imgeIS) {
				try {
					imgeIS.close();
				} catch (IOException e) {
					log.info("close ImageInputStream error", e);
				}
			}
		}
		int lastSeq = path.lastIndexOf(File.separator) + 1;
		int lastdot = path.lastIndexOf(".");
		rootPath = path.substring(0, lastSeq);
		name = path.substring(lastSeq, lastdot);
		size = file.length();
	}

	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public long getSize() {
		return size;
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getName() {
		return name;
	}

	public String getSuffix() {
		return suffix;
	}

}
