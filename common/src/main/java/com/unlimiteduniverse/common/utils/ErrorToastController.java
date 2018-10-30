package com.unlimiteduniverse.common.utils;

import android.content.Context;
import android.os.Handler;
import android.util.SparseLongArray;
import android.widget.Toast;

/**
 * Created by Irvin
 * on 2018/3/17 0017.
 */

public class ErrorToastController {

    private static int lastRes;
    private static SparseLongArray requestMap = new SparseLongArray();

    public static void timeMachine(Context context, int resources) {

        if (lastRes == resources && (System.currentTimeMillis() - requestMap.get(resources) < 5000)) {
            return;
        }

        requestMap.put(resources, System.currentTimeMillis());
        lastRes = resources;

        Toast.makeText(context.getApplicationContext(), resources, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestMap.clear();
            }
        }, 60 * 1000);
    }
}
