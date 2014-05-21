package edu.bjfu.klotski.core.BaseComponent;

public class BlankPosition {


	public Position Pos1;
	public Position Pos2;
	
	public BlankPosition()
	{
		this.Pos1 = new Position(-1,-1);
		this.Pos2 = new Position(-1,-1);
	}

	public BlankPosition(Position pos1, Position pos2)
	{
		this.Pos1 = pos1;
		this.Pos2 = pos2;
	}

	public boolean IsBlank(int x, int y)
	{
		if( x == Pos1.x && y == Pos1.y)
			return true;
		if( x == Pos2.x && y == Pos2.y)
			return true;

		return false;
	}
}
