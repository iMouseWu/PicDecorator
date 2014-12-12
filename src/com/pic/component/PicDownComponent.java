package com.pic.component;

import java.io.File;

import com.pic.Component;
import com.pic.DecoratorContext;
import com.pic.utils.GainPicture;

public class PicDownComponent implements Component {

	@Override
	public void operation(DecoratorContext context) {
		File file = new File("");
		String rootPath = file.getAbsolutePath();
		String localPath = GainPicture.gainPicture(context.getPicOnlinePath(), rootPath, System.currentTimeMillis() + "");
		context.setFilePaths(localPath);
	}
}
