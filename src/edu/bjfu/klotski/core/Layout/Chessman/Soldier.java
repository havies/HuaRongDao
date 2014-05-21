package edu.bjfu.klotski.core.Layout.Chessman;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import edu.bjfu.klotski.core.BaseComponent.*;
import edu.bjfu.klotski.core.Layout.*;

public class Soldier extends Chessman {

	public Soldier() {

	}
	
	public Soldier(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}
	
	private boolean CanMoveUp(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x , position.y - 1))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y - 1;

			// 设置新的空白位置
			if(blankPosition.Pos1.x == position.x && blankPosition.Pos1.y == position.y - 1)
				ResetNewBlankPosition1(blankPosition);
			else
				ResetNewBlankPosition2(blankPosition);

			return true;
		}

		return false;
	}

	private boolean CanMoveDown(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x , position.y + 1))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y + 1;
			
			// 设置新的空白位置
			if(blankPosition.Pos1.x == position.x && blankPosition.Pos1.y == position.y + 1)
				ResetNewBlankPosition1(blankPosition);
			else
				ResetNewBlankPosition2(blankPosition);

			
			return true;
		}

		return false;
	}

	private boolean CanMoveLeft(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x - 1 , position.y))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x - 1;
			newPosition.y = position.y;

			// 设置新的空白位置
			if(blankPosition.Pos1.x == position.x - 1 && blankPosition.Pos1.y == position.y)
				ResetNewBlankPosition1(blankPosition);
			else
				ResetNewBlankPosition2(blankPosition);

			
			return true;
		}

		return false;
	}

	private boolean CanMoveRight(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x + 1 , position.y))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x + 1;
			newPosition.y = position.y;

			// 设置新的空白位置
			if(blankPosition.Pos1.x == position.x + 1 && blankPosition.Pos1.y == position.y)
				ResetNewBlankPosition1(blankPosition);
			else
				ResetNewBlankPosition2(blankPosition);

			return true;
		}

		return false;
	}

	private boolean CanMoveUp2(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x , position.y - 1) && blankPosition.IsBlank(position.x , position.y - 2))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y - 2;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x;
			newBlankPosition.Pos2.y = position.y - 1;

			return true;
		}

		return false;
	}

	private boolean CanMoveDown2(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x , position.y + 1) && blankPosition.IsBlank(position.x , position.y + 2))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x;
			newPosition.y = position.y + 2;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x;
			newBlankPosition.Pos2.y = position.y + 1;
			
			return true;
		}

		return false;
	}

	private boolean CanMoveLeft2(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x - 1 , position.y) && blankPosition.IsBlank(position.x - 2 , position.y))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x - 2;
			newPosition.y = position.y;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x - 1;
			newBlankPosition.Pos2.y = position.y;
			
			return true;
		}

		return false;
	}
	
	private boolean CanMoveRight2(BlankPosition blankPosition)
	{
		if(blankPosition.IsBlank(position.x + 1 , position.y) 
		&& blankPosition.IsBlank(position.x + 2 , position.y))
		{
			//System.out.println(new Throwable().getStackTrace()[0]);
			// 设置棋子新位置
			newPosition.x = position.x + 2;
			newPosition.y = position.y;

			// 设置新的空白位置
			newBlankPosition.Pos1.x = position.x;
			newBlankPosition.Pos1.y = position.y;
			newBlankPosition.Pos2.x = position.x + 1;
			newBlankPosition.Pos2.y = position.y;
			
			return true;
		}

		return false;
	}

	private void CanMoveTurning(BlankPosition blankPosition, Layout callback)
	{
		if(CanMoveUp(blankPosition))
		{
			// 上移回调
			callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Up);

			newPosition.y = position.y - 1;

			if(blankPosition.IsBlank(position.x + 1 , position.y - 1)) // 右上
			{
				newPosition.x = position.x + 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x;
				newBlankPosition.Pos2.y = position.y - 1;

				// 右上回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.TurningRightUp);
				return;
			}

			if( blankPosition.IsBlank(position.x - 1 , position.y - 1)) // 左上
			{
				newPosition.x = position.x - 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x;
				newBlankPosition.Pos2.y = position.y - 1;

				// 左上回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.TurningLeftUp);
				return;
			}
		}

		if(CanMoveDown(blankPosition))
		{
			// 下移回调
			callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Down);

			newPosition.y = position.y + 1;

			if(blankPosition.IsBlank(position.x + 1 , position.y + 1)) // 右下
			{
				newPosition.x = position.x + 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x;
				newBlankPosition.Pos2.y = position.y + 1;
				
				// 右下回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.TurningRightDown);
				return;
			}
			
			if(blankPosition.IsBlank(position.x - 1 , position.y + 1)) // 左下
			{
				newPosition.x = position.x - 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x;
				newBlankPosition.Pos2.y = position.y + 1;
				
				// 左下回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.TurningLeftDown);
				return;
			}
		}

		if(CanMoveLeft(blankPosition))
		{
			// 左回调
			callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Left);

			newPosition.x = position.x - 1;
			if(blankPosition.IsBlank(position.x - 1 , position.y - 1)) // 左上
			{
				newPosition.y = position.y - 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x - 1;
				newBlankPosition.Pos2.y = position.y;
				
				// 左上回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Turning);
				return;
			}
			if(blankPosition.IsBlank(position.x - 1 , position.y + 1)) // 左下
			{
				newPosition.y = position.y + 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x - 1;
				newBlankPosition.Pos2.y = position.y;
				
				// 左下回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Turning);
				return;
			}
		}

		if(CanMoveRight(blankPosition))
		{
			// 右回调
			callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Right);

			newPosition.x = position.x + 1;
			if(blankPosition.IsBlank(position.x + 1 , position.y - 1))  // 右上
			{
				newPosition.y = position.y - 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x + 1;
				newBlankPosition.Pos2.y = position.y;
				
				// 右上回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Turning);
				return;
			}
			if(blankPosition.IsBlank(position.x + 1 , position.y + 1))  // 右下
			{
				newPosition.y = position.y + 1;

				// 设置新的空白位置
				newBlankPosition.Pos1.x = position.x;
				newBlankPosition.Pos1.y = position.y;
				newBlankPosition.Pos2.x = position.x + 1;
				newBlankPosition.Pos2.y = position.y;
				
				// 右下回调
				callback.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Turning);
				return;
			}
		}
	}

	private void ResetNewBlankPosition1(BlankPosition blankPosition)
	{
		newBlankPosition.Pos1.x = position.x;
		newBlankPosition.Pos1.y = position.y;
		newBlankPosition.Pos2.x = blankPosition.Pos2.x;
		newBlankPosition.Pos2.y = blankPosition.Pos2.y;
	}

	private void ResetNewBlankPosition2(BlankPosition blankPosition)
	{
		newBlankPosition.Pos1.x = blankPosition.Pos1.x;
		newBlankPosition.Pos1.y = blankPosition.Pos1.y;
		newBlankPosition.Pos2.x = position.x;
		newBlankPosition.Pos2.y = position.y;
	}
	
	
	public  ChessmanType chessmanType() {
		return ChessmanType.Solider;
	}

	
	public void CheckAvailableSteps(BlankPosition blankPosition,Layout layout) {
		// TODO Auto-generated method stub
//		InvocationHandler handler = new CallBackHandler(layout);
//		 
//		Layout proxy = (Layout) Proxy.newProxyInstance(
//				  layout.getClass().getClassLoader(),
//				  layout.getClass().getInterfaces(),
//                  handler); 
		
		
//		CanMoveTurning(blankPosition, layout);
//
//		if(CanMoveUp2(blankPosition))
//			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Up2);
//
//		if(CanMoveDown2(blankPosition))
//			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Down2);
//
//		if(CanMoveLeft2(blankPosition))
//			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Left2);
//
//		if(CanMoveRight2(blankPosition))
//			layout.CallBackDelegate(newPosition, newBlankPosition, MoveMethod.Right2);

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

		if(CanMoveLeft(layout.getBlankPosition()))
			lsMoveMethod.add( MoveMethod.Left);

		if(CanMoveRight(layout.getBlankPosition()))
			lsMoveMethod.add( MoveMethod.Right);

		if(CanMoveUp(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Up);

		if(CanMoveDown(layout.getBlankPosition()))
			lsMoveMethod.add(MoveMethod.Down);

//		if(CanMoveUp2(layout.getBlankPosition()))
//			lsMoveMethod.add(MoveMethod.Up2);
//
//		if(CanMoveDown2(layout.getBlankPosition()))
//			lsMoveMethod.add( MoveMethod.Down2);
//		
//		if(CanMoveLeft2(layout.getBlankPosition()))
//			lsMoveMethod.add( MoveMethod.Left2);
//
//		if(CanMoveRight2(layout.getBlankPosition()))
//			lsMoveMethod.add(  MoveMethod.Right2);
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
