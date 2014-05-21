package edu.bjfu.klotski;

import edu.bjfu.klotski.R;
import edu.bjfu.klotski.R.layout;
import edu.bjfu.klotski.R.menu;
import edu.bjfu.klotski.DAL.DBHelper;
import edu.bjfu.klotski.DAL.SharedPreferenceHelper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class KlotskiMainActivity extends Activity {

	Button btnStart=null;
	Button btnSetting=null;
	Button btnAbout=null;
	Button btnQuit=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
        	setContentView(R.layout.activity_klotski_main);
            
            DBHelper helper=new DBHelper(this);
    		helper.createDataBase();
            SharedPreferenceHelper spHelper=new SharedPreferenceHelper(this,"config");
            ConstCtrl.CONFIG_VOICE=spHelper.getIntValue("voice");
            ConstCtrl.CONFIG_SHAKE=spHelper.getIntValue("shake");
            ConstCtrl.CONFIG_STEP=spHelper.getIntValue("step");
            
            Log.i("voice", String.valueOf(ConstCtrl.CONFIG_VOICE));
            Log.i("shake", String.valueOf(ConstCtrl.CONFIG_SHAKE));
            Log.i("step", String.valueOf(ConstCtrl.CONFIG_STEP));
            
            if(ConstCtrl.CONFIG_VOICE==-1)
            {
            	spHelper.putIntValue("voice", 0);
            	ConstCtrl.CONFIG_VOICE=spHelper.getIntValue("voice");
            }
            
            if(ConstCtrl.CONFIG_SHAKE==-1)
            {
            	spHelper.putIntValue("shake", 0);
            	ConstCtrl.CONFIG_SHAKE=spHelper.getIntValue("shake");
            }
            
            if(ConstCtrl.CONFIG_STEP==-1)
            {
            	spHelper.putIntValue("step", 1);
            	ConstCtrl.CONFIG_STEP=spHelper.getIntValue("step");
            }
            
            Log.i("voice", String.valueOf(ConstCtrl.CONFIG_VOICE));
            Log.i("shake", String.valueOf(ConstCtrl.CONFIG_SHAKE));
            Log.i("step", String.valueOf(ConstCtrl.CONFIG_STEP));
            // Buttton
            btnStart=(Button)findViewById(R.id.btn_main_start);
            btnSetting=(Button)findViewById(R.id.btn_main_setting);
            btnAbout=(Button)findViewById(R.id.btn_main_about);
            btnQuit=(Button)findViewById(R.id.btn_main_quit);
            
            btnStart.setOnClickListener(new OnClickListener()
            {

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Intent i=new Intent(KlotskiMainActivity.this,SelectSectionActivity.class);
    				startActivity(i);
    			}
            	
            });
            
            btnSetting.setOnClickListener(new OnClickListener()
            {

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Intent i=new Intent(KlotskiMainActivity.this,ConfigActivity.class);
    				startActivity(i);
    			}
            	
            });
            
            btnAbout.setOnClickListener(new OnClickListener()
            {

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    			}
            	
            });
            
            btnQuit.setOnClickListener(new OnClickListener()
            {

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    			}
            	
            });
        }
        catch(Exception ex)
        {
        	
        }
        
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_klotski_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
