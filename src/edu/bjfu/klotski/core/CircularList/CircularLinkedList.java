package edu.bjfu.klotski.core.CircularList;

import edu.bjfu.klotski.UI.ShowHelp;
import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Interfaces.ICircularList;
import edu.bjfu.klotski.core.Interfaces.ILayoutFactory;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Mediator.Mediator;

public class CircularLinkedList implements ICircularList{

	protected LinkedListNode current;
	protected LinkedListNode last;
	protected LinkedListNode allocate;
	protected LinkedListNode end;

	protected int count;
	protected ILayoutFactory layoutFactory;
	protected Mediator _mediator;

	private boolean stop = false;
	private int stepCounter = 1;
	
	public Mediator getMediator()
	{
		return _mediator;
	}
	
	public int Length()
	{
		return count;
	}
	
	public boolean IsEmpty()
	{
		return (current == null);
	}
	
	public CircularLinkedList(ILayoutFactory layoutFactory)
	{
		last = null;
		current = null;
		allocate = null;
		end = null;
		stepCounter = 1;
		count = 0;
		this.layoutFactory = layoutFactory;
	}
	
	
	public void ClearAll() {
		// TODO Auto-generated method stub
		last = null;
		current = null;
		allocate = null;
		end = null;
		stop = false;
		count = 0;
		stepCounter = 1;
	}

	
	public ChessStep Initialize() {
		// TODO Auto-generated method stub
		if(current == null)
			return null;

		last = current;
		allocate = current;
		stop = false;

		ChessStep c = new ChessStep();
		c.layout = current.info.ToInt64();
		stepCounter = 1;
		return c;
		
	}

	
	public void ConfirmAllocation() {
		// TODO Auto-generated method stub
		allocate = allocate.link;
	}

	
	public void insertNode() {
		// TODO Auto-generated method stub
		this.insertNode(layoutFactory.getCurrentLayout());
	}
	
	

	
	public boolean BeginProcess() {
		while(current != last.link)
		{
			current.info.CheckAvailableSteps();

			
			
			if(stop)
				return true;

			current = current.link;

			if(current != last.link)
				_mediator.MoveCurrentToNext();
		}
		
		if(this.NextStep())
		{
//			System.out.printf("show layout:%s",current.info.layoutInt64);
//			System.out.println();
//			Position chessmanPosition=current.info.get_chessmen()[current.info._current].get_position();
//			int x = chessmanPosition.x;
//	        int y = chessmanPosition.y;
//	        int p = x + y * 4;
//	        
//			ShowHelp.ShowLayout(current.info.layoutInt64,p);
			return BeginProcess();
		}
		else
			return false;
	}

	
	public void Stop() {
		// TODO Auto-generated method stub
		this.stop = true;
	}

	
	public Layout AllocateLayout() {
		// TODO Auto-generated method stub
		if(allocate.link == current)
		{
			end = allocate;
			insertNode(layoutFactory.Create());
		}
		
		return allocate.link.info;
	}
	
	
	
	private void insertNode(Layout newitem)
	{
		LinkedListNode trail;
		LinkedListNode newNode;

		newNode = new LinkedListNode();
		newNode.info = newitem;
		newNode.link = null;

		if(current == null)
		{   
			current = newNode;
			current.link = newNode;
			end = current;
		}
		else
		{
			trail = end.link;
			end.link = newNode;
			end = newNode;
			newNode.link = trail;
		}
		count++;
	}
	
	private boolean NextStep()
	{
		stepCounter++;
		_mediator.HandleInfo(stepCounter);

		if(allocate == last)
			return false; //棋局无解

		current = last.link;
		last = allocate;
		_mediator.MoveCurrentToNext();
		return true;
	}

	
	public void setMediator(Mediator mediator) {
		// TODO Auto-generated method stub
		this._mediator=mediator;
	}

}
