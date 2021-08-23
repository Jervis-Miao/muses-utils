/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.muses.utils.file.read.IFileRead;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public interface IExcelReader<T, R> extends IFileRead {

    /**
     * 读取Excel，并逐行转化为对象
     *
     * @param filePath Excel文件路径
     * @param sheetIndex sheet索引, 从0开始 {@link Workbook#getSheetAt}
     * @param rowStartNum 起始行, 从0开始 {@link Sheet#getRow}
     * @param rowEndNum 终止行, 从0开始 {@link Sheet#getRow}
     * @return
     */
    public List<T> parse(String filePath, int sheetIndex, int rowStartNum, int rowEndNum);

    /**
     * 格式化数据
     *
     * @param data
     * @return
     */
    public default R format(List<T> data) {
        return (R)data.toString();
    }

    /**
     * 输出到指定文件
     *
     * @param fileWriter
     * @param data
     */
    public default void readToFile(FileWriter fileWriter, String data) throws IOException {
        return;
    }
}
