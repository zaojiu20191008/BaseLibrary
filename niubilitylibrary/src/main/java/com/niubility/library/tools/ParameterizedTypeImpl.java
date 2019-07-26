package com.niubility.library.tools;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Describe：
 * @Date：2019-07-23
 */
public class ParameterizedTypeImpl implements ParameterizedType {

    Class clazz;

    public ParameterizedTypeImpl(Class cls) {
        clazz = cls;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
