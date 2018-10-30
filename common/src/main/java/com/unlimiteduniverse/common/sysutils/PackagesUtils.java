package com.unlimiteduniverse.common.sysutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * @author ChengXinPing
 * @time 2017/11/27 17:23
 */

public class PackagesUtils {

    private Context mContext;
    private static PackagesUtils mPackagesUtils;

    private PackagesUtils(Context context) {
        mContext = context;
    }

    public static PackagesUtils getInstance(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            if (mPackagesUtils == null) {
                mPackagesUtils = new PackagesUtils(contextWeakReference.get());
            }
            return mPackagesUtils;
        }
        return new PackagesUtils(context);
    }

    /**
     * 获取 App versionCode
     *
     * @return versioncode
     */
    public String getVersionCode() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取 App versionName
     *
     * @return versionName
     */
    public String getVersionName() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }
}
