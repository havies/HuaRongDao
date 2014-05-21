package edu.bjfu.klotski.DAL;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class ScreenShot {
	 public static Bitmap takeScreenShot(View view) {
	        // View是你需要截图的View
//	        View view = activity.getWindow().getDecorView();
	        view.setDrawingCacheEnabled(true);
	        view.buildDrawingCache();
	        Bitmap b1 = view.getDrawingCache();


	        // 获取屏幕长和高
	        int width = view.getWidth();
	        int height = view.getHeight();
	        // 去掉标题栏
	        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
	        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
	        view.destroyDrawingCache();
	        return b;
	    }

	    // 保存到sdcard
	    private static void savePic(Bitmap b, String strFileName) {
	        FileOutputStream fos = null;
	        try {
	            fos = new FileOutputStream(strFileName);
	            if (null != fos) {
	                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
	                fos.flush();
	                fos.close();
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // 程序入口
	    public static void shoot(View a,String fileName) {
	        ScreenShot.savePic(ScreenShot.takeScreenShot(a), "sdcard/"+fileName);
	    }
	
}
