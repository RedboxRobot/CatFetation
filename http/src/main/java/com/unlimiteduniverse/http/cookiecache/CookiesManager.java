package com.unlimiteduniverse.http.cookiecache;

import android.content.Context;
import android.util.Log;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Irvin
 * on 2017/8/21.
 */

public class CookiesManager implements CookieJar {
    public static String APP_PLATFORM = "app-platform";

    private static PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        if (cookieStore == null ) {
            cookieStore = new PersistentCookieStore(context);
        }
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Log.e("Irvin", "url:" + url);
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                Log.e("Irvin", "item:" + item);
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        for (Cookie item : cookies) {
            Log.e("Irvin", "item:" + item);
        }
        return cookies;
    }

    static class Customer {
        private String user_id;
        private String token;
        private String expire;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }
    }
}
