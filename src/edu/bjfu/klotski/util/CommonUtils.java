package edu.bjfu.klotski.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import edu.bjfu.klotski.R;
/**
 * 
 * @author liwejie
 * @version 2013-7-15 
 * @function 经常用到的工具方法
 */
public class CommonUtils {
    private static final int SECOND_TYPE         = 1000;
    private static final int MINUTE_TYPE         = 60;
    private static final int HOUR_TYPE           = 24;

    private static final int MINUTE_LIMIT        = 3600;
    private static final int LIMIT_STRING_LENGTH = 140;
    private static Dialog    dialog              = null;
    private static PopupWindow popupWindow = null;

    /**
     * 验证是否是中文
     * 
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String check = "^[\u4e00-\u9fa5]+$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否为空
     * */
    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str));
    }

    /**
     * 判断密码长短是否合法
     * 
     * @param str
     * @return
     */
    public static boolean isPasswdAuthorized(String str) {

        return (str.length() >= 6 && str.length() <= 20);

    }

    /**
     * 判断是否相等
     * */
    public static boolean isEquals(String str, String str2) {
        return str.equals(str2);
    }

    /**
     * 计算距离
     * */
    public static double getDistance(int dis) {

        double disTemp = 0.0;
        disTemp = dis / 1000.0;
        int temp = (int) Math.round(disTemp * 10);

        return (double) temp / 10.0;
    }

    /**
     * 计算车费
     * */
    public static int getSpend(double dis) {
        int rez = 0;
        int disTemp = (int) Math.round(dis);
        if (disTemp <= 3) {
            rez = 10;
        } else if (disTemp > 3 && disTemp <= 15) {
            rez = 10 + (disTemp - 3) * 2;
        } else if (disTemp > 15) {
            rez = 10 + 12 * 2 + (disTemp - 15) * 3;
        }

        return rez;
    }

    /**
     * 判断邮箱格式
     * */
    public static boolean isEmail(String str) {
        String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

   

    /**
     * 判断字符串长度（英文一个字节，汉字两个字节）
     * 
     * @param str
     * @return
     */
    public static int getStringBytesLength(String str) {

        int result = 0;
        int len = 0;
        int tempNum1 = 0;
        String strTemp = null;

        try {
            strTemp = new String(str.getBytes("GBK"), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        len = strTemp.length();

        tempNum1 = 0;
        if (len % 2 == 1) {
            tempNum1 = len / 2 + 1;
        } else if (len % 2 == 0) {
            tempNum1 = len / 2;
        }

        result = LIMIT_STRING_LENGTH - tempNum1;

        return result;

    }

    /**
     * MD5码
     */

    public static String getMd5(String src) {

        return MD5Util.MD5(src);
    }

    /**
     * 判断是否存在SD卡
     * 
     * @return
     */
    public static boolean hasSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    /**
     * 判断是否是正确的手机号
     * 
     * @param phone
     * @return
     */
    public boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^13/d{9}||15[8,9]/d{8}$");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 获得网络连接是否可用
     * 
     * @param context
     * @return
     */
    public static boolean getNetWorkStatus(Context context) {

        boolean netSataus = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        cwjManager.getActiveNetworkInfo();

        if (cwjManager.getActiveNetworkInfo() != null) {
            netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        return netSataus;
    }

    /**
     * 显示dialog
     * 
     * @param context
     * @param message
     */
    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("title");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("", new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();

    }

    public static void showCustomProgressDialog(Activity Activity) {
        if (dialog == null) {
            dialog = new Dialog(Activity, R.style.theme_dialog_alert);
        }
        dialog.setContentView(R.layout.custom_loading_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dissmissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    /**
     * 
     * @param view
     *            要显示的view
     * @param parent
     *            显示在哪个view的下方， (popupwindow 要和这个view是同级的 所以应该是activity最上面的空间)
     */
    public static void ShowPopupWindow(View view, View below_view) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00000000"));
        //        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.showAsDropDown(below_view, 0, 12);
    }

    public static void ShowPopupWindowMatchParent(View view, View below_view) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00000000"));
        //        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.showAsDropDown(below_view, 0, 10);
    }

    public static void ShowPopupWindowGrivty(View view, View parentView,int y) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00000000"));
        //        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.showAtLocation(parentView, Gravity.CENTER | Gravity.CENTER, 0, y);
    }

    /**
     * dismissPopPopWindow
     */
    public static void disMissPopupWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow=null;
        }
    }
    /**
     * 获取软件版本号/名
     * 
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            // 获取软件版本名称，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    
    /**
     * 展现确认对话框
     * @param activity
     */
    public static void dialog( final Activity activity,String message) {
        AlertDialog.Builder builder = new Builder(activity);
        builder.setMessage(message);

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

  
         public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();

          activity.finish();
         }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

 
         public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
         }
        });

        builder.create().show();
       }
    /**
     * dp转换为px
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dp * scale + 0.5f); 
    } 
     /**
      * px转化为dp
      * @param context
      * @param px
      * @return
      */
    public static int Px2Dp(Context context, float px) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (px / scale + 0.5f); 
    } 
    


	/**
	 * 请求返回异常提示
	 * @param flag
	 * @return
	 */
	public static String requesPrompt(String flag) {
		String requestMsg = "";
		if ("fail".equals(flag)) {
			requestMsg = "失败";
		} else if ("error".equals(flag)) {
			requestMsg = "系统异常";
		} else if ("securityCodeError".equals(flag)) {
			requestMsg = "验证码错误";
		} else if ("loginnameIsNull".equals(flag)) {
			requestMsg = "账号为空";
		} else if ("corpCodeIsNull".equals(flag)) {
			requestMsg = "企业编码为空";
		} else if ("passIsNull".equals(flag)) {
			requestMsg = "密码为空";
		} else if ("exist".equals(flag)) {
			requestMsg = "账号不存在";
		} else if ("exists".equals(flag)) {
			requestMsg = "账号存在多个";
		} else if ("password".equals(flag)) {
			requestMsg = "密码错误";
		} else if ("expired".equals(flag)) {
			requestMsg = "账号已过期";
		} else if ("logout".equals(flag)) {
			requestMsg = "账号已注销";
		} else if ("roleIsNull".equals(flag)) {
			requestMsg = "账号没有可用角色";
		} else if ("functionIsNull".equals(flag)) {
			requestMsg = "账号没有可用功能";
		}else if ("nologin".equals(flag)) {
			requestMsg = "未登录";
		}else if("permission".equals(flag)){
			requestMsg = "无权登录系统";
		}
		else if("novehicle".equals(flag)){
			requestMsg = "车辆不存在";
		}
		return requestMsg;
	}

}


