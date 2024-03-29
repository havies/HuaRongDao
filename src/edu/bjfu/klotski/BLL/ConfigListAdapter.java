package edu.bjfu.klotski.BLL;



import edu.bjfu.klotski.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConfigListAdapter  extends BaseAdapter  {

	//private LayoutInflater inflater;
	private String[] array;
	private Context mContext;
	public ConfigListAdapter(Context c,String[] array) {
		mContext = c;
		this.array = array;
	}
	public int getCount() {
		return array.length;
	}

	public Object getItem(int position) {
		return array[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = View.inflate(mContext, R.layout.config_item, null);
		TextView tv = (TextView) convertView.findViewById(R.id.text);// 由于圆角listview一般都是固定的迹象，所以在这里没有做优化处理，需要的话可自行
		tv.setText(array[position]);
		if (array.length == 1) {
			setBackgroundDrawable(convertView,
					R.drawable.list_round_selector);
		} else if (array.length == 2) {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == array.length - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			}
		} else {
			if (position == 0) {
				setBackgroundDrawable(convertView,
						R.drawable.list_top_selector);
			} else if (position == array.length - 1) {
				setBackgroundDrawable(convertView,
						R.drawable.list_bottom_selector);
			} else {
				setBackgroundDrawable(convertView,
						R.drawable.list_rect_selector);
			}
		}
		return convertView;
	}
	
	private void setBackgroundDrawable(View view, int resID) {
		view.setBackgroundDrawable(mContext.getResources().getDrawable(resID));
	}

}
