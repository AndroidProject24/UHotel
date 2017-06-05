package com.acuteksolutions.uhotel.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acuteksolutions.uhotel.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Toan.IT on 5/11/16.
 */
public class Utils {
  public static int getWidth(Activity context) {
    DisplayMetrics dm = new DisplayMetrics();
    context.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }
  public static int getWidth(Context context){
    return context.getResources().getDisplayMetrics().widthPixels;
  }
  public static int getHeight(Activity context) {
    DisplayMetrics dm = new DisplayMetrics();
    context.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.heightPixels;
  }

  public static boolean isEmpty(List<?> list) {
    return list == null || list.size() == 0;
  }

  public static boolean isEmpty(List<?> list, int index) {
    return list == null || list.size() == 0 || list.size() <= index;
  }
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("");
  }
  public static boolean ExistSDCard() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }
  public static void layoutError(Context context,ViewGroup view,String message){
    view.removeAllViewsInLayout();
    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    TextView textView=new TextView(context);
    textView.setText(message);
    textView.setTextSize(25);
    textView.setLayoutParams(layoutParams);
    view.addView(textView,layoutParams);
  }
  public static PackageInfo getPackageInfo(Context context) {
    PackageManager packageManager = context.getPackageManager();
    PackageInfo packageInfo = null;
    try {
      packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return packageInfo;
  }

  public static String getDeviceName() {
    return Build.MODEL;
  }

  public static String getDeviceVersion() {
    return Build.VERSION.RELEASE;
  }

  public static String getDeviceIMEI(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (telephonyManager == null || TextUtils.isEmpty(telephonyManager.getDeviceId())) {
      return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    } else {
      return telephonyManager.getDeviceId();
    }
  }
  public static int getBuildLevel() {
    return Build.VERSION.SDK_INT;
  }
  @SuppressLint("SimpleDateFormat")
  public static String convertDate(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd").format(date);
  }

  public static String getTime(){
    Calendar c = Calendar.getInstance();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    c.setTimeZone(tz);
    SimpleDateFormat df = new SimpleDateFormat("h:mm a   |   MMMM dd, yyyy");
    return df.format(c.getTime());
  }
  public static String ImageBase64(Bitmap bitmap){
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);// bm is Bitmap that initialized when choosed image from gallery
    byte[] ba = bao.toByteArray();
    return Base64.encodeToString(ba, Base64.DEFAULT);
  }
  public static boolean isEmailValid(String email) {
    //TODO: Replace this with your own logic
    return email.contains("@");
  }
  public static boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 3;
  }

  @SuppressLint("SimpleDateFormat")
  public static String parseDate(@NonNull Date date, @NonNull String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    sdf.applyPattern(pattern);
    return sdf.format(date);
  }

  public static String getStringFromEditText(EditText content) {

    String strContent = content.getText().toString().trim();
    while (strContent.length() > 0 && strContent.endsWith("\n")) {
      strContent = strContent.substring(0, strContent.length() - 2);
    }
    strContent = strContent.replace("\\", "\\\\").replace("\"", "\\\"")
            .replace("\n", "\\n");
    return strContent;

  }
  public static String getMacAddess() {
    //return "00000000ffaa";
    WifiManager manager = (WifiManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = manager.getConnectionInfo();
   // Log.d(Utility.class.getName(),info.getMacAddress());
    return info.getMacAddress() != null ? info.getMacAddress().replace(":","")
            : "00000000ffaa";

  }
}
