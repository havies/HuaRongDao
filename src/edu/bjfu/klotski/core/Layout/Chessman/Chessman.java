package edu.bjfu.klotski.core.Layout.Chessman;

import java.util.ArrayList;
import java.util.List;
import edu.bjfu.klotski.core.BaseComponent.*;
import edu.bjfu.klotski.core.Layout.*;

public class Chessman {

	protected ChessmanType chessmanTpe=ChessmanType.Blank;
	protected Position position=new Position(-1,-1);

	protected Position newPosition = new Position(-1,-1);
	protected BlankPosition newBlankPosition = new BlankPosition();
	
	protected List<MoveMethod> lsMoveMethod=new ArrayList<MoveMethod>();
	
	protected String  imageName="";
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Chessman()
	{
		this.position.x =-1;
		this.position.y = -1;
	}
	
	public Chessman(Position pos)
	{
		this.position.x = pos.x;
		this.position.y = pos.y;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position.x = position.x;
		this.position.y = position.y;
	}

	public Position getNewPosition() {
		return newPosition;
	}

	public void setNewPosition(Position newPosition) {
		this.newPosition.x = newPosition.x;
		this.newPosition.y = newPosition.y;
	}

	public BlankPosition getNewBlankPosition() {
		return newBlankPosition;
	}

	public void setNewBlankPosition(BlankPosition newBlankPosition) {
		this.newBlankPosition.Pos1.x = newBlankPosition.Pos1.x;
		this.newBlankPosition.Pos1.y = newBlankPosition.Pos1.y;
		this.newBlankPosition.Pos2.x = newBlankPosition.Pos2.x;
		this.newBlankPosition.Pos2.y = newBlankPosition.Pos2.y;

	}
	
	public  ChessmanType chessmanType() {
		return ChessmanType.Blank;
	}
	
	public  void setChessmanType(ChessmanType ctype) {
		chessmanTpe=ctype;
	}
	
	public List<MoveMethod> getMoveMethodList()
	{
		return this.lsMoveMethod;
	}
	
	
	
	public void ClearMoveMethodList()
	{
		lsMoveMethod.clear();
	}
	
	public void CheckAvailableSteps(BlankPosition blankPosition,Layout layout)
	{
	}

	public void CheckAvailableSteps(Layout layout)
	{
	}
	
	public void Move(int index,MoveMethod mType,Layout layout)
	{

	}
}
