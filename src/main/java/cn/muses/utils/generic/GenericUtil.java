/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author miaoqiang
 * @date 2020/6/28.
 */
public class GenericUtil {

    /**
     * 通过反射, 获得定义Class时声明的父类的范型参数的类型.
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenericType(Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的范型参数的类型.
     *
     * @param clazz The class to introspect
     * @param index the Index of the generic declaration, start from 0.
     * @return
     */
    public static Class getSuperClassGenericType(Class clazz, int index)
        throws IndexOutOfBoundsException, ClassCastException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index < 0 || index >= params.length) {
            throw new IndexOutOfBoundsException(
                String.format("The index: %s, is out of params bounds: [0, %s)", index, params.length));
        }

        Type type = params[index];
        if (!(type instanceof Class)) {
            throw new ClassCastException(
                String.format("%s can not cast to java.lang.Class", type.getClass().getName()));
        }

        return (Class) type;
    }
}
