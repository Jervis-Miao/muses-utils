/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel;

import java.lang.annotation.*;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cell {

    /**
     * 单元格索引, 从0开始
     *
     * @see Row#getCell(int)
     * @return
     */
    int cellnum();
}
