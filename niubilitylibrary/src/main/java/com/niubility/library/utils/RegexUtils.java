package com.niubility.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     *     格式是由“.”分割的四部分，每部分的范围是0-255；
     *
     *     每段的正则可以分几部分来写：200—255；100-199；10-99；0-9；
     *
     *     每一部分对应的正则表达式：   2[0-4]\d|25[0-5];   1\d{2};      [1-9]\d;  \d
     *
     *     所以连起来就是\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5]
     */
    public static final String regex_ip = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\."
            + "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\."
            + "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\."
            + "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";

    /**
     * 格式是0-65535,。
     *
     * 同理可以分为几部分来构造：60000-65535；10000-59999；1000-9999；100-999；10-99；0-9；
     *
     * 每部分的正则是：           6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5];       [1-5]\d{4};      [1-9]\d{3};     [1-9]\d{2};[1-9]\d; [0-9]
     *
     * 整理完就是：([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])
      */
    public static final String regex_port = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";

    public static final String regex_ip_port = regex_ip + "\\:" + regex_port;


    /**
     * 匹配正则
     * @param regex 正则表达式
     * @param content 待匹配字符串
     * @return 是否匹配
     */
    public static boolean match(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher m=p.matcher(content);
        return m.matches();
    }


}

