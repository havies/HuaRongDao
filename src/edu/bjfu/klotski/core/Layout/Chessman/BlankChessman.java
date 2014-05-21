package edu.bjfu.klotski.core.Layout.Chessman;

import edu.bjfu.klotski.core.BaseComponent.BlankPosition;
import edu.bjfu.klotski.core.BaseComponent.ChessmanType;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Layout.Layout;

public class BlankChessman extends  Chessman{

	public BlankChessman(Position pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}

	
	public  ChessmanType chessmanType() {
		return ChessmanType.Blank;
	}

	
	public void CheckAvailableSteps(BlankPosition _blankPosition,Layout layout) 
	{
		return;
	}
}
