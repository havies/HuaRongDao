package edu.bjfu.klotski;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import edu.bjfu.klotski.R;
import edu.bjfu.klotski.R.drawable;
import edu.bjfu.klotski.R.id;
import edu.bjfu.klotski.R.layout;
import edu.bjfu.klotski.UI.LayoutFactory;
import edu.bjfu.klotski.UI.PlaySoundPool;
import edu.bjfu.klotski.UI.ResultHandler;
import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.BaseComponent.ChessmanType;
import edu.bjfu.klotski.core.BaseComponent.MoveMethod;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.CircularList.CircularLinkedList;
import edu.bjfu.klotski.core.HashTable.HashTable;
import edu.bjfu.klotski.core.Interfaces.ILayoutFactory;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;
import edu.bjfu.klotski.core.Layout.Chessman.General;
import edu.bjfu.klotski.core.Layout.Chessman.HChessman;
import edu.bjfu.klotski.core.Layout.Chessman.Soldier;
import edu.bjfu.klotski.core.Layout.Chessman.VChessman;
import edu.bjfu.klotski.core.Mediator.Mediator;
import edu.bjfu.klotski.core.TreeLinkedList.TreeLinkedList;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;



@SuppressWarnings("deprecation")
public class GameActivity extends Activity {

	private ILayoutFactory layoutFactory;
	private Mediator mediator;
	private ResultHandler resultHandler = new ResultHandler();
	
	 /*声明存储屏幕的分辨率变量 */  
    private int intScreenX, intScreenY;  
    AbsoluteLayout aLayout;
    private ImageView[] imgViews;
    private Chessman[] chessmen;
    private Layout layout;
    private Button btnPlay;
    private OnClickListener btnClickListener=null;
    
    int currentStep=1;
    ChessStep[] result=null;
    
    PlaySoundPool playSoundPool;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
        
        
        //DisplayMetrics dm = new DisplayMetrics();   
        //getWindowManager().getDefaultDisplay().getMetrics(dm);  
        //intScreenX = dm.widthPixels;  
        //intScreenY = dm.heightPixels;
        layoutFactory = new LayoutFactory();
		
        playSoundPool=new PlaySoundPool(this);
    	playSoundPool.loadSfx(R.raw.slide, 1);
    	
		try
		{
			Intent intent=getIntent();
			String value1=intent.getStringExtra("layoutname");
			InputStream in = getResources().getAssets().open(value1+".xml"); 
			int length = in.available();   
			byte [] buffer = new byte[length];  
			in.read(buffer);  
			String xml = EncodingUtils.getString(buffer, "UTF-8");     
			layout=layoutFactory.Create(xml);
		}
		catch(Exception e)
		{
			  e.printStackTrace();       
		}
        
        
        //layout=layoutFactory.Create();
        layout.SimpleCheckAvailableSteps();
        //GameView gv=new GameView(this,l,intScreenX,intScreenY);
        //setContentView(gv);
        setContentView(R.layout.activity_gamemain);
        
        aLayout= (AbsoluteLayout)findViewById(R.id.GameLayout);
        
        
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        drawChessMan(layout);
        
        btnClickListener = new OnClickListener()
		{

			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "完成",
//	       			     Toast.LENGTHSHORT).show();
				//layout.setchessmen(result[currentStep].Chessmen);
				
				
				
				if(currentStep>=result.length)
				{
					Toast.makeText(getApplicationContext(), "完成",
      			     Toast.LENGTH_SHORT).show();
				}
				else
				{
					longToLayout(result[currentStep].layout,layout);
					drawChessMan(layout);
					currentStep++;
					if(currentStep>=result.length)
					{
						Toast.makeText(getApplicationContext(), "完成",
	      			     Toast.LENGTH_SHORT).show();
					}
				}
				
			}

			
		};

    }
    
    private void longToLayout(long step,Layout l)
    {
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
    }

    private void drawChessMan(Layout l)
    {
    	try
    	{
    		if(imgViews!=null&&this.imgViews.length>0)
    		{
    			aLayout.removeViews(0,this.imgViews.length);
    		}
    		
    		 /* 取得屏幕对象 */  
            DisplayMetrics dm = new DisplayMetrics();   
            getWindowManager().getDefaultDisplay().getMetrics(dm);  
              
            /* 取得屏幕解析像素 */  
            intScreenX = dm.widthPixels;  
            intScreenY = dm.heightPixels;  
            
        	int count=l.getChessmen().length;
        	int cellWidth=intScreenX/4;
        	chessmen=l.getChessmen();
        	imgViews=new ImageView[count];
        	
        	for(int i=0;i<count;i++)
        	{
        		Chessman c=chessmen[i];
        		ImageView mImageView=new ImageView(this);
        		imgViews[i]=mImageView;
        		int left=c.getPosition().x*cellWidth;
        		int top=c.getPosition().y*cellWidth;
        		
        		int right=0;
        		int buttom=0;
        		if(c.chessmanType()==ChessmanType.General)
        		{
        			right=left+2*cellWidth;
        			buttom=top+2*cellWidth;
        			mImageView.setImageResource(R.drawable.general);
        			
        			//mImageView.set
        		}
        		else if(c.chessmanType()==ChessmanType.HChessman)
        		{
        			right=left+2*cellWidth;
        			buttom=top+cellWidth;
        			//mImageView.setImageResource(R.drawable.hchessman);
        		}
        		else if(c.chessmanType()==ChessmanType.VChessman)
        		{
        			right=left+cellWidth;
        			buttom=top+2*cellWidth;
        			//mImageView.setImageResource(R.drawable.vchessman);
        		}
        		else if(c.chessmanType()==ChessmanType.Solider)
        		{
        			right=left+cellWidth;
        			buttom=top+cellWidth;
        			mImageView.setImageResource(R.drawable.soldier);
        		}
        		
        		System.out.println("width:"+(right-left)+",height:"+(buttom-top)+",x:"+left+",y:"+top);
        		aLayout.addView(mImageView, i, new AbsoluteLayout.LayoutParams(right-left,buttom-top,left,top));

        	}
    	}
    	catch(Exception ex)
    	{
    		Log.e("ERR", ex.getMessage());
    		
    	}
    	
    }
    
    private int selectIndex=-1;  
    private float downx;
    private float downy;
    public boolean onTouchEvent(MotionEvent event)
    {
    	  /*取得手指触控屏幕的位置*/  
        float x = event.getX();  
        float y = event.getY();  
        
        try  
        {  
          /*触控事件的处理*/  
          switch (event.getAction())   
          {  
            /*点击屏幕*/  
            case MotionEvent.ACTION_DOWN:  
            	downx=x;
            	downy=y;
            	selectIndex=selectChessman(x,y);
//            	System.out.println("imgViewsx:"+imgViews[selectIndex].getLeft()+",imgViewsy:"+imgViews[selectIndex].getTop());
//            	System.out.println("downx:"+downx+",downy:"+downy+" ACTIONDOWN"+" selected:"+selectIndex);
            	if(this.chessmen[selectIndex].getMoveMethodList().size()>0)
            	{
            		System.out.println(selectIndex+",chessType:"+this.chessmen[selectIndex].chessmanType()+" can move");
            	}
            	else
            	{
            		System.out.println(selectIndex+",chessType:"+this.chessmen[selectIndex].chessmanType()+" can't  move");
            	}
            	
                break;  
            /*移动位置*/  
            case MotionEvent.ACTION_MOVE: 
            	//System.out.println("x:"+x+",y:"+y+" ACTIONMOVE"+" selected:"+selectIndex);
//            	if(selectIndex>=0)
//            	{
//            		Move(this.imgViews[selectIndex],x, y);  
//            	}
                break;  
            /*离开屏幕*/  
            case MotionEvent.ACTION_UP:  
            	if(selectIndex>=0)
            	{
            		Move(selectIndex,x, y);  
            	}
            	System.out.println("x:"+x+",y:"+y+" ACTIONUP"+" selected:"+selectIndex);

                break;  
          }  
          
          
        }catch(Exception e)  
          {  
            e.printStackTrace();  
          }  
        return true;  
    }
    
    private int selectChessman(float x,float y)
    {
    	int index=-1;
    	for(int i=0;i<this.imgViews.length;i++)
    	{
    		ImageView iv=imgViews[i];
    		int left=iv.getLeft();
    		int right=iv.getRight();
    		int top=iv.getTop();
    		int bottom=iv.getBottom();
    		
    		if(x>=left&&x<=right&&y>=top&&y<=bottom)
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
        	if(ConstCtrl.CONFIG_VOICE==1)
        	{
        		playSoundPool.play(1, 0);
        	}
    		ImageView iv=this.imgViews[selectedIndex];
    		Chessman c=this.chessmen[selectedIndex];
    		
        	int intWidth=iv.getWidth();
        	int intHeight=iv.getHeight();
        	
    		float offx=x-downx;  
    		float offy=y-downy; 
    		float left=iv.getLeft();
    		float top=iv.getTop();
    		float unit=(float)intScreenX/4;
    		MoveMethod mType=MoveMethod.Nothingness;
    		if(Math.abs(offx)>Math.abs(offy))
    		{
        		
        		if(offx>0)
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
        		if(offy>0)
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
            	iv.setLayoutParams  
                (  
                  new AbsoluteLayout.LayoutParams  
                  (intWidth,intHeight,(int) left,(int)top)  
                ); 
            	
            	
            	if(layout.IsFinished())
            	{
            		Toast.makeText(getApplicationContext(), "完成",
            			     Toast.LENGTH_SHORT).show();
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
    /*移动图片的方法*/  
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 menu.add(0, 1, 1, "求解");
         menu.add(0, 2, 2, "关于");
        //getMenuInflater().inflate(R.menu.activityklotski, menu);
        return super.onCreateOptionsMenu(menu);
    }

    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == 1){
    		layoutFactory.setCurrentLayout(layout);
    		
    		mediator = new Mediator();
    		layoutFactory.setMediator(mediator) ;
    		mediator.setCircleList(new CircularLinkedList(layoutFactory));
    		mediator.setHashTable(new HashTable())  ;
    		mediator.setTreeList( new TreeLinkedList());
    		mediator.setResultHandler(resultHandler)  ;
    		mediator.Init(1000);
    		if(!mediator.BeginProcess())
    		{
    			Log.i("INFO","此布局无解！");
    		}
    		else
    		{
    			result=mediator.result;
    			Log.i("INFO","ok");
    		}
    		
    		if(btnPlay==null)
            {
            	btnPlay=new Button(this);
            	btnPlay.setText("Play");
                int unit=intScreenX/4;
                
                int width=unit*2;
                int height=unit;
                int top=intScreenY-height;
                int left=intScreenX/2-width/2;
                aLayout.addView(btnPlay, this.imgViews.length, new AbsoluteLayout.LayoutParams(width,height,left,top));
    			btnPlay.setOnClickListener(btnClickListener);
            }
    		
        }
        else if(item.getItemId() == 2){
            
//            try
//            {
//            	if(btnPlay!=null)
//                {
//                	btnPlay.setVisibility(View.INVISIBLE);
//                }
//            }
//            catch(Exception ex)
//            {
//            	Log.e("ERR",ex.getMessage());
//            }
            
            
        } 
    	return true;
    }

    

}
