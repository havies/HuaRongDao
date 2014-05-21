package edu.bjfu.klotski;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.util.EncodingUtils;
import edu.bjfu.klotski.UI.GameView;
import edu.bjfu.klotski.UI.LayoutFactory;
import edu.bjfu.klotski.UI.ResultHandler;
import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.CircularList.CircularLinkedList;
import edu.bjfu.klotski.core.HashTable.HashTable;
import edu.bjfu.klotski.core.Interfaces.ILayoutFactory;
import edu.bjfu.klotski.core.Layout.Layout;
import edu.bjfu.klotski.core.Mediator.Mediator;
import edu.bjfu.klotski.core.TreeLinkedList.TreeLinkedList;
import edu.bjfu.klotski.util.CommonUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class ChessMainActivity extends Activity {

	private ILayoutFactory layoutFactory;
	private Mediator mediator;
	private ResultHandler resultHandler = new ResultHandler();
	private Layout layout;
	private GameView gm;
	private Button btnPlay;
	private Button btnRefresh;
	private Button btnHelp;
	private Button btnBack;
	private Button btnPre;
	
	private int playIndex=0;
    
	private int layoutType;
	private int layoutID;
	private TextView tvCurrentStep=null;
	private static final String TAG = "ASYNC_TASK";  
	private MyTask mTask;
	private Timer mTimer = new Timer(true);
	private MyTimerTask mTimerTask;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏      
        
        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        
        setContentView(R.layout.activity_chess_main);

        tvCurrentStep=(TextView)findViewById(R.id.tvCurrentStep);
        btnPlay=(Button)findViewById(R.id.btnPlay);
        btnRefresh=(Button)findViewById(R.id.btnRefresh);
        btnHelp=(Button)findViewById(R.id.btnHelp);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnPre=(Button)findViewById(R.id.btnPre);
        
        
        
        Intent intent=getIntent();
        layoutID=intent.getIntExtra("layoutID",0);
        layoutType=intent.getIntExtra("layoutType",0);
        
        //设置棋盘
        gm=(GameView)findViewById(R.id.cm_GameView);
        gm.setLayoutID(this.layoutID);
        gm.setLayoutType(this.layoutType);
        gm.setTvCurrentStep(tvCurrentStep);
        float rate=(float)dm.widthPixels/(float)ConstCtrl.frame_width;
        int inner_width=(int) Math.ceil(rate*ConstCtrl.frame_inner_width);
        int height=inner_width*5/4+(int) Math.ceil(rate*(ConstCtrl.frame_height-ConstCtrl.frame_innner_height));
	     
        gm.SetWidth(dm.widthPixels);
        gm.SetHeight(height);
//        LayoutParams params = gm.getLayoutParams();
//        AbsoluteLayout.LayoutParams new_params = new AbsoluteLayout.LayoutParams(params.width, params.height,0, 100);
//        gm.setLayoutParams(new_params);
        gm.SetCacheCanvas();
        //gm.SetBackground(R.drawable.frame);
        
        
        LoadLayout();
        
        btnPlay.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				if(mediator.result.length>0)
//				{
//					
//					if(playIndex<mediator.result.length)
//					{
//						gm.SetCacheCanvas();
//						ChessStep cs=mediator.result[playIndex];
//	        			Log.i("layout",String.valueOf(cs.layout));
//	        			gm.Move(cs);
//	        			playIndex++;
//	        			
//					}
//					
//				}
				mTimer = new Timer(true);
				mTimerTask = new MyTimerTask();
				mTimer.schedule(mTimerTask, 0, 1000);
			}});
        
        btnRefresh.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				gm.SetCacheCanvas();
				LoadLayout();
				gm.postInvalidate();
				tvCurrentStep.setText("当前步数：0");
			}});
        
        btnHelp.setOnClickListener(new OnClickListener(){

			

			public void onClick(View v) {
//				getHelp();
				mTask = new MyTask();  
                mTask.execute("");  
                
			}});
        
        btnBack.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
			}});
        
        btnPre.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
			}});
        
    }

    
    private void LoadLayout()
    {
    	try
		{
    		layoutFactory = new LayoutFactory();
    		Intent intent=getIntent();
			String value1=intent.getStringExtra("layoutname");
			InputStream in = getResources().getAssets().open(value1+".xml"); 
			int length = in.available();   
			byte [] buffer = new byte[length];  
			in.read(buffer);  
			String xml = EncodingUtils.getString(buffer, "UTF-8");     
			layout=layoutFactory.Create(xml);
			layout.SimpleCheckAvailableSteps();
			gm.LoadLayout(layout);
		}
		catch(Exception e)
		{
			Log.e("ERR",e.getMessage()); 
		}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    ChessStep[] result=null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   	
        return super.onOptionsItemSelected(item);
    }


    private String getHelp()
    {
    	playIndex=0;
    	layoutFactory = new LayoutFactory();
		mediator = new Mediator();
		layoutFactory.setMediator(mediator) ;
		layoutFactory.setCurrentLayout(layout);
		mediator.setCircleList(new CircularLinkedList(layoutFactory));
		mediator.setHashTable(new HashTable())  ;
		mediator.setTreeList( new TreeLinkedList());
		mediator.setResultHandler(resultHandler)  ;
		
		
		mediator.Init(1000);
		if(!mediator.BeginProcess())
		{
			System.out.print("此布局无解！");
			return "此布局无解！";
		}
		else
		{
			System.out.print("ok");
			btnPlay.setVisibility(View.VISIBLE);
			return "解算成功！";
		}
    }
	

    public int getLayoutType() {
		return layoutType;
	}


	public void setLayoutType(int layoutType) {
		this.layoutType = layoutType;
	}


	public int getLayoutID() {
		return layoutID;
	}


	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}

	private class MyTask extends AsyncTask<String, Integer, String> 
	{

		 @Override  
	        protected void onPreExecute() {  
	            Log.i(TAG, "onPreExecute() called");  
	            //textView.setText("loading...");  
	            CommonUtils.showCustomProgressDialog(ChessMainActivity.this);
	        }		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			return getHelp();
		}
		
		//onProgressUpdate方法用于更新进度信息  
        @Override  
        protected void onProgressUpdate(Integer... progresses) {  
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");  
        } 
		
      //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
            Log.i(TAG, "onPostExecute(Result result) called");  
            CommonUtils.dissmissDialog();
        } 
        
        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override  
        protected void onCancelled() {  
            Log.i(TAG, "onCancelled() called");   
        }
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			Message message = new Message();

			if (playIndex < ConstCtrl.CONFIG_STEP) {
				message.what = 1;
			} else {
				
				message.what = 2;
				mTimer.cancel();
			}

			doActionHandler.sendMessage(message);
		}

	}
	
	private Handler doActionHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int msgId = msg.what;
			switch (msgId) {
			case 1:
				if(mediator.result.length>0)
				{
					
					if(playIndex<mediator.result.length)
					{
						gm.SetCacheCanvas();
						ChessStep cs=mediator.result[playIndex];
	        			Log.i("layout",String.valueOf(cs.layout));
	        			gm.Move(cs);
	        			gm.postInvalidate();
	        			playIndex++;		
					}
					
				}
				break;
			case 2:
				mTimer.cancel();
				break;
			default:
				break;
			}
		}
	};
}
