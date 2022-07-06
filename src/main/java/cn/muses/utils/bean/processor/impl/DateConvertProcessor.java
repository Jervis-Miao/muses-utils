/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.bean.processor.impl;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import cn.muses.utils.bean.DateConvert;
import cn.muses.utils.bean.processor.BeanPropConvert;

/**
 * @author miaoqiang
 * @date 2019/6/17.
 */
public class DateConvertProcessor implements BeanPropConvert {

    @Override
    public Object convert(Object source, Annotation annotation) {
        Object ret = null;
        DateConvert dateConver = (DateConvert)annotation;
        if (dateConver.sourceClass().isInstance(source)) {
            try {
                switch (dateConver.targetClass()) {
                    case STRING:
                        if (source instanceof Date) {
                            ret = DateFormatUtils.format((Date)source, dateConver.pattern());
                        }
                        break;
                    case LONG:
                        if (source instanceof Date) {
                            ret = ((Date)source).getTime();
                        }
                        break;
                    case DATE:
                        if (source instanceof String) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(dateConver.pattern());
                            ret = dateFormat.parse((String)source);
                        } else if (source instanceof Long) {
                            ret = new Date((Long)source);
                        }
                        break;
                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

}
