package edu.bjfu.klotski.UI;

import edu.bjfu.klotski.core.CircularList.CircularLinkedList;
import edu.bjfu.klotski.core.HashTable.HashTable;
import edu.bjfu.klotski.core.Interfaces.ILayoutFactory;
import edu.bjfu.klotski.core.Mediator.Mediator;
import edu.bjfu.klotski.core.TreeLinkedList.TreeLinkedList;

public class ConsoleMain {

	/**
	 * @param args
	 */
	
	static ILayoutFactory layoutFactory;
	static Mediator mediator;
	static ResultHandler resultHandler = new ResultHandler();
	
	public static void Run() {
		
		layoutFactory = new LayoutFactory();
		mediator = new Mediator();
		layoutFactory.setMediator(mediator) ;
		
		mediator.setCircleList(new CircularLinkedList(layoutFactory));
		mediator.setHashTable(new HashTable())  ;
		mediator.setTreeList( new TreeLinkedList());
		mediator.setResultHandler(resultHandler)  ;
		
		
		mediator.Init(1000);
		if(!mediator.BeginProcess())
		{
			System.out.print("此布局无解！");
		}
		else
		{
			System.out.print("ok");
		}
		
	}

}
