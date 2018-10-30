package com.unlimiteduniverse.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.unlimiteduniverse.common.R;


/**
 * Created by Irvin
 * on 2017/9/12 0012.
 */

public class CheckEmptyUtils {

    public static final int FLAG_PHONE_NUMBER_NOT_NULL = 0x00;
    public static final int FLAG_PASSWORD_NOT_NULL = 0x01;
    public static final int FLAG_SOME_KIND_OF_NAME_NOT_NULL = 0x02;
    public static final int FLAG_VERIFY_CODE_NOT_NULL = 0x03;
    public static final int FLAG_OLD_PASSWORD_NOT_NULL = 0x04;
    public static final int FLAG_NEW_PASSWORD_NOT_NULL = 0x05;
    public static final int FLAG_SEARCH_NOT_NULL = 0x06;
    public static final int FLAG_SUGGESTIONS_NOT_NULL = 0x07;
    public static final int FLAG_NAME_NOT_NULL = 0x08;
    public static final int FLAG_NICK_NAME_NOT_NULL = 0x09;

    /**
     * @param view    The view we judge
     * @param context context
     * @param flag    which text to show
     * @return True mean empty
     */
    public static boolean checkIsEmpty(TextView view, Context context, int flag) {
        if (view.getText().toString().trim().length() <= 0) {
            showCheckResultDialog(flag, context);
            return true;
        }

        return false;
    }

    public static boolean checkIsEmpty(String text, Context context, int flag) {
        if (text == null) {
            showCheckResultDialog(flag, context);
            return true;
        }
        if (text != null && text.length() <= 0) {
            showCheckResultDialog(flag, context);
            return true;
        }
        return false;
    }

    public static boolean checkIsLessNumber(TextView view, Context context, int minNumber, int flag) {
        if (view.getText().length() < minNumber) {
            showCheckResultDialog(flag, context, minNumber);
            return true;
        }
        return false;
    }

    private static void showCheckResultDialog(int flag, Context context) {
        String title = context.getResources().getString(R.string.error);
        String Message = context.getResources().getString(R.string.can_not_be_null);
        String content;
        switch (flag) {
            case FLAG_PHONE_NUMBER_NOT_NULL:
                content = context.getResources().getString(R.string.phone_number);
                break;
            case FLAG_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.password);
                break;
            case FLAG_SOME_KIND_OF_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.some_kind_of_name);
                break;
            case FLAG_VERIFY_CODE_NOT_NULL:
                content = context.getResources().getString(R.string.verify_code);
                break;
            case FLAG_OLD_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.old_password);
                break;
            case FLAG_NEW_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.new_password);
                break;
            case FLAG_SEARCH_NOT_NULL:
                content = context.getResources().getString(R.string.search);
                break;
            case FLAG_SUGGESTIONS_NOT_NULL:
                content = context.getResources().getString(R.string.suggestions);
                break;
            case FLAG_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.name);
                break;
            case FLAG_NICK_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.nickname);
                break;
            default:
                return;
        }

        title = content + title;
        Message = Message + content;

        final AlertDialog checkResultDialog = new AlertDialog.Builder(context)

                .setMessage(Message)
                .setNegativeButton(context.getResources().getString(R.string.confirm),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .show();
        checkResultDialog.setCanceledOnTouchOutside(true);
    }

    private static void showCheckResultDialog(int flag, Context context, int minNumber) {
        String title = context.getResources().getString(R.string.error);
        String Message = context.getResources().getString(R.string.can_not_less_number) + minNumber + "位的";
        String content;
        switch (flag) {
            case FLAG_PHONE_NUMBER_NOT_NULL:
                content = context.getResources().getString(R.string.phone_number);
                break;
            case FLAG_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.password);
                break;
            case FLAG_SOME_KIND_OF_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.some_kind_of_name);
                break;
            case FLAG_VERIFY_CODE_NOT_NULL:
                content = context.getResources().getString(R.string.verify_code);
                break;
            case FLAG_OLD_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.old_password);
                break;
            case FLAG_NEW_PASSWORD_NOT_NULL:
                content = context.getResources().getString(R.string.new_password);
                break;
            case FLAG_SEARCH_NOT_NULL:
                content = context.getResources().getString(R.string.search);
                break;
            case FLAG_SUGGESTIONS_NOT_NULL:
                content = context.getResources().getString(R.string.suggestions);
                break;
            case FLAG_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.name);
                break;
            case FLAG_NICK_NAME_NOT_NULL:
                content = context.getResources().getString(R.string.nickname);
                break;
            default:
                return;
        }

        title = content + title;
        Message = Message + content;

        final AlertDialog checkResultDialog = new AlertDialog.Builder(context)
                .setMessage(Message)
                .setNegativeButton(context.getResources().getString(R.string.confirm),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .show();
        checkResultDialog.setCanceledOnTouchOutside(true);
    }
}
