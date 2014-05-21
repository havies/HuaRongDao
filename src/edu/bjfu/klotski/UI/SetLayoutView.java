package edu.bjfu.klotski.UI;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.http.util.EncodingUtils;


import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
//
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//
//import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
//
//import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
//
//import com.thoughtworks.xstream.io.json.JsonWriter;

import edu.bjfu.klotski.ConstCtrl;
import edu.bjfu.klotski.R;
import edu.bjfu.klotski.BLL.ComparatorInt;
import edu.bjfu.klotski.BLL.LayoutObject;
import edu.bjfu.klotski.DAL.DBHelper;
import edu.bjfu.klotski.DAL.ScreenShot;
import edu.bjfu.klotski.DAL.XMLFactory;
import edu.bjfu.klotski.core.BaseComponent.BlankPosition;
import edu.bjfu.klotski.core.BaseComponent.ChessmanType;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Layout.Chessman.Chessman;
import edu.bjfu.klotski.core.Layout.Chessman.General;
import edu.bjfu.klotski.core.Layout.Chessman.HChessman;
import edu.bjfu.klotski.core.Layout.Chessman.Soldier;
import edu.bjfu.klotski.core.Layout.Chessman.VChessman;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SetLayoutView extends View{
	Paint fillPaint=new Paint();
	private Path path= new Path();
	private Paint paint=new Paint();
	private int clrbg, clrfg;

	private Stack<List<Integer>> UndoList=new Stack<List<Integer>>();
	private Stack<List<Integer>> RedoList=new Stack<List<Integer>>();
	
	private List<Chessman> chessman=new ArrayList<Chessman>();
	
	private int width=100;
	private int height=100;
	private Context mContext;
	
	private boolean showLine=true;
	
	public  void SetWidth(int w)
	{
		width=w;
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
		cacheCanvas.drawColor(clrbg);
	}
	
	

	private void Init()
	{
		clrbg = Color.WHITE;
		clrfg = Color.CYAN;
		// TODO Auto-generated constructor stub
		fillPaint.setColor(Color.YELLOW);

		paint.setAntiAlias(true); // 抗锯齿
		paint.setStrokeWidth(3); // 线条宽度
		paint.setStyle(Paint.Style.STROKE); // 画轮廓
		paint.setColor(clrfg); // 颜色
		

	}
	public SetLayoutView(Context context) {
		super(context);
		mContext=context;
		Init();
	}
	
	
	public SetLayoutView(Context context, AttributeSet attrs) {
		 
		super( context, attrs );
		mContext=context;
		Init();
		
	}
	
	public SetLayoutView(Context context, AttributeSet attrs, int defStyle) {
		 
		super( context, attrs, defStyle );
		mContext=context;
		Init();
	}
	
	private int innertop=0;
	private int innerleft=0;
	public int backgroundh=0;

	float rate=0;
	public void SetBackgroud()
	{
        rate=(float)width/(float)ConstCtrl.frame_width;
      	int innerwidth=(int) Math.ceil(rate*ConstCtrl.frame_inner_width);
		innerleft=(int) Math.rint(rate*(ConstCtrl.frame_width-ConstCtrl.frame_inner_width)/2);
		innertop=(int) Math.rint(rate*(ConstCtrl.frame_height-ConstCtrl.frame_innner_height)/2);
		unit=(width-2*innerleft)/4; 		

        backgroundh=innerwidth*5/4+(int) Math.ceil(rate*(ConstCtrl.frame_height-ConstCtrl.frame_innner_height));
        
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.frame);
		Bitmap b=bmp.createScaledBitmap(bmp, width, backgroundh, true);
		cacheCanvas.drawBitmap(b, 0, 0, null);
	}
	private Rect[] rects=new Rect[20];
	public  int unit=0;
	@Override
	protected void onDraw(Canvas canvas)
	{

		 		
		// 绘制上一次的，否则不连贯
		if(cachebBitmap!=null)
		{
			canvas.drawColor(clrbg);
			canvas.drawBitmap(cachebBitmap, 0, 0, null);
			canvas.drawPath(path, paint);     	
		}
		int index=0;
		int x=0;
		int y=0;
		
		if(this.showLine)
		{
			for(x=0;x<5;x++)
	    	{
	    		for(y=0;y<4;y++)
	    		{
	    			Rect rt=new Rect();
	        		rt.top=x*unit+innertop;
	        		rt.left=y*unit+innerleft;
	        	    rt.bottom=rt.top+unit;
	        	    rt.right=rt.left+unit;
	        		
	        		canvas.drawRect(rt, paint);
//	        		canvas.drawLine(rt.left, rt.top, rt.left, rt.bottom, paint);
//	        		canvas.drawLine(rt.right, rt.top, rt.right, rt.bottom, paint);
//	        		
//	        		canvas.drawLine(rt.left, rt.top, rt.right, rt.top, paint);
//	        		canvas.drawLine(rt.left, rt.bottom, rt.right, rt.bottom, paint);
	        		
	        		rects[index]=rt;
	        		index++;
	    		}
	    		
	    	}
		}
    	
	}

	

	private Canvas cacheCanvas;
	private Bitmap cachebBitmap;
	
	private List<Integer> allSelectedRect=new ArrayList<Integer>();
	 boolean result = false; 
	 int i = 0;  
	 
	public int selectTop=0;
	public int selectLeft=0;
	public  List<Integer> selectedRect=null;
	public int type=0;
	public boolean onTouchEvent(MotionEvent event)
    {
		float x = event.getX();  
        float y = event.getY();  
        
      switch (event.getAction())   
      { 
	      case MotionEvent.ACTION_DOWN:
	    	  //color the cell
	    	  selectedRect=new ArrayList<Integer>();
	    	  for(int i=0;i<rects.length;i++)
	          {
	          	if(rects[i].contains((int)x, (int)y)
	          			&&!allSelectedRect.contains(i)
	          			&&!selectedRect.contains(i))
	          	{
	          		cacheCanvas.drawRect(rects[i], this.fillPaint);
	          		selectedRect.add(i);
	          		allSelectedRect.add(i);
	          	}
	          }
	    	  result = true;  
	      	break; 
	      case MotionEvent.ACTION_CANCEL: 
              result = true; 
              break;  
	      case MotionEvent.ACTION_MOVE: 
	    	  //color the cell
	    	  for(int i=0;i<rects.length;i++)
	          {
		          	if(rects[i].contains((int)x, (int)y)
		          			&&!allSelectedRect.contains(i)
		          			&&!selectedRect.contains(i))
	          	{
	          		cacheCanvas.drawRect(rects[i], this.fillPaint);
	          		selectedRect.add(i);
	          		allSelectedRect.add(i);
	          	}
	          }
	    	  
	    	  if (i > 0) { 
                  result = false; 
              } 
              i++;  
	      	break;
	      case MotionEvent.ACTION_UP:  
	    	  result = false;  
	    	  boolean validate=true;
	    	  type=0;
	    	  Paint p=new Paint();
	    	  // if colored cell is 1 , 2, 4 pass
	    	  // else redo the action
	    	  ComparatorInt comparator=new ComparatorInt();
    		  Collections.sort(selectedRect, comparator);
    		  
	    	  if(selectedRect.size()==1)
	    	  {
	    		  //添加一个“卒”
	    		  p.setColor(Color.LTGRAY);
	    		  type=1;
	    	  }
	    	  else if(selectedRect.size()==2)
	    	  {
	    		  //判断不回竖将或者横将
	    		  int num=Math.abs(selectedRect.get(0)-selectedRect.get(1));
	    		  if(num==1)
	    		  {
	    			  p.setColor(Color.BLUE);
	    			  type=3;
	    			 
	    		  }
	    		  else if(num==4)
	    		  {
	    			  p.setColor(Color.GREEN);
	    			  type=2;
	    		  }
	    		  else
	    		  {
	    			  validate=false;
	    		  }
	    	  }
	    	  else if(selectedRect.size()==4)
	    	  {
	    		  
	    		  //selectedRect.
	    		  //添加“曹操”
	    		  
	    		  
	    		  if(Math.abs(selectedRect.get(0)-selectedRect.get(1))==1
	    		   &&Math.abs(selectedRect.get(2)-selectedRect.get(3))==1
	    		   &&Math.abs(selectedRect.get(0)-selectedRect.get(2))==4
	    		   &&Math.abs(selectedRect.get(1)-selectedRect.get(3))==4
	    		   &&(Math.abs(selectedRect.get(1)-selectedRect.get(2))!=1
	    		   ||Math.abs(selectedRect.get(1)-selectedRect.get(2))!=4))
	    		  {
	    			  type=4;
	    			  p.setColor(Color.RED);
	    		  }
	    		  else
	    		  {
	    			  validate=false;
	    		  }
	    		  
	    	  }
	    	  else
	    	  {
	    		  //返回上一次操作
	    		  validate=false;
	    	  }
	    	  
	    	  if(validate)
	    	  {
	    		  this.UndoList.add(this.selectedRect);
	    	  }
	    	  else
	    	  {
	    		  for(Integer i:this.selectedRect)
	    		  {
	    			  if(this.allSelectedRect.contains(i))
	    			  {
	    				boolean bRemove= this.allSelectedRect.remove(i);
	    			  }
	    		  }
	    		  if(this.UndoList.size()>0)
	    		  {
	    			  this.UndoList.pop();
	    		  }
	    		  else
	    		  {
	    			 //清空
 
	    		  }
	    		  p.setColor(this.clrbg);

	    	  }
	    	  
	    	  for(int i=0;i<selectedRect.size();i++)
    		  {
	    		  if(i==0)
	    		  {
	    	    	  selectTop=rects[selectedRect.get(0)].top;
	    	    	  selectLeft=rects[selectedRect.get(0)].left;
	    		  }
    			  cacheCanvas.drawRect(rects[selectedRect.get(i)], p);
    		  }
	    	  
	    	  

	          break;
      		}
        
        
        invalidate();
        System.out.println("view touch"); 
        return result;  
		
    }
	Resources res=getResources();
	public void AddImage(String imgName,int w,int h,int l,int t)
	{
		
		Class drawable  =  R.drawable.class;
        Field field = null;
        
        try {
            field = drawable.getField(imgName);
            int id = field.getInt(field.getName());
            Bitmap bmp=BitmapFactory.decodeResource(res, id);
    		Bitmap b=bmp.createScaledBitmap(bmp, w, h, true);
    		cacheCanvas.drawBitmap(b, l, t, null);
        } catch (Exception e) {
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
		int num=selectedRect.get(0);
		
		int x=num%4;
		int y=num/4;
		

		Position p=new Position(x,y);
		Chessman c=null;
		
		
		if(this.type==1)
		{
			c=new Soldier(p);
			c.setChessmanType(ChessmanType.Solider);
		}
		else if(this.type==2)
		{
			c=new VChessman(p);
			c.setChessmanType(ChessmanType.VChessman);
		}
		else if(this.type==3)
		{
			c=new HChessman(p);
			c.setChessmanType(ChessmanType.HChessman);
		}
		else if(this.type==4)
		{
			c=new General(p);
			c.setChessmanType(ChessmanType.General);
		}
		
		c.setImageName(imgName);
		this.chessman.add(c);
		
		this.invalidate();
	}
	
	public void Redo()
	{
		
	}
	
	public void Undo()
	{
		
	}
	
	public void Save()
	{
        try {

        	
        	XStream xstream = new XStream();
        	
        	LayoutObject lb=new LayoutObject();
        	lb.setChessman(this.chessman);
        	
        	BlankPosition bp=new BlankPosition();
        	for(int i=0;i<20;i++)
        	{
        		if(!allSelectedRect.contains(i))
        		{
        			// is blank position
        			if(bp.Pos1.x==-1)
        			{
        				bp.Pos1.x=i%4;
        				bp.Pos1.y=i/4;
        				
        			}
        			else
        			{
        				bp.Pos2.x=i%4;
        				bp.Pos2.y=i/4;
        			
        			}
        		}
        	}
        	lb.setBlankPosition(bp);
        	String json=JSON.toJSONString(lb);
    		String xml=xstream.toXML(lb);
    		Log.i("INFO",xml);
    		
    		
    		String fileName=XMLFactory.CreateFileName();
    		XMLFactory.SaveFile(mContext, fileName, xml);
    		Bitmap screenShot=ScreenShot.takeScreenShot(this);
    		
    		ByteArrayOutputStream os = new ByteArrayOutputStream();  
    		/** 
    		* Bitmap.CompressFormat.JPEG 和 Bitmap.CompressFormat.PNG 
    		* JPEG 与 PNG 的是区别在于 JPEG是有损数据图像，PNG使用从LZ77派生的无损数据压缩算法。 
    		* 这里建议使用PNG格式保存 
    		* 100 表示的是质量为100%。当然，也可以改变成你所需要的百分比质量。 
    		* os 是定义的字节输出流 
    		*  
    		* .compress() 方法是将Bitmap压缩成指定格式和质量的输出流 
    		*/  
    		screenShot.compress(Bitmap.CompressFormat.PNG, 100, os);  
    		DBHelper helper=new DBHelper(mContext);  
    		
    		ContentValues initValues = new ContentValues();  
    		initValues.put("id", 61);
    		initValues.put("screenShot", os.toByteArray());
    		initValues.put("layout", "");
    		initValues.put("type", 6);
    		initValues.put("opend", 1);
    		initValues.put("layoutXML", xml);
    		initValues.put("layoutJSON", json);
    		helper.insert("DB_GAME_LEVEL", initValues);
    		Toast.makeText(mContext.getApplicationContext(), "保存成功!",
   			     Toast.LENGTH_SHORT).show();
    		//String temp=readFile("layout1");
        } catch (Exception e) {

            e.printStackTrace();

        }
        
		

	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}
	

//	public String readFile(String fileName){
//		  String res="";
//		  try{
//		         FileInputStream fin = mContext.openFileInput(fileName);
//		         int length = fin.available();
//		         byte [] buffer = new byte[length];
//		         fin.read(buffer);    
//		         res = EncodingUtils.getString(buffer, "UTF-8");
//		         fin.close();    
//		     }
//		     catch(Exception e){
//		         e.printStackTrace();
//		     }
//		     return res;
//		 
//		}
}
