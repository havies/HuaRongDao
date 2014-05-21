package edu.bjfu.klotski.UI;

import java.io.InputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import edu.bjfu.klotski.BLL.LayoutObject;
import edu.bjfu.klotski.DAL.XMLFactory;
import edu.bjfu.klotski.core.BaseComponent.BlankPosition;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Layout.Chessman.*;

public class LayoutFactory extends AbstractLayoutFactory
{

	private Layout layout=null;
	public Layout Create() {
		Layout l = new Layout();
		l.setBlankPosition(new BlankPosition(new Position(0,4), new Position(3,4)));
		l.setMediator(mediator);

	    l.getChessmen()[0] = new VChessman(new Position(0,0));
	    l.getChessmen()[1] = new General(new Position(1,0));
	    l.getChessmen()[2] = new VChessman(new Position(3,0));
	    l.getChessmen()[3] = new VChessman(new Position(0,2));
	    l.getChessmen()[4] = new HChessman(new Position(1,2));
	    l.getChessmen()[5] = new VChessman(new Position(3,2));
	    l.getChessmen()[6] = new Soldier(new Position(1,3));
	    l.getChessmen()[7] = new Soldier(new Position(2,3));
	    l.getChessmen()[8] = new Soldier(new Position(1,4));
	    l.getChessmen()[9] = new Soldier(new Position(2,4));
	    l.layoutInt64=l.ToInt64();
	    l.setMediator(mediator);
	    return l;
	}

	
	@Override
	public Layout Create(String xml) {
		Layout l = new Layout();
		XStream xstream=new XStream();
		LayoutObject lb=(LayoutObject)xstream.fromXML(xml);
		List<Chessman> lChessman=lb.getChessman();
		
		for(int index=0;index<l.getChessmen().length;index++)
		{
			l.getChessmen()[index]=lChessman.get(index);
		}
		l.setBlankPosition(lb.getBlankPosition());
		l.setMediator(mediator);
		l.layoutInt64=l.ToInt64();
		return l;

	}
	
	public void setCurrentLayout(Layout l) {
		// TODO Auto-generated method stub
		layout= l;
	}

	public Layout getCurrentLayout() {
		// TODO Auto-generated method stub
		Layout l = new Layout();
		l.setBlankPosition(layout.getBlankPosition());
		l.setMediator(mediator);

		for(int i=0;i<layout.getChessmen().length;i++)
		{
			Chessman c=layout.getChessmen()[i];
			Chessman nChess=null;
			switch(c.chessmanType())
			{
			case VChessman:
				nChess=new VChessman(new Position(c.getPosition().x,c.getPosition().y));
				break;
			case General:
				nChess=new General(new Position(c.getPosition().x,c.getPosition().y));
				break;
			case HChessman:
				nChess=new HChessman(new Position(c.getPosition().x,c.getPosition().y));
				break;
			case Solider:
				nChess=new Soldier(new Position(c.getPosition().x,c.getPosition().y));
				break;
			}
			l.getChessmen()[i]=nChess;
		}
	    l.layoutInt64=l.ToInt64();
		return l;
		
	}

	
}
