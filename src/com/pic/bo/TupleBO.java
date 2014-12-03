package com.pic.bo;

/**
 * 用于函数的多值返回
 * 
 * @author Wuhao
 * @param <A>
 * @param <B>
 */
public class TupleBO<A, B> {

	public final A first;
	public final B second;

	public TupleBO(A first, B second){
		this.first = first;
		this.second = second;
	}

}
