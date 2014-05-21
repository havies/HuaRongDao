package edu.bjfu.klotski.DAL;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	
	Context context;
	
	public SharedPreferenceHelper(Context c,String name)
	{
		context=c;
		sp=context.getSharedPreferences(name, 0);
		editor=sp.edit();
	}
	
	public void putStrValue(String key,String value)
	{
		editor=sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getStrValue(String key)
	{
		return sp.getString(key, null);
	}
	
	public void putIntValue(String key,int value)
	{
		editor=sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int getIntValue(String key)
	{
		return sp.getInt(key, -1);
	}
	

}
