package edu.bjfu.klotski.core.Layout.Chessman;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import edu.bjfu.klotski.core.BaseComponent.*;
import edu.bjfu.klotski.core.Layout.*;

public class General extends Chessman {

	public General() {

	}
	public General(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}
	
	private boolean CanMoveUp(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x , position.y - 1)
		&& blankPosition.IsBlank(position.x + 1, position.y - 1))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y - 1;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y + 1;
			newBlankPosition.Pos2.x = position.x + 1;
			newBlankPosition.Pos2.y = position.y + 1;

			return true;
		}

		return false;
	}

	private boolean CanMoveDown(BlankPosition blankPosition)
	{
		
		if(blankPosition.IsBlank(position.x , position.y + 2) 
		&& blankPosition.IsBlank(position.x + 1, position.y + 2))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y + 1;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x + 1;
			newBlankPosition.Pos2.y = position.y;

			return true;
		}

		return false;
	}

	private boolean CanMoveLeft(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x - 1 , position.y) 
		&& blankPosition.IsBlank(position.x - 1, position.y + 1))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x - 1;
			newPosition.y = position.y;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x + 1;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x + 1;
			newBlankPosition.Pos2.y = position.y + 1;
			
            return true;
		}

		return false;
	}

	private boolean CanMoveRight(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x + 2 , position.y) 
		&& blankPosition.IsBlank(position.x + 2, position.y + 1))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x + 1;
			newPosition.y = position.y;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x;
			newBlankPosition.Pos2.y = position.y + 1;
			
			return true;
		}

		return false;
	}
	

	
	public  ChessmanType chessmanType() {
		return ChessmanType.General;
	}
	

	
	public void CheckAvailableSteps(BlankPosition blankPosition,Layout layout) {
		// TODO Auto-generated method stub

//		 InvocationHandler handler = new CallBackHandler(layout);
//		 
//		 Layout proxy = (Layout) Proxy.newProxyInstance(
//				  layout.getClass().getClassLoader(),
//				  layout.getClass().getInterfaces(),
//                  handler); 

		if(CanMoveUp(blankPosition))
			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Up);

		if(CanMoveDown(blankPosition))
			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Down);

		if(CanMoveLeft(blankPosition))
			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Left);

		if(CanMoveRight(blankPosition))
			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Right);
		return;
	}
	
	public void CheckAvailableSteps(Layout layout) {

		if(CanMoveUp(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Up);
		
		if(CanMoveDown(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Down);
		
		if(CanMoveLeft(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Left);
		
		if(CanMoveRight(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Right);
		return;
	}
	
	public void Move(int index,MoveMethod mType,Layout layout)
	{
		switch(mType)
		{
    		case Left:
    			CanMoveLeft(layout.getBlankPosition());
    			break;
    		case Right:
    			CanMoveRight(layout.getBlankPosition());
    			break;  			
    		case Up:
    			CanMoveUp(layout.getBlankPosition());
    			break;
    		case Down:
    			CanMoveDown(layout.getBlankPosition());
    			
    			break;
		}
		layout.Refresh(index,newPosition, newBlankPosition, mType);

	}

}
