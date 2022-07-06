/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 同类型的Field切换
 *
 * @author miaoqiang
 * @date 2019/6/17.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConvert {

    @AliasFor("sourceName")
    String value() default "";

    @AliasFor("value")
    String sourceName() default "";
}
