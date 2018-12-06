package com.niubility.library.http;

import java.util.Map;

public interface RequestListener<T> {
    void onSuccess(T result);

    void onFailed(Map<String, Object> map);
}
