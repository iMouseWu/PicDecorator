package com.pic.decorate;

import com.pic.DecoratorContext;

public abstract class Decorator {

	protected Decorator decorator;

	public void setDecorator(Decorator decorator) {
		this.decorator = decorator;
	}

	public abstract void operation(DecoratorContext decoratorContext);
}
