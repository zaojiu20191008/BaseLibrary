package com.niubility.library.utils.wxpay;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    /*public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";*/

    /**
     * getWxpayfaceCode方法中 face_authtype 参数可选值
     * “FACEPAY” 刷脸支付
     * “FACEID” 人脸识别
     */
    public static final String FACEPAY = "FACEPAY";
    public static final String FACEPAY_DELAY = "FACEPAY_DELAY";
    public static final String FACEID = "FACEID";

    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    /*public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";*/



    /**
     * 接口请求成功
     * 解决方案：无
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 接口请求失败
     * 解决方案：展示失败原因
     */
    public static final String FAIL = "FAIL";

    /**
     * 接口请求错误
     * 解决方案：展示错误原因（该请求无法通过重试解决）
     */
    public static final String ERROR = "ERROR";

    /**
     * 参数错误
     * 解决方案：参照错误提示
     */
    public static final String PARAM_ERROR = "PARAM_ERROR";

    /**
     * 接口返回错误
     * 解决方案：系统异常，可重试该请求
     */
    public static final String SYSTEMERROR = "SYSTEMERROR";

    /**
     * 用户退出了人脸识别
     * 解决方案：返回到结账流程
     */
    public static final String USER_CANCEL = "USER_CANCEL";

    /**
     * 用户选择扫码支付
     * 解决方案：进入扫码支付流程
     */
    public static final String SCAN_PAYMENT = "SCAN_PAYMENT";


    /**
     * 余额不足
     */
    public static final String NOTENOUGH = "NOTENOUGH";

    /**
     * 交易失败
     */
    public static final String TRADE_ERROR = "TRADE_ERROR";

    /**
     * 无效请求
     */
    public static final String INVALID_REQUEST = "INVALID_REQUEST";


}

