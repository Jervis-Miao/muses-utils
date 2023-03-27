/*
 * Copyright 2021 XYZ Co., Ltd. All Rights Reserved
 */
package cn.muses.utils.match;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.muses.utils.json.JsonMapper;

/**
 * @author miaoqiang
 * @date 2022/7/5.
 */
public class MatchUtils {
    private static final Logger logger = LoggerFactory.getLogger(MatchUtils.class);

    private MatchUtils() {}

    /**
     * 正则匹配提取
     *
     * @param input
     * @param regex
     * @return
     */
    public static String extractionFst(String input, String regex) {
        List<String> extractions;
        if (CollectionUtils.isNotEmpty(extractions = extractionAll(input, regex))) {
            return extractions.get(0);
        }
        return null;
    }

    /**
     * 正则匹配提取
     *
     * @param input
     * @param regex
     * @return
     */
    public static List<String> extractionAll(String input, String regex) {
        List<String[]> extractions;
        int index = 0;
        if (CollectionUtils.isNotEmpty(extractions = extraction(input, regex, index))) {
            return extractions.stream().map(e -> e[index]).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 正则匹配提取
     *
     * @param input
     * @param regex
     * @param index
     * @return
     */
    public static List<String[]> extraction(String input, String regex, int... index) {
        if (null == index) {
            return null;
        }
        List<String[]> ret = new ArrayList<>(16);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            final int groupCount = matcher.groupCount();
            String[] g = new String[index.length];
            for (int i = 0; i < index.length; i++) {
                if (index[i] <= groupCount - 1) {
                    g[i] = matcher.group(index[i] + 1);
                } else {
                    g[i] = null;
                }
            }
            ret.add(g);
        }
        return ret;
    }

    public static void main(String[] args) {
        String str = "aklsdfjlkasdfj#{123}lkd#{456}sajf#{xxx}lkasdjflk";
        List<String[]> extraction = MatchUtils.extraction(str, "\\#\\{([\\S].+?)\\}", 0);
        System.out.println(new JsonMapper().toJson(extraction));
    }
}
