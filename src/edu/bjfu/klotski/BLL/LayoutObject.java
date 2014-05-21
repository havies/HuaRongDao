package edu.bjfu.klotski.BLL;

import java.util.ArrayList;
import java.util.List;

import edu.bjfu.klotski.core.BaseComponent.BlankPosition;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;

public class LayoutObject {

	private BlankPosition blankPosition;
	
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
	
	private List<Chessman> chessman=new ArrayList<Chessman>();
	
	public List<Chessman> getChessman() {
		return chessman;
	}
	
	public void  setChessman(List<Chessman> lc) {
		chessman=lc;
	}
}
