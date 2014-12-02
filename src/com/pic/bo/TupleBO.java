package com.pic.bo;

/**
 * 用于函数的多值返回
 * 
 * @author Wuhao
 * @param <A>
 * @param <B>
 */
public class TupleBO<A, B> {

	private A a;
	private B b;

	public TupleBO(){
		super();
	}

	public TupleBO(A a, B b){
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

}
