package com.niubility.library.utils;

import com.niubility.library.base.BaseEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @Describe：
 * @date：2019-01-04
 */
public class EventUtils {

    public static void post(String string) {
        EventBus.getDefault().post(new BaseEvent(string));
    }

    public static void post(String string, Object object) {
        EventBus.getDefault().post(new BaseEvent(string, object));
    }
}
