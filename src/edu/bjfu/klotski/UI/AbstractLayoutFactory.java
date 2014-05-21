package edu.bjfu.klotski.UI;

import edu.bjfu.klotski.core.Interfaces.ILayoutFactory;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Mediator.Mediator;


public abstract class AbstractLayoutFactory implements ILayoutFactory
{
	protected Mediator mediator;

	
	public void setMediator(Mediator value)
	{
		mediator = value;
	}

	public abstract Layout Create();
	
	public abstract Layout Create(String xml);
}