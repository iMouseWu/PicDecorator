package com.pic.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pic.DecoratorContext;
import com.pic.bo.TupleBO;
import com.pic.utils.Im4javaImageUtil;
import com.pic.utils.ImageUtils;

/**
 * 长度装饰器
 * 
 * @author Wuhao
 */
public class LhDecorator extends Decorator {

	@Override
	public void operate(DecoratorContext decoratorContext) {
		decorator.operate(decoratorContext);
		List<String> newPath = new ArrayList<String>();
		List<String> paths = decoratorContext.getFilePaths();
		try {
			for (String path : paths) {
				Map<String, Integer> map = ImageUtils.getResolution(path);
				int w = map.get("width");
				int h = map.get("height");
				if (w > h) {
					TupleBO<Integer, Integer> tupleBO = dealwithWH(w, h, decoratorContext.getHeightUpper(), decoratorContext.getHeightFloor());
					w = tupleBO.getA();
					h = tupleBO.getB();
				} else {
					TupleBO<Integer, Integer> tupleBO = dealwithWH(h, w, decoratorContext.getHeightUpper(), decoratorContext.getHeightFloor());
					h = tupleBO.getA();
					w = tupleBO.getB();
				}
				Im4javaImageUtil.cutImage(w, path, path);
				newPath.add(path);
			}
		} catch (Exception e) {

		}
	}

	protected TupleBO<Integer, Integer> dealwithWH(int a, int b, int desA, int desB) {
		TupleBO<Integer, Integer> tupleBO = new TupleBO<Integer, Integer>();
		if (a > desA && b > desA) {
			b = desA * b / a;
			a = desA;
		} else if (a > desA && b < desA && desB < b) {
			b = desA * b / a;
			a = desA;
		} else if (a > desA && b < desB) {
			b = desA * b / a;
			a = desA;
		} else if (a < desA && a > desB && b > desB && b < desA) {
			// TODO
		} else if ((desB < a && a < desA && b < desB) || (a < desB && b < desB)) {
			if ((desB / b) > (desA / a)) {
				b = desA * b / a;
				a = desA;
			} else {
				a = desB * a / b;
				b = desB;
			}
		}
		tupleBO.setA(a);
		tupleBO.setB(b);
		return tupleBO;
	}

}
