package com.pic;

/*
 * 装饰器上下文，就是装饰的条件.暂时只支持宽度上限和高度上限是一样的情况
 */
public class DecoratorContext {

	public final static Double DEFAULTUPPER = 300D;

	public final static Double DEFAULTFLOOR = 800D;
	/**
	 * 期望上限
	 */
	private Double widthUpper;
	/**
	 * 期望下限
	 */
	private Double widthFloor;

	/**
	 * 期望高比宽或者宽高比的上限。初步认为这个值大于1
	 */
	private Double aspectContrast;
	/**
	 * 期望图片大小,以kb为单位
	 */
	private Double size;

	/**
	 * 图片路径
	 */
	private String filePaths;

	public String getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}

	public Double getAspectContrast() {
		return aspectContrast;
	}

	public void setAspectContrast(Double aspectContrast) {
		this.aspectContrast = aspectContrast;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public Double getWidthUpper() {
		if (null == widthUpper) {
			return DEFAULTUPPER;
		}
		return widthUpper;
	}

	public void setWidthUpper(Double widthUpper) {
		this.widthUpper = widthUpper;
	}

	public Double getWidthFloor() {
		if (null == widthFloor) {
			return DEFAULTFLOOR;
		}
		return widthFloor;
	}

	public void setWidthFloor(Double widthFloor) {
		this.widthFloor = widthFloor;
	}

}
