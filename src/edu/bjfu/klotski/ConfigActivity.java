package edu.bjfu.klotski;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import edu.bjfu.klotski.DAL.SharedPreferenceHelper;

public class ConfigActivity extends Activity {

	private ListView list1;
	private ListView list2;
	private ListView list3;
	
	String array1[] = {"声音","震动"};
	String array2[] = {"提示步数","自定义布局"};
	String array3[] = {"检查更新","关于"};
	
	CheckBox chkVoice;
	CheckBox chkShake;
	LinearLayout lltCustomLayout;
	LinearLayout lltAbout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        final SharedPreferenceHelper spHelper=new SharedPreferenceHelper(this,"config");
        chkVoice=(CheckBox)findViewById(R.id.chkVoice); 
        chkShake=(CheckBox)findViewById(R.id.chkShake); 
        Spinner spin=(Spinner)findViewById(R.id.spinner1); 
        lltCustomLayout=(LinearLayout)findViewById(R.id.lltCustomLayout); 
        lltAbout=(LinearLayout)findViewById(R.id.lltAbout); 
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.steps_arry, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        if(ConstCtrl.CONFIG_STEP>0)
        {
        	 spin.setSelection(ConstCtrl.CONFIG_STEP-1);
        }
        
        if(ConstCtrl.CONFIG_VOICE==1)
        {
        	chkVoice.setChecked(true);
        }
        else
        {
        	chkVoice.setChecked(false);
        }
        
        if(ConstCtrl.CONFIG_SHAKE==1)
        {
        	chkShake.setChecked(true);
        }
        else
        {
        	chkShake.setChecked(false);
        }
       
        spin.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				spHelper.putIntValue("step", arg2+1);
				ConstCtrl.CONFIG_STEP=spHelper.getIntValue("step");
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
        
        
        
        chkVoice.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					spHelper.putIntValue("voice", 1);
				}
				else
				{
					spHelper.putIntValue("voice", 0);
				}
				ConstCtrl.CONFIG_VOICE=spHelper.getIntValue("voice");
			}});
        
       
        chkShake.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					spHelper.putIntValue("shake", 1);
				}
				else
				{
					spHelper.putIntValue("shake", 0);
				}
				ConstCtrl.CONFIG_SHAKE=spHelper.getIntValue("shake");
			}});
        
        
        lltCustomLayout.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ConfigActivity.this,LayoutManageActivity.class);
				startActivity(i);
			}});
        
        lltAbout.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_config, menu);
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
