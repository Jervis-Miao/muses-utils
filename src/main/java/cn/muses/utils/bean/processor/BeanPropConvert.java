/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean.processor;

import java.lang.annotation.Annotation;

/**
 * @author miaoqiang
 * @date 2019/6/17.
 */
public interface BeanPropConvert {

    /**
     * bean 属性类型转换器
     *
     * @param source
     * @param annotation
     * @return
     */
    Object convert(Object source, Annotation annotation);

}
