/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils;

import java.util.List;
import java.util.Map;

import cn.muses.utils.excel.IExcelReader;
import cn.muses.utils.excel.dto.CashValueDTO;
import cn.muses.utils.excel.impl.CashValueExcelReader;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class ExcelReaderTest {

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
