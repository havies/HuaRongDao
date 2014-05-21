package edu.bjfu.klotski;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bjfu.klotski.UI.SetLayoutView;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SetLayoutActivity extends Activity {


	SetLayoutView slv=null;
	
	Button btnSave=null;
	Button btnBack=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
        
      //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        
        setContentView(R.layout.activity_set_layout);

        slv=(SetLayoutView)findViewById(R.id.setLayoutView);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnBack=(Button)findViewById(R.id.btnBack);
        
        //----------------------------------------------------------
        float rate=(float)dm.widthPixels/(float)ConstCtrl.frame_width;
        int inner_width=(int) Math.ceil(rate*ConstCtrl.frame_inner_width);
        int height=inner_width*5/4+(int) Math.ceil(rate*(ConstCtrl.frame_height-ConstCtrl.frame_innner_height));
	     
        slv.SetWidth(dm.widthPixels);
        slv.SetHeight(height);
        slv.SetCacheCanvas();
        slv.SetBackgroud();
        //-----------------------------------------------------------
        

        
        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        
        btnBack.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				SetLayoutActivity.this.finish();
			}});
        
        btnSave.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				slv.setShowLine(false);
				slv.Save();
				Intent i=new Intent(SetLayoutActivity.this,LayoutManageActivity.class);
				startActivity(i);
			}});

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_set_layout, menu);
        return true;
    }

    Button[] buttons=new Button[20];
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private ListView listView;
    
    private List<String> getData(){
        
        List<String> data = new ArrayList<String>();
        data.add("黄忠");
        data.add("张飞");
        data.add("赵云");
        data.add("马超");
        data.add("关羽");
         
        return data;
    }
    
	private boolean isShow = false;
    private PopupWindow pop;
    private ListView lvChessman;
    
    private void getPopupWindow() {  
        
        if(null != pop) {  
        	pop.dismiss();  
            return;  
        }else {  
            initPopuptWindow();  
        }  
    } 
    
    
    protected void initPopuptWindow() 
    {  

    	
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popup_chess, null,false);  
        pop = new PopupWindow(popupWindow_view, 300, 200, true);//创建PopupWindow实例  
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
		lvChessman=(ListView)popupWindow_view.findViewById(R.id.lvChesses);
		List<String> data=this.getData();
		for(int i=0;i<data.size();i++)  
		{  
            HashMap<String, Object> map = new HashMap<String, Object>();  

            map.put("itemTitle", data.get(i));  
            listItem.add(map);  
        }	          
		 
		 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
		            R.layout.popup_chess_item,//ListItem的XML实现  
		            //动态数组与ImageItem对应的子项          
		            new String[] {"itemTitle"},   
		            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
		            new int[] {R.id.tvChessName}  
		        );  
		 lvChessman.setAdapter(listItemAdapter); 
		 lvChessman.setOnItemClickListener(new OnItemClickListener() {  
			  
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                    long arg3) {  
	                arg1.setBackgroundColor(Color.BLUE);
	                pop.dismiss();
	                String imageName="";
	                if(slv.type==2)
	                {
	                	if(arg2==0)
	                	{
	                		imageName="v_hz";
	
	                	}
	                	else if(arg2==1)
	                	{
	                		imageName="v_zf";
	                	}
	                	else if(arg2==2)
	                	{
	                		imageName="v_zy";
	                	}
	                	else if(arg2==3)
	                	{
	                		imageName="v_mc";
	                	}
	                	else if(arg2==4)
	                	{
	                		imageName="v_gy";
	                	}
	                		                	
	                	slv.AddImage(imageName,slv.unit,slv.unit*2,slv.selectLeft,slv.selectTop);
	                	index++;
	                }
	                else if(slv.type==3)
	                {

	                	if(arg2==0)
	                	{
	                		imageName="h_hz";
	                	}
	                	else if(arg2==1)
	                	{
	                		imageName="h_zf";
	                	}
	                	else if(arg2==2)
	                	{
	                		imageName="h_zy";
	                	}
	                	else if(arg2==3)
	                	{
	                		imageName="h_mc";
	                	}
	                	else if(arg2==4)
	                	{
	                		imageName="h_gy";
	                	}
	                	slv.AddImage(imageName,slv.unit*2,slv.unit,slv.selectLeft,slv.selectTop);
	                	index++;
	                }
	            }  
	        });   

          
    }
    
    
    private int index=1;
    public boolean onTouchEvent(MotionEvent event)
    {
    	boolean result; 
        switch (event.getAction()) { 
            case MotionEvent.ACTION_UP: 
                result = true; 
                
                if(this.slv.type==1&&this.slv.selectedRect.size()==1)
                {

                	slv.AddImage("soldier",slv.unit,slv.unit,slv.selectLeft,slv.selectTop);
                	index++;
                }
                else if(this.slv.type==4&&this.slv.selectedRect.size()==4)
                {

                	slv.AddImage("general",slv.unit*2,slv.unit*2,slv.selectLeft,slv.selectTop);
                	index++;
                }
                else if(this.slv.selectedRect.size()==2)
                {

                    getPopupWindow();  
                    pop.showAtLocation(this.slv, Gravity.CENTER, 0, 0); 
                }

				
                break; 
            case MotionEvent.ACTION_DOWN: 
                result = true; 
                break; 
            case MotionEvent.ACTION_CANCEL: 
                result = true; 
                break; 
            case MotionEvent.ACTION_MOVE: 
                result = false; 
                break; 
            default : 
                result = true; 
                break; 
        } 
        System.out.println("activity touch: " + result); 
        return result;  
        

    	
    }

}
