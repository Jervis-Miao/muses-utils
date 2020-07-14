/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.muses.utils.file.read.excel.IExcelReader;
import cn.muses.utils.file.read.excel.dto.CashValueDTO;
import cn.muses.utils.file.read.excel.impl.CashValueExcelReader;
import cn.muses.utils.file.read.excel.impl.PackExcelReader;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class ExcelReaderTest {

    @Test
    public void packTest() throws IOException {
        IExcelReader poi = new PackExcelReader();
        String filePath = "C:\\Users\\miaoqiang\\Desktop\\7.8最终版【wap站改版】产品要素整理.xlsx";
        int sheetIndex = 0;
        int rowStartNum = 1;
        int rowEndNum = 229;
        poi.format(poi.parse(filePath, sheetIndex, rowStartNum, rowEndNum));
    }

    /**
     * Excel表格解析测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IExcelReader poi = new CashValueExcelReader();
        String filePath = "C:\\Users\\miaoqiang\\Downloads\\心相随资料\\04-2_爱心人寿心相随年金保险现金价值全表.xlsx";
        int sheetIndex = 0;
        int rowStartNum = 3;
        int rowEndNum = 20930;
        List<CashValueDTO> list = poi.parse(filePath, sheetIndex, rowStartNum, rowEndNum);
        Map<String, List<CashValueDTO>> format = (Map<String, List<CashValueDTO>>) poi.format(list);
        format.keySet().forEach(k -> {
            System.out.println(String.format("%s: \"%s\"", k, format.get(k)));
        });
        // 解析职业
        // poi.getJobInfo(list);
        // 解析费率
        // poi.getSXFee(list);
        // 解析最低保费条件
        // poi.getSXLimitPriceCond(list);
        // 获取满足条件的最低保费
        // poi.getSXLimitPrice(list);
    }
}
