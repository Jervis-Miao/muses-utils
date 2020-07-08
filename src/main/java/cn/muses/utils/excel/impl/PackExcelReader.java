/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.excel.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.muses.utils.excel.AbstractExcelReader;
import cn.muses.utils.excel.dto.PackDTO;

/**
 * @author miaoqiang
 * @date 2020/7/8.
 */
public class PackExcelReader extends AbstractExcelReader<PackDTO, Void> {

    @Override
    public Void format(List<PackDTO> data) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("C:\\Users\\miaoqiang\\Desktop\\产品要素整理.sql");
            fw.write("set define off;\n");
            fw.write("\n");
            for (PackDTO p : data) {
                fw.write(p.toString() + "\n");
            }
            fw.write("\n");
            fw.write("commit;\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fw) {
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
