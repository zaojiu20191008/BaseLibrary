package com.niubility.library.http.exception;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.niubility.library.http.base.HttpResult;
import com.niubility.library.utils.GsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * 网络错误处理引擎
 */
public class HttpExceptionEngine {

    public static final String TAG = "HttpExceptionEngine";

    public static class Error {
        //错误类型
        public static final int HTTP = 1000001;
        public static final int BUSSINESS = 1000002;
        public static final int JSON = 1000003;
        public static final int NETWORK = 1000004;
        public static final int UNKNOWN = -1000005;
        //错误类型

    }

    public static final String ErrorCode = "ErrorCode";
    public static final String ErrorType = "ErrorType";
    public static final String ErrorMsg = "ErrorMsg";

    public static boolean isBussinessError(int errorType) {
        return Error.BUSSINESS == errorType;
    }
    /**
     * 是否是业务错误 (后台正常返回，但error != 200)
     */
    public static boolean isBussinessError(Map<String, Object> map) {
        int errorType = (int) map.get(HttpExceptionEngine.ErrorType);
        return isBussinessError(errorType);
    }

    public static ApiException handleException(Throwable e) {
        int err_type = Error.UNKNOWN;
        int err_code = Error.UNKNOWN;
        String err_msg = "未知错误";
        if (e instanceof HttpException) {//   HTTP错误
            HttpException httpException = (HttpException) e;
            err_type = Error.HTTP;
            err_code = httpException.code();
            err_msg = httpException.response().toString();
        } else if (e instanceof SocketTimeoutException) {//  连接超时
            err_type = Error.NETWORK;
            err_msg = "服务器响应超时";
        } else if(e instanceof ConnectException) {   //  连接异常
            err_type = Error.NETWORK;
            err_msg = "网络连接异常，请检查网络";
        } else if (e instanceof UnknownHostException) {
            err_type = Error.NETWORK;
            err_msg = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            err_type = Error.NETWORK;
            err_msg = "未知的服务器错误";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            err_type = Error.JSON;
            err_msg = "解析错误";
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            err_type = Error.BUSSINESS;
            err_code = apiException.getErr_code();
            err_msg = apiException.getErr_msg();
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("ErrorType", err_type);
        result.put("ErrorCode", err_code);
        result.put("ErrorMsg", err_msg);

        return new ApiException(err_type, err_msg);
    }

    public static Map<String, Object> handleExceptionToMap(Throwable e) {

        int err_type = Error.UNKNOWN;
        int err_code = Error.UNKNOWN;
        String err_msg = "未知错误";
        if (e instanceof HttpException) {//   HTTP错误
            HttpException httpException = (HttpException) e;
            err_type = Error.HTTP;
            ResponseBody responseBody = httpException.response().errorBody();
            try {
                if(responseBody != null) {
                    String response = responseBody.string();
                    HttpResult result = GsonUtils.getInstance().getGson().fromJson(response, HttpResult.class);
                    if (result != null) {
                        err_code = result.getErr_code();
                        err_msg = result.getErr_msg();
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(err_code == Error.UNKNOWN) {
                err_code = httpException.code();
                err_msg = httpException.response().toString();
            }
        } else if (e instanceof SocketTimeoutException) {//  连接超时
            err_type = Error.NETWORK;
            err_msg = "服务器响应超时";
        } else if(e instanceof ConnectException) {   //  连接异常
            err_type = Error.NETWORK;
            err_msg = "网络连接异常，请检查网络";
        } else if (e instanceof UnknownHostException) {
            err_type = Error.NETWORK;
            err_msg = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            err_type = Error.NETWORK;
            err_msg = "未知的服务器错误";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            err_type = Error.JSON;
            err_msg = "解析错误";
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            err_type = Error.BUSSINESS;
            err_code = apiException.getErr_code();
            err_msg = apiException.getErr_msg();
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put(ErrorType, err_type);
        result.put(ErrorCode, err_code);
        result.put(ErrorMsg, err_msg);

        String sb = "handleExceptionToMap: --> " +
                ErrorType + ":" + err_type +
                ", " + ErrorCode + ":" + err_code +
                ", " + ErrorMsg + ":" + err_msg;
        Log.i(TAG, sb);

        return result;
    }
}
