package com.niubility.library.http.base;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import android.util.Log;

import com.niubility.library.BuildConfig;
import com.niubility.library.base.BaseApplication;
import com.niubility.library.common.constants.BaseConstants;
import com.niubility.library.utils.GetSign;
import com.niubility.library.utils.SharedPreferencesUtils;
import com.niubility.library.utils.TransCodeUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 单例， Okhttp网络请求客户端
 */
public class HttpClient {

    private final static String TAG = "HttpClient";
    private final OkHttpClient mLoginHeaderClient;
    private OkHttpClient mOkHttpClient;

    private static final long DEFAULT_CONNECT_TIMEOUT = 15;
    private static final long DEFAULT_WRITE_TIMEOUT  = 15;
    private static final long DEFAULT_READ_TIMEOUT   = 15;
    private final Interceptor mLoginInterceptor;

    /**
     * 根据类型生成并获取实例
     */
    public static HttpClient getInstance() {
        return Holder.sHttpClient;
    }

    public static class Holder {
        static HttpClient sHttpClient = new HttpClient();
    }

    private HttpClient() {

        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.i(TAG, "log: " + TransCodeUtils.decodeUnicode(message));
            }
        });

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }


        //可统一添加请求头
        mLoginInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);
                long time = sp.getLong(BaseConstants.KEY_TIME, 0);
                String session_id = sp.getString(BaseConstants.KEY_SESSION_ID, "");

                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("LC-Appkey", "723949279")
                        .addHeader("LC-Sign", GetSign.getSign(time))
                        .addHeader("LC-Session", session_id)
                        .addHeader("LC-Timestamp", String.valueOf(time))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };


        mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(httpLoggingInterceptor)
//                            .addInterceptor(networkInterceptor)
                            .retryOnConnectionFailure(true)
                            .build();

        mLoginHeaderClient = mOkHttpClient.newBuilder().addInterceptor(mLoginInterceptor).build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public OkHttpClient getLoginHeaderClient() {
        return mLoginHeaderClient;
    }

    public RequestBody createRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    /**
     * 登录头部
     * @return
     */
    public Map<String, String> getLoginHeader() {
        Map<String, String> head = new HashMap<>();
        head.put("LC-Appkey", "723949279");
        final long time = new Date().getTime() / 1000;
        head.put("LC-Sign", GetSign.getSign(time));
        head.put("LC-Session", "");
        head.put("LC-Timestamp",  String.valueOf(time));

        SharedPreferences.Editor editor = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication).edit();
        editor.putLong(BaseConstants.KEY_TIME, time).apply();
        return head;
    }

    /**
     * 动态appkey、secret, 不需session_id
     * @return
     */
    public Map<String, String> getLoginSignHeader() {
        Map<String, String> head = new HashMap<>();

        SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);

        String appkey = sp.getString(BaseConstants.KEY_APPKEY, "");
        String secret = sp.getString(BaseConstants.KEY_SECRET, "");
        final long time = new Date().getTime() / 1000;

        head.put("LC-Appkey", appkey);
        head.put("LC-Sign", GetSign.sign(appkey, secret, time));
        head.put("LC-Session", "");
        head.put("LC-Timestamp",  String.valueOf(time));

        return head;
    }

    /**
     * 登录头部
     * @return
     */
    public Map<String, String> getHeader() {
//        Map<String, String> head = new HashMap<>();
//        head.put("LC-Appkey", "723949279");
//
//        SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);
//        long time = sp.getLong(BaseConstants.KEY_TIME, 0);
//        String session_id = sp.getString(BaseConstants.KEY_SESSION_ID, "");
//
//        head.put("LC-Sign", GetSign.getSign(time));
//        head.put("LC-Session", session_id);
//        head.put("LC-Timestamp", String.valueOf(time));
//
//        return head;
        return getSignHeader();
    }

    public Map<String, String> getOldHeader() {
        Map<String, String> head = new HashMap<>();
        head.put("LC-Appkey", "723949279");

        SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);
        long time = sp.getLong(BaseConstants.KEY_TIME, 0);
        String session_id = sp.getString(BaseConstants.KEY_SESSION_ID, "");

        head.put("LC-Sign", GetSign.getSign(time));
        head.put("LC-Session", session_id);
        head.put("LC-Timestamp", String.valueOf(time));

        return head;
    }

    public Map<String,String> getHeaders(){
        Map<String, String> head = new HashMap<>();
        head.put("LC-Appkey", "24");

        SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);
        long time = sp.getLong(BaseConstants.KEY_TIME, 0);
        String session_id = sp.getString(BaseConstants.KEY_SESSION_ID, "");

        head.put("LC-Sign", GetSign.getSigns(new Date().getTime() / 1000));
        head.put("LC-Session", session_id);
        head.put("LC-Timestamp", String.valueOf(new Date().getTime() / 1000));

        return head;
    }

    /**
     * 动态获取appkey、secret
     * @return
     */
    public Map<String, String> getSignHeader() {
        Map<String, String> head = new HashMap<>();
        long time = new Date().getTime() / 1000;

        SharedPreferences sp = SharedPreferencesUtils.getInstance().getSharedPreferences(BaseApplication.sApplication);
        String appkey = sp.getString(BaseConstants.KEY_APPKEY, "");
        String secret = sp.getString(BaseConstants.KEY_SECRET, "");
        String session_id = sp.getString(BaseConstants.KEY_SESSION_ID, "");

        head.put("LC-Appkey", appkey);
        head.put("LC-Sign", GetSign.sign(appkey, secret, time));
        head.put("LC-Session", session_id);
        head.put("LC-Timestamp", String.valueOf(time));

        return head;
    }
}
