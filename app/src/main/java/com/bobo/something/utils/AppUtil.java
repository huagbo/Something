package com.bobo.something.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by huangbo on 2017/6/20.
 */

public class AppUtil {

    public static Context ApplicationContext;

    public static String getAppVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("1");//1: Android，2: IOS
        stringBuffer.append(getVersionRelease());//获取SDK版本号
        stringBuffer.append(String.valueOf(getAppVersionCode()));
        stringBuffer.append("000000");
        return stringBuffer.toString();
    }

    private static String getVersionRelease() {
        String release = Build.VERSION.RELEASE;
        return release.replace(".", "").substring(0, 2);
    }

    public static int getAppVersionCode() {
        Context context = ApplicationContext;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getAppVersionName() {
        try {
            String verStr = ApplicationContext.getPackageManager().getPackageInfo(
                    ApplicationContext.getPackageName(), 0).versionName;
            return verStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }

    }

    static public String GetDeviceIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) ApplicationContext.getSystemService(Context.TELEPHONY_SERVICE);
            String udid = tm.getDeviceId();
            if (udid == null) {
                udid = Settings.System.getString(ApplicationContext.getContentResolver(), Settings.System.ANDROID_ID);
            }
            return udid;
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getMobileModel() {
        String name = "";
        try {
//        1. 获取手机型号：
            String model = android.os.Build.MODEL;
//        2. 获取手机厂商：
            String carrier = android.os.Build.MANUFACTURER;
            name = carrier + model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
