package com.pic.exception;

import java.io.IOException;

public class PicException extends IOException {

	private static final long serialVersionUID = 7622918966750947888L;

	public PicException(){
		super();
	}

	public PicException(String message, Throwable throwable){
		super(message, throwable);
	}

	public PicException(Throwable throwable){
		super(throwable);
	}

}
