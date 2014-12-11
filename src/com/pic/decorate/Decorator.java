package com.pic.decorate;

import com.pic.DecoratorContext;

public class Decorator {

	protected Decorator decorator;

	public void setDecorator(Decorator decorator) {
		this.decorator = decorator;
	}

	public void operation(DecoratorContext context) {
		decorator.operation(context);
	}
}
