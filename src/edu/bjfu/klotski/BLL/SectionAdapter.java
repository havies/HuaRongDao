package edu.bjfu.klotski.BLL;

import edu.bjfu.klotski.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class SectionAdapter extends BaseAdapter{

	int mGalleryItemBackground;
	
	private Context mContext;
	
	private Integer[] mImage = {
            R.drawable.section_1,
            R.drawable.section_2,
            R.drawable.section_3,
            R.drawable.section_4,
            R.drawable.section_5,
            R.drawable.section_custom
    };
	
	public SectionAdapter(Context c){
        mContext = c;
    }
	
	public int getCount() {
		// TODO Auto-generated method stub
		 return mImage.length;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView (mContext);
        i.setImageResource(mImage[position]);
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setLayoutParams(new Gallery.LayoutParams(400, 700));
       
        i.setBackgroundResource(mGalleryItemBackground);
        return i;
	}

	public int getmGalleryItemBackground() {
		return mGalleryItemBackground;
	}

	public void setmGalleryItemBackground(int mGalleryItemBackground) {
		this.mGalleryItemBackground = mGalleryItemBackground;
	}
	
}
