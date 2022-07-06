/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import cn.muses.utils.bean.processor.BeanPropConverFactory;
import cn.muses.utils.bean.processor.BeanPropConvert;

/**
 * @author miaoqiang
 * @date 2019/6/17.
 */
public class MyBeanUtils extends BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(MyBeanUtils.class);

    private MyBeanUtils() {}

    /**
     * 拷贝source对象中的空字段
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyAndConvertIfSourceNull(Object source, Object target, @Nullable Class<?> editable)
        throws BeansException {
        copy(source, target, editable, FieldScope.SOURCE_NULL);
    }

    /**
     * 属性拷贝
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyAndConvertProperties(Object source, Object target) throws BeansException {
        copy(source, target, null, FieldScope.ALL);
    }

    /**
     * 属性拷贝
     *
     * @param source
     * @param target
     * @param editable
     * @param scope
     * @throws BeansException
     */
    private static void copy(Object source, Object target, @Nullable Class<?> editable, FieldScope scope)
        throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                    "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

        for (PropertyDescriptor targetPd : targetPds) {
            if (!isNeedCopyAndConvert(scope, target, targetPd)) {
                continue;
            }
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                Field field = null;
                try {
                    field = actualEditable.getDeclaredField(targetPd.getName());
                } catch (NoSuchFieldException e) {
                    // 应当不会走到这，只是处理一下方法异常
                    log.info("Can not find field: {}", targetPd.getName());
                    continue;
                }
                String targetPropName = targetPd.getName();
                NameConvert nameConver = AnnotationUtils.findAnnotation(field, NameConvert.class);
                if (null != nameConver) {
                    targetPropName = nameConver.sourceName();
                }
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPropName);
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            // processor value
                            Object convertVal = propConvert(field.getAnnotations(), value);

                            if (null != convertVal && writeMethod.getParameterTypes()[0].isInstance(convertVal)) {
                                writeMethod.invoke(target, convertVal);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName()
                                + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 是否需要拷贝属性
     *
     * @param scope
     * @param target
     * @param targetPd
     * @return
     */
    private static Boolean isNeedCopyAndConvert(FieldScope scope, Object target, PropertyDescriptor targetPd) {
        switch (scope) {
            case SOURCE_NULL:
                Method readMethod;
                if (null == targetPd || null == (readMethod = targetPd.getReadMethod())) {
                    return Boolean.FALSE;
                }
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                try {
                    return null == readMethod.invoke(target);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.info("Can not read field: {}", targetPd.getName(), e);
                    return Boolean.FALSE;
                }
            default:
                return Boolean.TRUE;
        }
    }

    /**
     * 属性类型转换
     *
     * @param annotations
     * @param value
     * @return
     */
    private static Object propConvert(Annotation[] annotations, Object value) {
        if (ArrayUtils.isNotEmpty(annotations)) {
            for (Annotation annotation : annotations) {
                BeanPropConvert convert = BeanPropConverFactory.get(annotation);
                if (null != convert) {
                    return convert.convert(value, annotation);
                }
            }
        }
        return value;
    }

    /**
     * bean转换Map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

    /**
     * 字段范围
     */
    enum FieldScope {
        // 全部
        ALL,

        // 源数据字段为空
        SOURCE_NULL
    }
}
