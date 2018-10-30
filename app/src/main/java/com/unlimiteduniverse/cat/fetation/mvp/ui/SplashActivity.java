package com.unlimiteduniverse.cat.fetation.mvp.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.unlimiteduniverse.cat.fetation.AppCache;
import com.unlimiteduniverse.common.sysutils.SysInfoUtil;
import com.unlimiteduniverse.common.utils.PermissionsUtil;
import com.unlimiteduniverse.common.utils.StatusBarUtil;


/**
 * Created by Irvin
 * on 2017/8/9 0009.
 */

public class SplashActivity extends Activity {

    private static boolean mFirstEnter = true; // 是否首次进入

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
        if (savedInstanceState != null) {
            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
        }
        //Do something innit
        if (!mFirstEnter) {
            handleIntent();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (mFirstEnter) {
            mFirstEnter = false;
            if (PermissionsUtil.hasWritePermissions(this)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (canAutoLogin()) {
                            handleIntent();
                        } else {
                            HomeActivity.start(SplashActivity.this);
                            finish();
                        }
                    }
                }, 500);
            }
        }
    }

    private boolean canAutoLogin() {
        return true;
    }

    private void handleIntent() {
        if (TextUtils.isEmpty(AppCache.getAccountCache())) {
            // 判断当前app是否正在运行
            if (!SysInfoUtil.stackResumed(this)) {
                HomeActivity.start(this);
            }
            finish();
        } else {
            // 已经登录过了，处理传过来的请求
            Intent intent = getIntent();
            if (intent != null) {
//                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
//                    parseNotifyIntent(intent);
//                    return;
//                } else if (intent.hasExtra(Extras.EXTRA_JUMP_P2P)) {
//                    parseNormalIntent(intent);
//                }
            }

            if (!mFirstEnter && intent == null) {
                finish();
            } else {
                showMainActivity();
            }
        }
    }

    private void showMainActivity() {
        HomeActivity.start(SplashActivity.this);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        handleIntent();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            return;
        }
        switch (requestCode) {
            case PermissionsUtil.REQ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (canAutoLogin()) {
                                handleIntent();
                            } else {
                                HomeActivity.start(SplashActivity.this);
                                finish();
                            }
                        }
                    }, 500);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("需要获取权限")
                            .setMessage("我们需要获取存储空间，储存相关信息；否则，您将无法正常使用得力e+，设置路径：设置→应用→得力e+→权限→读写手机储存")
                            .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    SplashActivity.this.startActivity(intent);
                                    exitApp();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exitApp();
                        }
                    })
                            .setCancelable(false)
                            .create().show();
                }
                break;
            default:
                break;
        }
    }

    private void exitApp() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        assert am != null;
        am.killBackgroundProcesses(getPackageName());
        finish();
        System.exit(0);
    }
}
