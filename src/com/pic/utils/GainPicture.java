package com.pic.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GainPicture {

	static Logger log = LoggerFactory.getLogger(GainPicture.class);

	public static String gainPicture(String imageUrl, String path) {
		return gainPicture(imageUrl, path, null);
	}

	public static String gainPicture(String imageUrl, String path, String newName) {
		String disPath = null;
		boolean isDown = false;
		try {
			String imageName = null;
			if (null != newName) {
				imageName = newName + imageUrl.substring(imageUrl.lastIndexOf('.'));
			} else {
				imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
			}

			disPath = path + File.separator + imageName;
			isDown = FileUtils.downloadImgWebToLocal(imageUrl, disPath);
			if (!isDown) {
				isDown = FileUtils.downloadImgWebToLocal(imageUrl, disPath);
			}
		} catch (IOException e) {
			log.info("下拉商品错误，图片url:" + imageUrl, e);
		}
		if (!isDown) {
			disPath = null;
		}
		return disPath;
	}

	public static String[] gainPicture(String[] imgUrls, String path) {
		if (imgUrls == null || path == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < imgUrls.length; i++) {
			String imgPath = GainPicture.gainPicture(imgUrls[i], path);
			if (null == imgPath) {
				continue;
			}
			list.add(imgPath);
		}
		if (list.isEmpty()) {
			return null;
		} else {
			return list.toArray(new String[] {});
		}
	}
}
