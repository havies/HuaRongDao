package edu.bjfu.klotski;

import edu.bjfu.klotski.R;
import edu.bjfu.klotski.R.layout;
import edu.bjfu.klotski.R.menu;
import edu.bjfu.klotski.BLL.SectionAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SelectSectionActivity extends Activity {
	
	private Gallery mGallery;
	private Button btn_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_section);
        mGallery = (Gallery)findViewById(R.id.gallery_section);
        btn_back=(Button)findViewById(R.id.btn_section_back);
        // Gallery
        SectionAdapter adapter=new SectionAdapter(this);
        TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
        adapter.setmGalleryItemBackground(typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0));
       
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView parent, View v, int position, long id) {

				 //Toast.makeText(SelectSectionActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				
				Intent i=new Intent(SelectSectionActivity.this,SelectLevelActivity.class);
				i.putExtra("position", position);
				startActivity(i);
			}
        });
        
        // Button
        btn_back.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				 finish();
			}});
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_select_section, menu);
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
