/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean.processor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.muses.utils.bean.NameConvert;

/**
 * @author miaoqiang
 * @date 2019/6/17.
 */
public class BeanPropConverFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanPropConverFactory.class);
    private static final Map<String, BeanPropConvert> CONVERT_BEANS = new HashMap<>(2);
    private static final Object LOCK = new Object();

    public static BeanPropConvert get(Annotation annotation) {
        if (annotation instanceof NameConvert) {
            return null;
        }
        Class<? extends Annotation> aClass = annotation.annotationType();
        String name = aClass.getSimpleName();
        BeanPropConvert conver = CONVERT_BEANS.get(name);
        if (null == conver) {
            synchronized (LOCK) {
                try {
                    String fullyClassName = new StringBuilder(BeanPropConverFactory.class.getPackage().getName())
                        .append(".").append("impl").append(".").append(name).append("Processor").toString();
                    Class<?> processorClass = Class.forName(fullyClassName);
                    conver = (BeanPropConvert)processorClass.newInstance();
                    CONVERT_BEANS.put(name, conver);
                } catch (Exception e) {
                    log.info("BeanPropConver exception: {}", e.getMessage(), e);
                }
            }
        }
        return conver;
    }

}
