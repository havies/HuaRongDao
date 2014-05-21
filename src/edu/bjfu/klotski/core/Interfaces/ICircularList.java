package edu.bjfu.klotski.core.Interfaces;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Mediator.Mediator;


public interface ICircularList {

	final  Mediator mediator = null;

	void ClearAll();
	
	ChessStep Initialize();
	
	void ConfirmAllocation();
	
	void insertNode();
	

	boolean BeginProcess();

	void Stop();

	Layout AllocateLayout();

	void setMediator(Mediator mediator);
}
