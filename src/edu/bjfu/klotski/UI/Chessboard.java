package edu.bjfu.klotski.UI;


import java.lang.reflect.Field;

import edu.bjfu.klotski.ConstCtrl;
import edu.bjfu.klotski.R;
import edu.bjfu.klotski.core.BaseComponent.ChessmanType;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Chessboard extends View {

	protected int width=100;
	protected int height=100;
	protected Context mContext;
	protected Canvas cacheCanvas;
	protected Bitmap cachebBitmap;
//	private int clr_bg, clr_fg;
//	private Paint fillPaint=new Paint();
//	private Path path= new Path();
//	private Paint paint=new Paint();
	protected int inner_top=0;
	protected int inner_left=0;
	private float rate=0;
	public int unit=0;
	protected Chessman[] _chessmen;
	
	protected  Rect[] chessRects=new Rect[10];
	protected Layout layout;
	
	protected int currentStep=0;
	public Chessboard(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
		mContext=context;

	}

	public Chessboard(Context context, AttributeSet attrs) {
		super( context, attrs );
		mContext=context;

	}

	public Chessboard(Context context) {
		super(context);
		mContext=context;

	}
	
	
	public  void SetWidth(int w)
	{
		width=w;	
		rate=(float)w/(float)ConstCtrl.frame_width;
		inner_left=(int) Math.rint(rate*(ConstCtrl.frame_width-ConstCtrl.frame_inner_width)/2);
		inner_top=(int) Math.rint(rate*(ConstCtrl.frame_height-ConstCtrl.frame_innner_height)/2);
		unit=(w-2*inner_left)/4; 
	}
	
	public void SetHeight(int h)
	{
		height=h;
	}
	
	public void SetCacheCanvas()
	{
		// 创建一张屏幕大小的位图，作为缓冲
		cachebBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		cacheCanvas = new Canvas(cachebBitmap);
		//cacheCanvas.drawColor(clr_bg);
	}
	
	public void SetBackground(int resID)
	{
		 //this.AddImage(resID, width, height, 0, 0);
	}
	
	protected void Init()
	{
//		clr_bg = Color.WHITE;
//		clr_fg = Color.CYAN;
//		// TODO Auto-generated constructor stub
//		fillPaint.setColor(Color.YELLOW);
//		paint.setAntiAlias(true); // 抗锯齿
//		paint.setStrokeWidth(3); // 线条宽度
//		paint.setStyle(Paint.Style.STROKE); // 画轮廓
//		paint.setColor(clr_fg); // 颜色	
		
	}

	public void LoadLayout(Layout l)
	{
		try
		{
			currentStep=0;
			layout=l;
			int count=l.getChessmen().length;
			_chessmen=l.getChessmen();
			for(int i=0;i<count;i++)
        	{
				Chessman c=_chessmen[i];
				int left=c.getPosition().x*unit+inner_left;
        		int top=c.getPosition().y*unit+inner_top;
        		int width=0;
        		int height=0;
        		if(c.chessmanType()==ChessmanType.General)
        		{
        			width=unit*2;
        			height=unit*2;
        			
        		}
        		else if(c.chessmanType()==ChessmanType.HChessman)
        		{
        			width=unit*2;
        			height=unit;
        		}
        		else if(c.chessmanType()==ChessmanType.VChessman)
        		{
        			width=unit;
        			height=unit*2;
        		}
        		else if(c.chessmanType()==ChessmanType.Solider)
        		{
        			width=unit;
        			height=unit;
        		}
        		//
        		
        		Rect rt=new Rect();
        		rt.top=top;
        		rt.left=left;
        	    rt.bottom=rt.top+height;
        	    rt.right=rt.left+width;
        	    chessRects[i]=rt;
        	}
		}
		catch(Exception ex)
		{
			Log.e("ERR",ex.getMessage());
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		
		AddImage(R.drawable.frame, width, height, 0, 0);
	}
	
	
	public void AddImage(String imgName,int w,int h,int l,int t)
	{
		Class drawable  =  R.drawable.class;
        Field field = null;
        
        try {
        	Resources res=getResources();
            field = drawable.getField(imgName);
            int id = field.getInt(field.getName());
            Bitmap bmp=BitmapFactory.decodeResource(res, id);
    		Bitmap b=bmp.createScaledBitmap(bmp, w, h, true);
    		cacheCanvas.drawBitmap(b, l, t, null);
        } catch (Exception e) {
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
	}
	
	public void AddImage(int id,int w,int h,int l,int t)
	{
		Resources res=getResources();
		Bitmap bmp=BitmapFactory.decodeResource(res, id);
		Bitmap b=bmp.createScaledBitmap(bmp, w, h, true);
		cacheCanvas.drawBitmap(b, l, t, null);
	}
	
	
//	protected void Clear()
//	{
//		cachebBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//	}

}
