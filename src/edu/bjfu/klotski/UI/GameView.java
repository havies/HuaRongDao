package edu.bjfu.klotski.UI;

import java.util.ArrayList;
import java.util.List;

import edu.bjfu.klotski.ConstCtrl;
import edu.bjfu.klotski.R;
import edu.bjfu.klotski.DAL.DBHelper;
import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.BaseComponent.MoveMethod;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;
import edu.bjfu.klotski.core.Layout.Chessman.General;
import edu.bjfu.klotski.core.Layout.Chessman.HChessman;
import edu.bjfu.klotski.core.Layout.Chessman.Soldier;
import edu.bjfu.klotski.core.Layout.Chessman.VChessman;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import android.widget.TextView;
import android.widget.Toast;


public class GameView extends Chessboard
{

	 private int selectIndex=-1;  
	 private float down_x;
	 private float down_y;
	 PlaySoundPool playSoundPool;  
	 Vibrator vibrator;   
	 private Context context;
	 
	 private int layoutType;
	 private int layoutID;
	 private TextView tvCurrentStep=null;
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
		playSoundPool=new PlaySoundPool(context);
    	playSoundPool.loadSfx(R.raw.slide, 1);
    	vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);   
    	this.context=context;
	}

	public GameView(Context context, AttributeSet attrs) {
		super( context, attrs );
		playSoundPool=new PlaySoundPool(context);
    	playSoundPool.loadSfx(R.raw.slide, 1);
    	vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);   
    	this.context=context;
	}

	public GameView(Context context) {
		super(context);
		playSoundPool=new PlaySoundPool(context);
    	playSoundPool.loadSfx(R.raw.slide, 1);
    	vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);   
    	this.context=context;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		for(int i=0;i<_chessmen.length;i++)
    	{
			
			Chessman c=_chessmen[i];
			Log.i(String.valueOf(c.getPosition().x),String.valueOf(c.getPosition().y));
			this.AddImage(c.getImageName(), chessRects[i].width(),chessRects[i].height(), chessRects[i].left, chessRects[i].top);
    	}
		if(cachebBitmap!=null)
		{		
			// 绘制上一次的，否则不连贯
			canvas.drawBitmap(cachebBitmap, 0, 0, null);

		}
	}
	
	private boolean isMove=false;
	
	@Override  
    public boolean onTouchEvent(MotionEvent event)
    {  
  	    /*取得手指触控屏幕的位置*/  
        float x = event.getX();  
        float y = event.getY();  
        
        switch (event.getAction())
        {  
	        case MotionEvent.ACTION_DOWN:  
            	down_x=x;
            	down_y=y;
            	selectIndex=selectChessman(x,y);
	            break;  
	        case MotionEvent.ACTION_MOVE:  
	            // 移动   
	 
	            // 通知重绘   
	            //postInvalidate();  
	            break;  
	        case MotionEvent.ACTION_UP:  
	            // 抬起   
	        	isMove=true;
	        	if(selectIndex>=0)
            	{
            		Move(selectIndex,x, y);  
            	}
	        	
	        	currentStep++;
	        	
	        	this.SetCacheCanvas();
	        	postInvalidate();  
	        	
	        	this.tvCurrentStep.setText("当前步数："+String.valueOf(currentStep));
	            break;  
        }  
        return true;    
    }
	
    private int selectChessman(float x,float y)
    {
    	int index=-1;
    	for(int i=0;i<this.chessRects.length;i++)
    	{
    		if(this.chessRects[i].contains((int)x,(int)y))
    		{
    			index= i;
    			break;
    		} 			
    	}
    	return index;
    	
    }

    
    private void Move(int selectedIndex,float x,float y)
    {
    	try
    	{

    		
    		//ImageView iv=this.imgViews[selectedIndex];
    		Chessman c=this._chessmen[selectedIndex];
    		
        	int intWidth=this.chessRects[selectIndex].width();
        	int intHeight=this.chessRects[selectIndex].height();
        	
    		float off_x=x-down_x;  
    		float off_y=y-down_y; 
    		float left=this.chessRects[selectIndex].left;
    		float top=this.chessRects[selectIndex].top ;;
    		float unit=this.unit;
    		MoveMethod mType=MoveMethod.Nothingness;
    		if(Math.abs(off_x)>Math.abs(off_y))
    		{
        		
        		if(off_x>0)
        		{
        			//向右移动
        			mType=MoveMethod.Right;
        		}
        		else
        		{
        			//向左移动
        			mType=MoveMethod.Left;
        		}
    		}
    		else
    		{
        		if(off_y>0)
        		{
        			mType=MoveMethod.Down;
        			//向下移动
        		}
        		else
        		{
        			mType=MoveMethod.Up;
        			//向上移动
        		}
    		}

    		if(c.getMoveMethodList().contains(mType))
    		{
    			switch(mType)
        		{
    	    		case Left:
    	    			left=left-unit;
    	    			break;
    	    		case Right:
    	    			left=left+unit;
    	    			break;
    	    			
    	    		case Up:
    	    			top=top-unit;
    	    			break;
    	    			
    	    		case Down:
    	    			top=top+unit;
    	    			break;
        		}
    			c.Move(selectIndex,mType, layout);
    			layout.SimpleCheckAvailableSteps();
//            	iv.setLayoutParams  
//                (  
//                  new AbsoluteLayout.LayoutParams  
//                  (intWidth,intHeight,(int) left,(int)top)  
//                ); 
    			
    			Rect rect=chessRects[selectIndex];
    			int w=rect.width();
    			int h=rect.height();
    			
    			rect.left=(int)left;
    			rect.top=(int)top;
    			rect.right=(int)left+w;
    			rect.bottom=(int)top+h;
    			
        		if(ConstCtrl.CONFIG_VOICE==1)
            	{
            		playSoundPool.play(1, 0);
            	}
        		if(ConstCtrl.CONFIG_SHAKE==1)
            	{
        			vibrator.vibrate(500L);
            	}
        		
            	if(layout.IsFinished())
            	{
            		Toast.makeText(mContext.getApplicationContext(), "完成",
            			     Toast.LENGTH_SHORT).show();
            		DBHelper helper=new DBHelper(context);
            		

            		String[] whereArgs = new String[] {String.valueOf(this.layoutType),String.valueOf(this.layoutID+1) };
            		ContentValues cv=new ContentValues(); 
            	    cv.put("opend", 1);
            		helper.Update("DB_GAME_LEVEL", cv, "type=? and id=?", whereArgs);
            	}
    		}
    		else
    		{
    			return;
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		Log.e("ERR",ex.getMessage());
    	}
 
    }

    public void Move(ChessStep cs)
    {
    	for(int i=0;i<this._chessmen.length;i++)
    	{
    		Chessman c=this._chessmen[i];
    		if(cs.chessmanPosition.x==c.getPosition().x
    		 &&cs.chessmanPosition.y==c.getPosition().y)
    		{
    			c.Move(i,cs.moveMethod, layout);
    			layout.SimpleCheckAvailableSteps();
    			
    			Rect rect=chessRects[i];
    			int w=rect.width();
    			int h=rect.height();
    			float left=this.chessRects[i].left;
        		float top=this.chessRects[i].top ;
        		float unit=this.unit;
    			switch(cs.moveMethod)
        		{
    	    		case Left:
    	    			left=left-unit;
    	    			break;
    	    		case Right:
    	    			left=left+unit;
    	    			break;   	    			
    	    		case Up:
    	    			top=top-unit;
    	    			break;   	    			
    	    		case Down:
    	    			top=top+unit;
    	    			break;
    	    		case Left2:
    	    			left=left-2*unit;
    	    			break;
    	    		case Right2:
    	    			left=left+2*unit;
    	    			break;   	    			
    	    		case Up2:
    	    			top=top-2*unit;
    	    			break;   	    			
    	    		case Down2:
    	    			top=top+2*unit;
    	    			break;
    	    			
    	    		case TurningLeftUp:
    	    			left=left-unit;
    	    			top=top-unit;
    	    			break;
    	    		case TurningLeftDown:
    	    			left=left-unit;
    	    			top=top+unit;
    	    			break;   	    			
    	    		case TurningRightUp:
    	    			left=left+unit;
    	    			top=top-unit;
    	    			break;   	    			
    	    		case TurningRightDown:
    	    			left=left+unit;
    	    			top=top+unit;
    	    			break;
    	    			
        		}
    			
    			rect.left=(int)left;
    			rect.top=(int)top;
    			rect.right=(int)left+w;
    			rect.bottom=(int)top+h;
    			
    			this.SetCacheCanvas();
	        	postInvalidate();  
            	if(layout.IsFinished())
            	{
            		Toast.makeText(mContext.getApplicationContext(), "完成",
            			     Toast.LENGTH_SHORT).show();
            		DBHelper helper=new DBHelper(context);
            		String[] whereArgs = new String[] {String.valueOf(this.layoutType),String.valueOf(this.layoutID+1) };
            		ContentValues cv=new ContentValues(); 
            	    cv.put("opend", 1);
            		helper.Update("DB_GAME_LEVEL", cv, "type=? and id=?", whereArgs);
            	}
    			break;
    		}
    	}
    }
    public Layout longToLayout(long step)
    {
    	Layout l=new Layout();
    	try
    	{
    		
    		List<Chessman> lsChess=new ArrayList<Chessman>();
    		long a = 7; // 二进制的 “111”
            long mask;
            int chessmanType, x, y;
            for (int i = 0; i < 20; i++)
            {
                mask = a << (i * 3);
                chessmanType = (int)((step & mask) >> (i * 3));
                x = i % 4;
                y = i / 4;

                Chessman c = null ;

                switch (chessmanType)
                {
                    case 0:	// 空格
                        continue;
                    case 1: // 曹操
                    	c = new General(new Position(x, y));
                        break;
                    case 2: // 竖放
                        c = new VChessman(new Position(x, y));
                        break;
                    case 3: // 横放
                        c = new HChessman(new Position(x, y));

                        break;
                    case 4: // 横放
                        c = new Soldier(new Position(x, y)); 
                        break;
                }

                lsChess.add(c);
            }
            
            Object[] temp=lsChess.toArray();
            
            for(int i=0;i<temp.length;i++)
            {
            	if(temp[i]!=null)
            	{
            		l.getChessmen()[i]=(Chessman)temp[i];
            	}
            }
            //l.setchessmen(temp);
           
    	}
    	catch(Exception ex)
    	{
    		Log.e("ERR",ex.getMessage());
    		
    	}
    	 return l;
    }


	public int getLayoutID() {
		return layoutID;
	}

	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}

	public int getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(int layoutType) {
		this.layoutType = layoutType;
	}

	public TextView getTvCurrentStep() {
		return tvCurrentStep;
	}

	public void setTvCurrentStep(TextView tvCurrentStep) {
		this.tvCurrentStep = tvCurrentStep;
	}

}