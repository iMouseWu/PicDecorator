package com.pic.component;

import com.pic.Component;
import com.pic.DecoratorContext;
import com.pic.utils.GainPicture;

public class PicDownComponent implements Component {

	@Override
	public void operation(DecoratorContext context) {
		String localPath = GainPicture.gainPicture(context.getPicOnlinePath(), context.getRootPath(), context.getPicName());
		context.setFilePaths(localPath);
	}
}
