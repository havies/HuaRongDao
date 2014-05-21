package edu.bjfu.klotski.core.Layout;

import android.util.Log;
import edu.bjfu.klotski.Log.LogException;
import edu.bjfu.klotski.Log.LogWriter;
import edu.bjfu.klotski.UI.ShowHelp;
import edu.bjfu.klotski.core.BaseComponent.BlankPosition;
import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.BaseComponent.ChessmanType;
import edu.bjfu.klotski.core.BaseComponent.MoveMethod;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;
import edu.bjfu.klotski.core.Mediator.Mediator;

public class Layout implements ICallBackDelegate{

	private Mediator mediator;
	private Chessman[] chessmen = new Chessman[10];

	private boolean gotTheAnswer = false;
	public  int current = 0;

	private BlankPosition blankPosition;
	
    public long layoutInt64;
    LogWriter logger = null;

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public Chessman[] getChessmen() {
		return chessmen;
	}

	public void setChessmen(Chessman[] chessmen) {
		this.chessmen = chessmen;
	}



	public BlankPosition getBlankPosition() {
		return blankPosition;
	}

	public void setBlankPosition(BlankPosition blankPosition) {
		if(this.blankPosition==null)
		{
			this.blankPosition=new BlankPosition();
		}
		this.blankPosition.Pos1.x = blankPosition.Pos1.x;
		this.blankPosition.Pos1.y = blankPosition.Pos1.y;
		this.blankPosition.Pos2.x = blankPosition.Pos2.x;
		this.blankPosition.Pos2.y = blankPosition.Pos2.y;
	}
	
	public boolean IsFinished()
	{
		for(int i=0; i<10; i++)
			if(chessmen[i].chessmanType() == ChessmanType.General && 
					chessmen[i].getPosition().x == 1 &&
					chessmen[i].getPosition().y == 3)
				return true;

		return false;
	}
	
	//===========================================================
	// 根据当前棋盘布局获取下一步可走的方案
	//===========================================================
	public void CheckAvailableSteps()
	{
//		CallBackDelegate callback = new CallBackDelegate(EncapsulateChessStep);
        
		
		for(current = 0; current<10; current++)
		{
			if(current==3||current==5||current==8||current==9)
			{
				//System.out.println(current+"  "+chessmen[current].chessmanType());
				//System.out.println("X="+chessmen[current].getposition().x+",Y="+chessmen[current].getposition().y);
				
			}

			chessmen[current].CheckAvailableSteps(blankPosition, this);
		}
			

	}	

	
	//===========================================================
	// 根据当前棋盘布局获取下一步可走的方案
	//===========================================================
	public void SimpleCheckAvailableSteps()
	{
//		CallBackDelegate callback = new CallBackDelegate(EncapsulateChessStep);
        
		
		for(current = 0; current<10; current++)
		{
			chessmen[current].ClearMoveMethodList();
			chessmen[current].CheckAvailableSteps(this);
		}
			

	}
	
	public void Refresh(int index,Position newPosition,BlankPosition newBlankPosition, MoveMethod mm) 
	{
		try
		{

			this.getChessmen()[index].setPosition(newPosition);
			this.setBlankPosition(newBlankPosition);
		}
		catch(Exception ex)
		{
			Log.e("ERR",ex.getMessage());
		}
	}
	
	public void CallBackDelegate(Position newPosition,BlankPosition newBlankPosition, MoveMethod mm){
		// TODO Auto-generated method stub
		if( gotTheAnswer )
			return;

		Layout l = mediator.AllocateLayout();
		
		// 生成新布局
		for(int i = 0; i<10; i++)
		{
			if(i == current)
				l.getChessmen()[i].setPosition(newPosition);
			else
				l.getChessmen()[i].setPosition(this.getChessmen()[i].getPosition());
				
			
		}

		l.layoutInt64=l.ToInt64();
		
		l.setBlankPosition(newBlankPosition);

		ChessStep cs = new ChessStep();
		cs.layout = l.ToInt64();
//		cs.Chessmen=new Chessman[l.getchessmen().length];
//		for(int i = 0; i<10; i++)
//		{
//			cs.Chessmen[i]=l.getchessmen()[i];
//			cs.Chessmen[i].setposition(l.getchessmen()[i].getposition());
//		}
		//cs.chessmanPosition = this.getChessmen()[current].getPosition();
		cs.chessmanPosition = new Position(this.chessmen[current].getPosition().x,this.chessmen[current].getPosition().y);// this.getChessmen()[current].getPosition();
		
		cs.moveMethod = mm;

//		int x = cs.chessmanPosition.x;
//        int y = cs.chessmanPosition.y;
//        int p = x + y * 4;
//        
//        System.out.printf("show layout:%s",l.layoutInt64);
//		System.out.println();        
//		ShowHelp.ShowLayout(l.layoutInt64,p);
		
		// 将封装好的ChessStep发送给mediator作进一步处理
		if(l.IsFinished())
		{
			gotTheAnswer = true;
			mediator.Finished(cs);
		}
		else
			mediator.CheckStep(cs);
	}	
	
	

	
	//===========================================================
	// 将当前棋局转换为一整数
	//===========================================================
	public long ToInt64()
	{

		long result = 0;
		long typeCode, positionCode;
		
		for(int i=0; i<10; i++)
		{
			typeCode =chessmen[i].chessmanType().ordinal();
			positionCode = chessmen[i].getPosition().y * 4 + chessmen[i].getPosition().x;
			result += typeCode << ((int)(3 * positionCode));
		}

		return result;
	}


}
