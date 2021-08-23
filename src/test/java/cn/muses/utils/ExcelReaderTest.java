/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        String inFilePath = "C:\\Users\\jervis\\Downloads\\臻享资料包\\9.横琴臻享一生养老年金保险_现金价值表（全表）.xlsx";
        String outFilePath = "C:\\Users\\jervis\\Downloads\\臻享资料包\\9.横琴臻享一生养老年金保险_现金价值表（全表）.txt";
        int sheetIndex = 0;
        int rowStartNum = 4;
        int rowEndNum = 130391;
        List<CashValueDTO> list = poi.parse(inFilePath, sheetIndex, rowStartNum, rowEndNum);
        Map<String, List<CashValueDTO>> format = (Map<String, List<CashValueDTO>>) poi.format(list);
        FileWriter fw = new FileWriter(outFilePath);
        try {
            int i = 0;
            final Set<String> keySet = format.keySet();
            for (String k : keySet) {
                System.out.println("当前第：" + i + "行");
                i++;
                poi.readToFile(fw, String.format("%s: \"%s\"", k, format.get(k)));
            }
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
