/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.excel.impl;

import cn.muses.utils.excel.AbstractExcelReader;

import java.util.List;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class JobExcelReader extends AbstractExcelReader {

    /**
     * 解析寿险职业EXCEL表格
     *
     * @param list
     */
    public void getJobInfo(List<List<String>> list) {
        if (list != null) {
            Long loopfirst = 1L;
            Long loopsecond = 1L;
            Long loopthird = 1L;
            Long comId = 17828543L;
            Long comCode = 873000L;
            Long firstId = 0L;
            Long secondId = 0L;
            Long thirdId = 0L;
            String tableName =
                "insert into ins.ins_job_info (JOB_ID, PARENT_JOB_ID, INS_COM_ID, NAME, JOB_CODE, GRADE, REMARK, LEAT, WEIGHT, STATUS, ADDER_NO, UPDATER_NO, ADDER_NAME, UPDATER_NAME, ADD_TIME, UPDATE_TIME) values (";
            System.out.println("set define off;");
            System.out.println("");
            for (int i = 0; i < list.size(); i++) {
                if (i >= 1) {
                    List<String> cellList = list.get(i);
                    for (int j = 0; j < cellList.size(); j++) {
                        if (j >= 0) {
                            if (cellList.get(j).equals("")) {
                                continue;
                            } else {
                                if (j == 0) {
                                    firstId = (comCode * 100 + loopfirst) * 100000;
                                    System.out.println(tableName + firstId + ",0" + "," + comId + ",'"
                                        + cellList.get(j).substring(2) + "','" + cellList.get(j).substring(0, 2)
                                        + "',99" + ",'" + "',0" + ",1" + ",1" + ",0" + ",0" + ",'admin'"
                                        + ",'admin'" + ",sysdate" + ",sysdate);");
                                    // }
                                    loopsecond = 1L;
                                    loopfirst++;
                                } else if (j == 1) {
                                    secondId = (firstId / 1000 + loopsecond) * 1000;
                                    System.out.println(tableName + secondId + "," + firstId + "," + comId + ",'"
                                        + cellList.get(j).substring(4) + "','" + cellList.get(j).substring(0, 4)
                                        + "',99" + ",'" + "',0" + ",1" + ",1" + ",0" + ",0" + ",'admin'"
                                        + ",'admin'" + ",sysdate" + ",sysdate);");
                                    loopthird = 1L;
                                    loopsecond++;
                                } else if (j == 2) {
                                    thirdId = secondId + loopthird;
                                    System.out.println(tableName + thirdId + "," + secondId + "," + comId + ",'"
                                        + cellList.get(3) + "','" + cellList.get(j) + "',"
                                        + (int) Double.parseDouble(cellList.get(4))
                                        + ",'',1,1,1,0,0,'admin','admin',sysdate,sysdate);");
                                    loopthird++;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("");
            System.out.println("commit;");
        }
    }
}
