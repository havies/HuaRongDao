package edu.bjfu.klotski.DAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.EncodingUtils;


import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;

import com.thoughtworks.xstream.XStream;

import edu.bjfu.klotski.R;



public class XMLFactory {

	private static XStream xstream;
	
	public static XStream GetXStream()
	{
		if(xstream==null)
		{
			xstream = new XStream();
		}
		return xstream;
	}
	
	
	public static String ToXML(Object o)
	{
		try
		{
			return xstream.toXML(o);
		}
		catch(Exception e){
	         e.printStackTrace();
	         return "";
		}
		
	}
	
	public static Object ToObject(String xml)
	{
		try
		{
			return xstream.fromXML(xml);
		}
		catch(Exception e){
	         e.printStackTrace();
	         return null;
		}
		
	}
	
	
	
	public static String ReadFile(Context mContext,String fileName)
	{
		  String res="";
		  try{
			  	Resources resource = mContext.getResources();
			  	String directoryName=CreateDirectory( (String)resource.getString(R.string.app_name));
		        FileInputStream fin = mContext.openFileInput(directoryName+"/"+fileName);
		        int length = fin.available();
		        byte [] buffer = new byte[length];
		        fin.read(buffer);    
		        res = EncodingUtils.getString(buffer, "UTF-8");
		        fin.close();    
		     }
		     catch(Exception e){
		         e.printStackTrace();
		     }
		     return res;
		 
	}
	
	public static void SaveFile(Context mContext,String fileName,String xml)
	{
		 try
		 {
			Resources resource = mContext.getResources();
			String directoryName=CreateDirectory( (String)resource.getString(R.string.app_name));
			File file = new File(directoryName,fileName);			 
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(xml.getBytes());
		    fos.flush();
		    fos.close();
		 }
		 catch(Exception e){
	         e.printStackTrace();
	     }

	}
	
	public static boolean IsSDCardExist()
	{

		boolean sdCardExist = Environment.getExternalStorageState() 
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在 
		return sdCardExist;	
	}
	
	public static String CreateDirectory(String directoryName)
	{
		String directoryPath="";
		if(IsSDCardExist())
		{
			 File dir = Environment.getExternalStorageDirectory();   
			 String path=dir.getPath()+"/";	
			 File SubDir= new File(path+directoryName);  
			 SubDir.mkdir();
			 directoryPath=SubDir.getPath();
		}
		return directoryPath;
	}
	
	public static String CreateFileName()
	{
		String name="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); 
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间        
		String   str   =   formatter.format(curDate);  
		name=str+".xml";
		return name;
	}
}
