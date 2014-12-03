package com.pic.decorate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.bo.TupleBO;
import com.pic.utils.Im4javaImageUtil;
import com.pic.utils.ImageUtils;

/**
 * 长度装饰器
 * 
 * @author Wuhao
 */
public class LHDecorator extends Decorator {

	@Override
	public void operation(DecoratorContext decoratorContext) {
		decorator.operation(decoratorContext);
		String path = decoratorContext.getFilePaths();
		try {
			ImageInfoBO image = ImageUtils.getPicInfo(path);
			int width = image.getWidth();
			int height = image.getHeigth();
			int background = 0;
			if (width > height) {
				TupleBO<Double, Double> tupleBO = dealwithHL(width, height, decoratorContext.getWidthUpper(), decoratorContext.getWidthFloor());
				background = width = tupleBO.first.intValue();
				height = tupleBO.second.intValue();
			} else {
				TupleBO<Double, Double> tupleBO = dealwithHL(height, width, decoratorContext.getWidthUpper(), decoratorContext.getWidthFloor());
				background = height = tupleBO.first.intValue();
				width = tupleBO.second.intValue();
			}
			Im4javaImageUtil.cutImage(width, path, path);
			backgroundPic(path, background);
		} catch (Exception e) {

		}
	}

	protected TupleBO<Double, Double> dealwithHL(double bigNum, double smallNum, double upper, double floor) {
		// TODO
		// 1.bigNum>upper smallNum>upper
		// 2.bigNum>upper upper>smallNum>floor
		// 3.bigNum>upper floor>smallNum
		// 4.upper>bigNum>ssmallNum upper>smallNum>floor
		// 5.upper>bigNum>smallNum floor>smallNum
		// 6.floor>bigNum smallNum>bigNum
		double bigValue = bigNum;
		double smallValue = smallNum;

		if (!(floor < bigNum && bigNum < upper && upper < smallNum && upper < floor)) {
			smallValue = smallNum * upper / bigNum;
		}

		TupleBO<Double, Double> tupleBO = new TupleBO<Double, Double>(bigValue, smallValue);
		return tupleBO;
	}

	private void backgroundPic(String filePath, int background) throws IOException {
		File file = new File(filePath);
		BufferedImage small = ImageIO.read(file);
		BufferedImage image = new BufferedImage(background, background, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect(0, 0, background, background);
		int x = (image.getWidth() - small.getWidth()) / 2;
		int y = (image.getHeight() - small.getHeight()) / 2;
		graphics.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
		graphics.dispose();
		ImageIO.write(image, ImageUtils.getSuffixName(filePath), file);
	}
}
