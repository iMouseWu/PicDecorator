package com.pic;

/*
 * 装饰器上下文，就是装饰的条件.暂时只支持宽度上限和高度上限是一样的情况
 */
public class DecoratorContext {

	public final static Integer DEFAULTUPPER = 300;

	public final static Integer DEFAULTFLOOR = 800;
	/**
	 * 期望上限
	 */
	private Integer widthUpper;
	/**
	 * 期望下限
	 */
	private Integer widthFloor;

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

	public Integer getWidthUpper() {
		if (null == widthUpper) {
			return DEFAULTUPPER;
		}
		return widthUpper;
	}

	public void setWidthUpper(Integer widthUpper) {
		this.widthUpper = widthUpper;
	}

	public Integer getWidthFloor() {
		if (null == widthFloor) {
			return DEFAULTFLOOR;
		}
		return widthFloor;
	}

	public void setWidthFloor(Integer widthFloor) {
		this.widthFloor = widthFloor;
	}

}
