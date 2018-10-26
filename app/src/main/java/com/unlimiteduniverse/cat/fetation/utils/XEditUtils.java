package com.unlimiteduniverse.cat.fetation.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author chengxinping
 * @date 2017年12月26日 09:29:45
 */
public class XEditUtils {
    public void set(final EditText et, final String regular, final String msg) {
        et.addTextChangedListener(new TextWatcher() {
            String before = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                before = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(regular) && !"".equals(s.toString())) {
                    et.setText(before);
                    et.setSelection(et.getText().toString().length());
                    if (msg != null) {
                        Toast.makeText(et.getContext(), msg, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }
}