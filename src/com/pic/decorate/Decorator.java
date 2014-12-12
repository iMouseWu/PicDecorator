package com.pic.decorate;

import com.pic.Component;
import com.pic.DecoratorContext;

public class Decorator implements Component {

	protected Component component;

	public void setComponent(Component component) {
		this.component = component;
	}

	public void operation(DecoratorContext context) {
		component.operation(context);
	}
}
