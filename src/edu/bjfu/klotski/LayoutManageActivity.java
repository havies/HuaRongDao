package edu.bjfu.klotski;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;
import edu.bjfu.klotski.BLL.LayoutAdapter;
import edu.bjfu.klotski.DAL.DBHelper;
import edu.bjfu.klotski.Entity.LevelEntity;

public class LayoutManageActivity extends Activity {

	private Gallery mGallery;
	Button btnNew=null;
	private List<LevelEntity> listLevel=new ArrayList<LevelEntity>();
	LayoutAdapter imageAdapter=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manage);
        
        mGallery = (Gallery)findViewById(R.id.glCustomLayout);
        btnNew=(Button)findViewById(R.id.btnNew);
		this.getLevelItems();
		imageAdapter = new LayoutAdapter(this,listLevel);
		TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
		imageAdapter.setmGalleryItemBackground(typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0));
		mGallery.setAdapter(imageAdapter);
		
		 mGallery.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView parent, View v, int position, long id) {

					 //Toast.makeText(LayoutManageActivity.this, "编辑", Toast.LENGTH_SHORT).show();
					
//					Intent i=new Intent(SelectSectionActivity.this,SelectLevelActivity.class);
//					i.putExtra("position", position);
//					startActivity(i);
				}
	        });
		 
		 mGallery.setOnItemLongClickListener(new OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// 
				listLevel.remove(arg2);
				imageAdapter.refreshData(listLevel);
				Toast.makeText(LayoutManageActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
				return false;
			}});
		btnNew.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(LayoutManageActivity.this,SetLayoutActivity.class);
				startActivity(i);
			}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_layout_manage, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void getLevelItems()
    {
    	try
    	{
    		DBHelper helper=new DBHelper(this);
    		//helper.createDataBase();
    		
    		String selection = "type=?";
    		String[] selectionArgs = new String[] { "6" };
    		
        	Cursor myCursor=helper.select("DB_GAME_LEVEL",null,selection,selectionArgs);
        	
        	for (myCursor.moveToFirst(); ! myCursor.isAfterLast();myCursor.moveToNext())
            {
        		LevelEntity item=new LevelEntity();
        		item.setId(myCursor.getInt(0));
        		item.setType(myCursor.getInt(1));
        		item.setLayoutText(myCursor.getString(2));
        		item.setOepned(myCursor.getInt(3));
        		item.setLayoutXML(myCursor.getString(4));
        		item.setLayoutJSON(myCursor.getString(5));
        		item.setImage(myCursor.getBlob(6));
        		listLevel.add(item);
            }
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	
    }
}
