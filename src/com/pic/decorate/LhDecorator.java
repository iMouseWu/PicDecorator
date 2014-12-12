package com.pic.decorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.DecoratorContext;
import com.pic.bo.ImageInfoBO;
import com.pic.bo.TupleBO;
import com.pic.utils.Im4javaImageUtil;
import com.pic.utils.ImageUtils;

/**
 * 长度装饰器 TODO 注意int的除法
 * 
 * @author Wuhao
 */
public class LHDecorator extends Decorator {

	private Logger log = LoggerFactory.getLogger(AspectDecorator.class);
	private int upper;
	private int floor;

	@Override
	public void operation(DecoratorContext context) {
		component.operation(context);

		String path = context.getFilePaths();
		upper = context.getWidthUpper();
		floor = context.getWidthFloor();
		try {
			ImageInfoBO image = new ImageInfoBO(path);
			int width = image.getWidth();
			int height = image.getHeigth();
			if (isNumInRange(width) && isNumInRange(height)) {
				return;
			}
			int background = 0;
			if (width > height) {
				TupleBO<Integer, Integer> tupleBO = dealwithHL(width, height, upper, floor);
				height = tupleBO.second.intValue();
				width = tupleBO.first.intValue();
				background = width;
			} else {
				TupleBO<Integer, Integer> tupleBO = dealwithHL(height, width, upper, floor);
				height = tupleBO.first.intValue();
				width = tupleBO.second.intValue();
				background = height;
			}
			Im4javaImageUtil.scaleImage(width, height, path, path);
			if (isNumInRange(width) && isNumInRange(height)) {
				return;
			}
			ImageUtils.createImgWithBacg(path, path, background);
		} catch (Exception e) {
			log.warn("LHDecorator operation error", e);
		}
	}

	protected TupleBO<Integer, Integer> dealwithHL(int bigNum, int smallNum, int upper, int floor) {
		// 1.bigNum>upper smallNum>upper
		// 2.bigNum>upper upper>smallNum>floor
		// 3.bigNum>upper floor>smallNum
		// 4.upper>bigNum>ssmallNum upper>smallNum>floor
		// 5.upper>bigNum>smallNum floor>smallNum
		// 6.floor>bigNum smallNum>bigNum
		TupleBO<Integer, Integer> tupleBO = new TupleBO<Integer, Integer>(upper, smallNum * upper / bigNum);
		return tupleBO;
	}

	private boolean isNumInRange(int num) {
		return floor < num && num < upper;

	}

}
