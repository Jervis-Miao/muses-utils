/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.muses.utils.file.read.excel.AbstractExcelReader;
import cn.muses.utils.file.read.excel.dto.CashValueDTO;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class CashValueExcelReader extends AbstractExcelReader<CashValueDTO, Map<String, List<CashValueDTO>>> {

    @Override
    public Map<String, List<CashValueDTO>> format(List<CashValueDTO> data) {
        final Map<String, List<CashValueDTO>> dataMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final String[] split = o1.split("-");
                final String[] split1 = o2.split("-");
                int compare = 0;
                for (int i = 0; i < split.length; i++) {
                    // if (i == 0 || i == split.length - 1) {
                    compare = Integer.valueOf(split[i]).compareTo(Integer.valueOf(split1[i]));
                    // } else {
                    // compare = split[i].compareTo(split1[i]);
                    // }
                    if (0 != compare) {
                        return compare;
                    }
                }
                return compare;
            }
        });
        data.forEach(d -> {
            final Integer np = d.getNp();
            if (!(np.equals(4) || np.equals(9) || np.equals(19))) {
                String key = new StringBuilder(d.getSex().toString()).append("-").append(np).append("-")
                    .append(d.getAge()).append("-").append(d.getCa()).toString();
                List<CashValueDTO> categorized;
                if (null == (categorized = dataMap.get(key))) {
                    categorized = new ArrayList<>(64);
                    dataMap.put(key, categorized);
                }
                categorized.add(d);
            }
        });

        return dataMap;
    }

    @Override
    public void readToFile(FileWriter fileWriter, String data) throws IOException {
        fileWriter.write(data + "\n");
        fileWriter.flush();
    }
}
