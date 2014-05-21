package edu.bjfu.klotski.BLL;



import java.util.List;

import edu.bjfu.klotski.R;
import edu.bjfu.klotski.Entity.LevelEntity;
import edu.bjfu.klotski.UI.CoverFlow;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class LayoutAdapter extends BaseAdapter {

	private List<LevelEntity> array;
	int mGalleryItemBackground;
	private Context mContext;
//	private Integer[] mImageIds = {R.drawable.section_1,
//            R.drawable.section_2,
//            R.drawable.section_3,
//            R.drawable.section_4,
//            R.drawable.section_5,
//            R.drawable.section_custom };
	
	public LayoutAdapter(Context c,List<LevelEntity> array) {
		mContext = c;
		this.array=array;
	}

	//@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	//@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.array.get(position);
	}

	//@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void refreshData(List<LevelEntity> data)
	{
		this.array = data;
		notifyDataSetChanged();
		
	}
	//@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(mContext);
		LevelEntity item =array.get(position);
		Bitmap bm = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);  
        i.setImageBitmap(bm);
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
	
	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
	
     public ImageView createReflectedImages(Context mContext, int imageId) {
    	 Bitmap originalImage=null;
//		Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
    	
    	 if(imageId<array.size())
    	 {
    		 LevelEntity item =array.get(imageId);
    		 originalImage = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);  
    	 }
    	 else
    	 {
    		 originalImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.section_custom );
    	 }
		final int reflectionGap = 4;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Matrix matrix=new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,height / 2, width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(originalImage, 0, 0, null);
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		ImageView imageView = new ImageView(mContext);
		imageView.setImageBitmap(bitmapWithReflection);
		return imageView;
	}

}
