/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *   目前支持的类型为：
 *   String <-> Date
 *   Long <-> Date
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/6/17.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConvert {

    public Class<?> sourceClass();

    public TargetType targetClass();

    public String pattern() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换类型
     */
    enum TargetType {

        // 字符串
        STRING,

        // 时间戳
        LONG,

        // 日期
        DATE
    }
}
