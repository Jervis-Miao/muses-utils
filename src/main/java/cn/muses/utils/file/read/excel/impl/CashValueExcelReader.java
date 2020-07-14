/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.muses.utils.file.read.excel.AbstractExcelReader;
import cn.muses.utils.file.read.excel.dto.CashValueDTO;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class CashValueExcelReader extends AbstractExcelReader<CashValueDTO, Map<String, List<CashValueDTO>>> {

    @Override
    public Map<String, List<CashValueDTO>> format(List<CashValueDTO> data) {
        final Map<String, List<CashValueDTO>> dataMap = new LinkedHashMap<>(512);
        data.forEach(d -> {
            String key =
                new StringBuilder(d.getSex()).append("-").append(d.getAge()).append("-").append(d.getNp())
                    .append("-").append(d.getBp()).toString();
            List<CashValueDTO> categorized;
            if (null == (categorized = dataMap.get(key))) {
                categorized = new ArrayList<>(64);
                dataMap.put(key, categorized);
            }
            categorized.add(d);
        });

        return dataMap;
    }
}
