package com.bobo.something.base;

import android.support.annotation.NonNull;

import com.bobo.something.Config;
import com.bobo.something.utils.AppUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by huangbo on 2017/6/20.
 */

public class BaseHttpRequest {

    public static final int RequestTypeGet = 0;
    public static final int RequestTypePost = 1;
    public static final int RequestTypeDelete = 2;
    public static final int RequestTypePut = 3;
    public static final int RequestTypePostFile = 4;
    private int requestType = RequestTypeGet;

    protected String url;
    protected Object postObject;
    protected HashMap<String, String> urlParams = null;

    private String fileName;
    private boolean showLoading;
    private static String baseParam = null;
    private OnHttpResult onResultCallback;

    public interface OnHttpResult {
        void onHttpSuccess(String data);

        void onHttpFailure(String message);
    }

    public BaseHttpRequest() {
        mTag = this.getClass().getSimpleName();
    }

    public BaseHttpRequest(String url) {
        this.url = url;
        this.postObject = "";
    }

    public BaseHttpRequest(String url, Object postObject) {
        this.url = url;
        this.postObject = postObject;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getPostData() {
        if (postObject == null) {
            return "";
        }
        if (postObject instanceof String) {
            return (String) postObject;
        } else {
            return new Gson().toJson(postObject);
        }

    }

    public String getFilterUrl() {
        return filterUrl(getFullUrl());
    }

    @NonNull
    private String getFullUrl() {
        String ful = url;
        if (urlParams != null) {
            ful = appendUrlParams(ful);
        }
        if (ful.indexOf('?') > 0) {
            ful += "&" + getBaseParam();
        } else {
            ful += '?' + getBaseParam();
        }

        if (ful.contains("http")) {
            return ful;
        }
        return Config.Base_URL_API + ful;
    }

    String filterUrl(String url) {
        if (url.contains(" ")) {
            url = url.replace(" ", "%20");
        }
        if (url.contains("\"")) {
            url = url.replace("\"", "%22");
        }
        if (url.contains("{")) {
            url = url.replace("{", "%7B");
        }
        if (url.contains("}")) {
            url = url.replace("{", "%7D");
        }
        return url;
    }

    private String appendUrlParams(String ful) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            sb.append("&");
            sb.append(entry.getKey() + "=" + entry.getValue());
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            sb.deleteCharAt(sb.length() - 1);
            s = sb.toString();
        }

        if (s.length() > 0) {
            if (url.indexOf('?') > 0) {
                ful = url + s;
            } else {
                sb.deleteCharAt(0);
                ful = url + '?' + sb.toString();
            }
        }
        return ful;
    }

    /**
     * 上传文件时必须设置
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void doHttpRequest(boolean showLoading, OnHttpResult callBack) {
        this.showLoading = showLoading;
        this.onResultCallback = callBack;
        HttpResponseHandler handler = new HttpResponseHandler();
        switch (requestType) {
            case RequestTypeGet:
                doGetString(getFilterUrl(), handler);
                break;
            case RequestTypePost:
                postString(getFilterUrl(), getPostData(), handler);
                break;
            case RequestTypeDelete:
                break;
            case RequestTypePut:
                break;
            case RequestTypePostFile:
                uploadFile(getFilterUrl(), fileName, handler);
                break;
        }
    }

    public void sendRequest(OnHttpResult callBack) {
        sendRequest(true, callBack);
    }

    public void sendRequest(boolean showLoading, OnHttpResult callBack) {
        doHttpRequest(showLoading, callBack);
    }

    private String getBaseParam() {
        if (baseParam == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("appBuild=" + AppUtil.getAppVersion() + "&appVersion=" + AppUtil.getAppVersionName())
                    .append("&os=Android&osVersion=" + android.os.Build.VERSION.RELEASE)
                    .append("&deviceId=" + AppUtil.GetDeviceIMEI())
                    .append("&devicePlatform=" + AppUtil.getMobileModel());
            baseParam = sb.toString();
        }
        return baseParam;
    }

    Object mTag;

    public void stopRequest() {
        OkHttpUtils.getInstance().cancelTag(mTag);
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    private void postString(String url, String params, StringCallback callBack) {
        OkHttpUtils.postString()
                .url(url)
                .tag(mTag)
                .headers(getHeaders())
                .content(params)
                .mediaType(MediaType.parse("application/json;charset=utf-8"))
                .build()
                .execute(callBack);
    }

    /**
     * get 请求
     *
     * @param url
     * @param callBack
     */
    private void doGetString(String url, StringCallback callBack) {
        OkHttpUtils.get()
                .url(url)
                .tag(mTag)
                .headers(getHeaders())
                .build()
                .execute(callBack);
    }

    /**
     * 上传文件 图片
     *
     * @param url
     * @param fileName
     * @param callBack
     */
    private void uploadFile(String url, String fileName, StringCallback callBack) {
        File file = new File(fileName);
        OkHttpUtils.post()
                .addFile("image", file.getName(), file)
                .headers(getHeaders())
                .url(url)
                .tag(mTag)
                .build()
                .execute(callBack);
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token");

        return headers;
    }


    private class HttpResponseHandler extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(String response, int id) {
        }
    }
}
