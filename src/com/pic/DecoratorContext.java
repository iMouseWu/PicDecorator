package com.pic;

import java.util.List;

/*
 * 装饰器上下文，就是装饰的条件
 */
public class DecoratorContext {

	/**
	 * 期望宽度上限
	 */
	private Integer widthUpper;
	/**
	 * 期望宽度下限
	 */
	private Integer widthFloor;

	/**
	 * 期望高度上限
	 */
	private Integer heightUpper;
	/**
	 * 期望高度下限
	 */
	private Integer heightFloor;
	/**
	 * 期望宽比高
	 */
	private Double wbh;
	/**
	 * 期望高比宽
	 */
	private Double hbw;
	/**
	 * 期望图片大小,以kb为单位
	 */
	private Double size;

	/**
	 * 图片路径
	 */
	private List<String> filePaths;

	public List<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
	}

	public Integer getWidthUpper() {
		return widthUpper;
	}

	public void setWidthUpper(Integer widthUpper) {
		this.widthUpper = widthUpper;
	}

	public Integer getWidthFloor() {
		return widthFloor;
	}

	public void setWidthFloor(Integer widthFloor) {
		this.widthFloor = widthFloor;
	}

	public Integer getHeightUpper() {
		return heightUpper;
	}

	public void setHeightUpper(Integer heightUpper) {
		this.heightUpper = heightUpper;
	}

	public Integer getHeightFloor() {
		return heightFloor;
	}

	public void setHeightFloor(Integer heightFloor) {
		this.heightFloor = heightFloor;
	}

	public Double getWbh() {
		return wbh;
	}

	public void setWbh(Double wbh) {
		this.wbh = wbh;
	}

	public Double getHbw() {
		return hbw;
	}

	public void setHbw(Double hbw) {
		this.hbw = hbw;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

}
