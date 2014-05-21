package edu.bjfu.klotski.BLL;

import java.util.List;


import edu.bjfu.klotski.R;
import edu.bjfu.klotski.Entity.LevelEntity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelAdapter extends BaseAdapter{

	private Context mContext;
	
	private List<LevelEntity> array;
	
	public LevelAdapter(Context c ,List<LevelEntity> array){
        mContext = c;
        this.array=array;
    }
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return array.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.level_item, null);
		AbsoluteLayout al = (AbsoluteLayout) view.findViewById(R.id.al_level_item);
		//ImageView image = (ImageView) al.findViewById(R.id.iv_lock);
		TextView text = (TextView) al.findViewById(R.id.tv_level_number);
		//image.setImageResource(mThumbIds[position]);
		
		LevelEntity levelItem=array.get(position);
		if(levelItem.getOepned()>0)
		{
			text.setText(String.valueOf(position+1));
			text.setBackgroundResource(R.drawable.level_item_open);
		}
		
		return al;
	}

}
