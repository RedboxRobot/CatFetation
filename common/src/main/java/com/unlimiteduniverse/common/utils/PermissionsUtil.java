/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unlimiteduniverse.common.utils;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help with runtime permissions.
 * suppose api level is 23 or higher
 */
public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    public static final int REQ_PERMISSION_CODE = 1;

    public static final int CALL_PHONE_PERMISSION_CODE = 11;
    public static final int LOCATION_PERMISSION_CODE = 12;//获取定位需要的权限组
    public static final int AUDIO_PERMISSION_CODE = 13;
    public static final int IBEACON_LOCATION_PERMISSION_CODE = 14; //iBeacon需要的权限

    // Each permission in this list is a cherry-picked permission from a particular permission
    // group. Granting a permission group enables access to all permissions in that group so we
    // only need to check a single permission in each group.
    // Note: This assumes that the app has correctly requested for all the relevant permissions
    // in its Manifest file.
    public static final String PHONE = permission.CALL_PHONE;
    public static final String CONTACTS = permission.READ_CONTACTS;
    public static final String[] LOCATION = {
            permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
    };
    public static final String AUDIO = permission.RECORD_AUDIO;
    public static final String CAMERA = permission.CAMERA;

    public static final String[] MEDIA = {
            permission.RECORD_AUDIO,
            permission.CAMERA,
    };

    public static final String IBEACON_LOCATION = permission.ACCESS_COARSE_LOCATION;
    /**
     * 定位需要进行检测的权限数组
     */
    public static String[] locationNeedPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static boolean hasPhonePermissions(Activity activity) {
        if (hasPermission(activity, PHONE)) {
            return true;
        }
        requestPermission(activity, PHONE, CALL_PHONE_PERMISSION_CODE);
        return false;
    }

    public static boolean hasIBeaconLocationPermissions(Activity activity) {
        if (hasPermission(activity, IBEACON_LOCATION)) {
            return true;
        }
        requestPermission(activity, IBEACON_LOCATION, IBEACON_LOCATION_PERMISSION_CODE);
        return false;
    }

    public static boolean hasWritePermissions(Activity activity) {
        if (hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return true;
        }
        requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return false;
    }

    public static boolean hasWritePermissions(Fragment fragment) {
        if (hasPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return true;
        }
        requestPermission(fragment, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return false;
    }

    public static boolean hasPhonePermissions(Fragment fragment) {
        if (hasPermission(fragment.getContext(), PHONE)) {
            return true;
        }
        requestPermission(fragment, PHONE);
        return false;
    }

    public static boolean hasContactsPermissions(Fragment fragment) {
        if (hasPermission(fragment.getContext(), CONTACTS)) {
            return true;
        }
        requestPermission(fragment, CONTACTS);
        return false;
    }

    public static boolean hasContactsPermissions(Activity activity) {
        if (hasPermission(activity, CONTACTS)) {
            return true;
        }
        requestPermission(activity, CONTACTS);
        return false;
    }

    public static boolean hasLocationPermissions(Activity activity) {
        if (hasPermissions(activity, LOCATION)) {
            return true;
        }
        requestPermissions(activity, LOCATION, LOCATION_PERMISSION_CODE);
        return false;
    }

    public static boolean hasLocationPermissions(Fragment fragment) {
        if (hasPermissions(fragment.getContext(), LOCATION)) {
            return true;
        }
        requestPermissions(fragment, LOCATION, LOCATION_PERMISSION_CODE);
        return false;
    }

    public static boolean hasCameraPermissions(Activity activity) {
        if (hasPermission(activity, CAMERA)) {
            return true;
        }
        requestPermission(activity, CAMERA);
        return false;
    }

    public static boolean hasCameraPermissions(Fragment fragment) {
        if (hasPermission(fragment.getContext(), CAMERA)) {
            return true;
        }
        requestPermission(fragment, CAMERA);
        return false;
    }

    public static boolean hasAudioPermissions(Activity activity, int permissionCode) {
        if (hasPermission(activity, AUDIO)) {
            return true;
        }
        requestPermission(activity, AUDIO, AUDIO_PERMISSION_CODE);
        return false;
    }

    public static boolean hasAudioPermissions(Fragment fragment, int permissionCode) {
        if (hasPermission(fragment.getContext(), AUDIO)) {
            return true;
        }
        requestPermission(fragment, AUDIO, AUDIO_PERMISSION_CODE);
        return false;
    }

    public static boolean hasAudioPermissions(Fragment fragment) {
        if (hasPermission(fragment.getContext(), AUDIO)) {
            return true;
        }
        requestPermission(fragment, AUDIO);
        return false;
    }

    public static boolean hasMediaPermissions(Activity activity) {
        if (hasPermissions(activity, MEDIA)) {
            return true;
        }
        requestPermissions(activity, MEDIA);
        return false;
    }

    public static boolean hasMediaPermissions(Fragment fragment) {
        if (hasPermissions(fragment.getContext(), MEDIA)) {
            return true;
        }
        requestPermissions(fragment, MEDIA);
        return false;
    }

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 判断权限集合 无权限返回true
    public boolean lacksPermissions(Context context, String... permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (lacksPermission(context, permissions[i])) {//无权限
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限  无权限返回true
    private boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public static boolean requestSinglePermission(Activity activity, String permissionRequest) {
        return requestSinglePermission(activity, permissionRequest, REQ_PERMISSION_CODE);
    }

    public static boolean requestSinglePermission(Activity activity, String permissionRequest, int PERMISSION_CODE) {
        if (ContextCompat.checkSelfPermission(activity, permissionRequest)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permissionRequest}, PERMISSION_CODE);
            Log.d(TAG, "Ask Permission:" + permissionRequest);
            return false;
        } else {
            Log.d(TAG, "Permission Granted :" + permissionRequest);
            return true;
        }
    }

    public static boolean requestPermissions(Activity activity, String[] requestList, int PERMISSION_CODE) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        boolean needCheck = false;
        for (int i = 0; i < requestList.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, requestList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissionList.add(requestList[i]);
                needCheck = true;
            }
        }
        if (needRequestPermissionList.size() > 0) {
            String[] array = needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]);
            ActivityCompat.requestPermissions(activity, array, PERMISSION_CODE);
        }
        return needRequestPermissionList.size() == 0;
    }

    public static void requestPermissions(Activity activity, String[] requestList) {
        requestPermissions(activity, requestList, REQ_PERMISSION_CODE);
    }

    public static void requestPermission(Activity activity, String request, int PERMISSION_CODE) {
        String[] array = {request};
        ActivityCompat.requestPermissions(activity, array, PERMISSION_CODE);
    }

    public static void requestPermission(Activity activity, String request) {
        requestPermission(activity, request, REQ_PERMISSION_CODE);
    }

    public static void requestPermission(Fragment fragment, String request, int PERMISSION_CODE) {
        fragment.requestPermissions(new String[]{request}, PERMISSION_CODE);
    }

    public static void requestPermission(Fragment fragment, String request) {
        fragment.requestPermissions(new String[]{request}, REQ_PERMISSION_CODE);
    }

    public static void requestPermissions(Fragment fragment, String[] requestList) {
        String[] listNew = new String[requestList.length];

        for (int i = 0; i < requestList.length; i++) {
            if (ContextCompat.checkSelfPermission(fragment.getActivity(), requestList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                listNew[i] = requestList[i];
            }
        }
        fragment.requestPermissions(listNew, REQ_PERMISSION_CODE);
    }

    public static void requestPermissions(Fragment fragment, String[] requestList, int PERMISSION_CODE) {
        String[] listNew = new String[requestList.length];

        for (int i = 0; i < requestList.length; i++) {
            if (ContextCompat.checkSelfPermission(fragment.getActivity(), requestList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                listNew[i] = requestList[i];
            }
        }
        fragment.requestPermissions(listNew, PERMISSION_CODE);
    }

    /**
     * 是否需要授权
     *
     * @return
     */
    public static boolean isNeedPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Rudimentary methods wrapping the use of a LocalBroadcastManager to simplify the process
     * of notifying other classes when a particular fragment is notified that a permission is
     * granted.
     * <p>
     * To be notified when a permission has been granted, create a new broadcast receiver
     * and register it using {@link #registerPermissionReceiver(Context, BroadcastReceiver, String)}
     * <p>
     * E.g.
     * <p>
     * final BroadcastReceiver receiver = new BroadcastReceiver() {
     *
     * @Override public void onReceive(Context context, Intent intent) {
     * refreshContactsView();
     * }
     * }
     * <p>
     * PermissionsUtil.registerPermissionReceiver(getActivity(), receiver, READ_CONTACTS);
     * <p>
     * If you register to listen for multiple permissions, you can identify which permission was
     * granted by inspecting {@link Intent#getAction()}.
     * <p>
     * In the fragment that requests for the permission, be sure to call
     * {@link #notifyPermissionGranted(Context, String)} when the permission is granted so that
     * any interested listeners are notified of the change.
     */
    public static void registerPermissionReceiver(Context context, BroadcastReceiver receiver,
                                                  String permission) {
        final IntentFilter filter = new IntentFilter(permission);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public static void unregisterPermissionReceiver(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    public static void notifyPermissionGranted(Context context, String permission) {
        final Intent intent = new Intent(permission);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
