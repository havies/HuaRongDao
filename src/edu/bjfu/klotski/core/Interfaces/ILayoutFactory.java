package edu.bjfu.klotski.core.Interfaces;

import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Mediator.Mediator;

public interface ILayoutFactory {
	
	//public Mediator mediator = null;
	Layout Create();
	Layout Create(String xml);
	void setMediator(Mediator mediator);
	
	void setCurrentLayout(Layout l);
	
	Layout getCurrentLayout();
}
