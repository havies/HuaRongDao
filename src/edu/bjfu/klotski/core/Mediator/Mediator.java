package edu.bjfu.klotski.core.Mediator;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.Interfaces.*;
import edu.bjfu.klotski.core.Layout.Layout;

public class Mediator {

	private IHashTable hashTable;
	private ICircularList circleList;
	private ITreeList treeList;
	private IResultHandler resultHandler;
	public  ChessStep[] result=null;
	
	public void setHashTable(IHashTable hashTable) {
		this.hashTable = hashTable;
	}
	public void setCircleList(ICircularList circleList) {
		this.circleList = circleList;
	}
	public void setTreeList(ITreeList treeList) {
		this.treeList = treeList;
	}
	public void setResultHandler(IResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	}
	
	
	public void Init(int n)
	{
		for(int i = 0; i<n; i++)
			circleList.insertNode();
		
		ChessStep c = circleList.Initialize();
		treeList.ClearAll();
		hashTable.Release();

		treeList.insertNode(c);
		hashTable.Insert(c.layout);
		circleList.setMediator(this);
	}

	public void Release()
	{
		circleList.ClearAll();
		treeList.ClearAll();
		hashTable.Release();
	}

	public void CheckStep(ChessStep cStep)
	{
		try
		{
			if(hashTable.Insert(cStep.layout))
			{
				circleList.ConfirmAllocation();
				treeList.insertNode(cStep);
			}
		}
		catch(Exception ex)
		{
			//throw ex;
		}
	}

	public Layout AllocateLayout()
	{
		return circleList.AllocateLayout();
	}

	public void Finished(ChessStep cStep)
	{
		circleList.Stop();
		result = treeList.TraceResult(cStep);

		resultHandler.HandleResult(result);
		return;
	}

	public boolean BeginProcess()
	{
		return circleList.BeginProcess();
	}

	public void Stop()
	{
		circleList.Stop();
	}

	public void MoveCurrentToNext()
	{
		treeList.MoveCurrentToNext();
	}

	public void HandleInfo(int currentStep)
	{
		resultHandler.HandleInfo(currentStep);
	}
	

	
}
