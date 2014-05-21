package edu.bjfu.klotski;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import edu.bjfu.klotski.R;
import edu.bjfu.klotski.R.layout;
import edu.bjfu.klotski.R.menu;

import edu.bjfu.klotski.BLL.LevelAdapter;
import edu.bjfu.klotski.DAL.DBHelper;
import edu.bjfu.klotski.Entity.LevelEntity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.support.v4.app.NavUtils;

public class SelectLevelActivity extends Activity {

	private Button btn_back;
	private GridView gridview;
	private int type=0;
	private List<LevelEntity> listLevel=new ArrayList<LevelEntity>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        
        Intent intent=getIntent();
		type=intent.getIntExtra("position", 0);
		
        gridview = (GridView) findViewById(R.id.gv_level);
        btn_back=(Button)findViewById(R.id.btn_level_back);
        
        getLevelItems();
        gridview.setAdapter(new LevelAdapter(this,listLevel));
        gridview.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i=new Intent(SelectLevelActivity.this,ChessMainActivity.class);
				i.putExtra("layoutname", String.valueOf(arg2+1));
				
				LevelEntity item=listLevel.get(arg2);
				i.putExtra("layoutID", item.getId());
				i.putExtra("layoutType", item.getType());
				
				startActivity(i);
				}
			}
        );
        
        
        // Button
        btn_back.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				 finish();
			}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_select_level, menu);
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

    private void getLevelItems()
    {
    	try
    	{
    		DBHelper helper=new DBHelper(this);
    		//helper.createDataBase();
    		
    		String selection = "type=?";
    		String[] selectionArgs = new String[] { String.valueOf(type+1) };
    		
        	Cursor myCursor=helper.select("DB_GAME_LEVEL",null,selection,selectionArgs);
        	
        	for (myCursor.moveToFirst(); ! myCursor.isAfterLast();myCursor.moveToNext())
            {
        		LevelEntity item=new LevelEntity();
        		item.setId(myCursor.getInt(0));
        		item.setType(myCursor.getInt(1));
        		item.setLayoutText(myCursor.getString(2));
        		item.setOepned(myCursor.getInt(3));
        		listLevel.add(item);
            }
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	
    }
}
