package com.niubility.library.utils;

import android.text.TextUtils;

import com.niubility.library.tools.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonUtils {

    /**
     * StringBuilder转List
     *
     * @param str
     * @param regex
     * @return
     */
    public static List<String> sbConversionList(String str, String regex) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(regex);
        return arrayConversionList(split);
    }

    /**
     * String[]转List
     *
     * @param array
     * @return
     */
    public static List<String> arrayConversionList(String[] array) {
        if (array == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    /**
     * List转StringBuilder
     *
     * @param list
     * @param regex 分隔符
     * @return
     */
    public static String listConversionSb(List<String> list, String regex) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(regex);
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        return sb.toString();
    }

    /**
     * 解析json --- List
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(String json, Class cls) {
        if (json.length() == 0) {
            return null;
        }
        Type type = new ParameterizedTypeImpl(cls);
        return GsonUtils.getInstance().getGson().fromJson(json, type);
    }

    /**
     * 解析json --- 对象
     *
     * @param json
     * @return
     */
    public static Object getObject(String json, Class cls) {
        if (json.length() == 0) {
            return null;
        }
        return GsonUtils.getInstance().getGson().fromJson(json, cls);
    }
}
