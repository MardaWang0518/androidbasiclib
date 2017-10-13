package com.hxh.component.basicore.net.cookie;


import com.hxh.component.basicore.util.Utils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by hxh on 2017/3/30.
 */
public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore = new PersistentCookieStore(Utils.getApplicationContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
