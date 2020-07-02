/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.excel.impl;

import java.util.*;

import cn.muses.utils.excel.AbstractExcelReader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import cn.muses.utils.excel.dto.FeeMode;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class FeeExcelReader extends AbstractExcelReader {

    /**
     * 解析寿险费率EXCEL表格，获取费率字典
     *
     * @param list
     */
    public void getSXFee(List<List<String>> list) {
        if (list != null) {
            System.out.println("    trial_data_tb: [");
            Map<String, FeeMode> fees = new HashMap<>();
            // 遍历行
            for (int i = 0; i < 20; i++) {
                if (i > 1) {
                    List<String> cellList = list.get(i);
                    // 遍历列
                    for (int j = 1; j < cellList.size();) {
                        if (cellList.get(j).equals("")) {
                            j = j + 3;
                            continue;
                        } else {
                            FeeMode fm = new FeeMode();
                            fm.setAge((int) Double.parseDouble(cellList.get(0)));
                            fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
                            if (j < 13) {
                                fm.setSex(Short.valueOf("1"));
                            } else {
                                fm.setSex(Short.valueOf("0"));
                            }
                            fm.setMainFee(Double.parseDouble(cellList.get(j)));
                            fm.setFee(Double.parseDouble(cellList.get(j + 1)));
                            fm.setSubFee(Double.parseDouble(cellList.get(j + 2)));
                            // fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());

                            // 打印费率数据字典
                            System.out.print("        {\"age\": \"" + fm.getAge() + "\",");
                            System.out.print("  \"sex\": \"" + fm.getSex() + "\",");
                            System.out.print("  \"PayPeriod\": \"" + fm.getPayPeriod() + "\",");
                            // System.out.print(" \"insurPeriod\": \"" + fm.getInsurPeriod() + "\",");
                            System.out.print("  \"mainFee\": \"" + fm.getMainFee() + "\",");
                            System.out.print("  \"fee\": \"" + fm.getFee() + "\",");
                            System.out.print("  \"subFee\": \"" + fm.getSubFee() + "\"");
                            if (fm.getAge() == 17 && fm.getSex() == 0 && fm.getPayPeriod().equals("1")
                                && fm.getFee() == 467) {
                                System.out.println("}");
                            } else {
                                System.out.println("},");
                            }
                            j = j + 3;
                        }
                    }
                }
            }
            System.out.println("    ],");
        }
    }

    /**
     * 解析寿险费率EXCEL表格，获取最低保费
     *
     * @param list
     * @throws CloneNotSupportedException
     */
    public void getSXLimitPrice(List<List<String>> list) throws CloneNotSupportedException {
        if (list != null) {
            List<FeeMode> fees = new ArrayList<FeeMode>();
            // 遍历行
            for (int i = 0; i < 20; i++) {
                if (i > 1) {
                    List<String> cellList = list.get(i);
                    // 遍历列
                    for (int j = 1; j < cellList.size(); j++) {
                        if (cellList.get(j).equals("")) {
                            continue;
                        } else {
                            FeeMode fm = new FeeMode();
                            fm.setAge((int) Double.parseDouble(cellList.get(0)));
                            fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(1).get(j)))).toString());
                            if (j < 7) {
                                fm.setSex(Short.valueOf("1"));
                            } else {
                                fm.setSex(Short.valueOf("0"));
                            }
                            fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
                            fm.setMainFee(Double.parseDouble(cellList.get(j)));
                            fm.setSubFee(Double.parseDouble(cellList.get(j + 1)));

                            // 保额
                            // int amount5 = 5;
                            int amount10 = 10 * 10000;
                            int amount20 = 20 * 10000;
                            int amount30 = 30 * 10000;
                            FeeMode fm10 = (FeeMode) fm.clone();
                            FeeMode fm20 = (FeeMode) fm.clone();
                            FeeMode fm30 = (FeeMode) fm.clone();
                            // 保费
                            // Long amount = Math.round(amount5 * 10000 * fm.getSubFee() / 1000
                            // / (1 - fm.getMainFee() / 1000));
                            // Double price5 = ((Long) Math.round((amount * fm.getMainFee() / 1000 + fm.getSubFee()
                            // * amount5 * 10) * 100)).doubleValue() / 100;
                            Long mount10 = Math.round(amount10 * fm10.getSubFee() / 1000
                                / (1 - fm10.getMainFee() / 1000));
                            Double price10 = ((Long) Math.round((mount10 * fm10.getMainFee() / 1000 + fm10.getSubFee()
                                * amount10 / 1000) * 100)).doubleValue() / 100;
                            Long mount20 = Math.round(amount20 * fm20.getSubFee() / 1000
                                / (1 - fm20.getMainFee() / 1000));
                            Double price20 = ((Long) Math.round((mount20 * fm20.getMainFee() / 1000 + fm20.getSubFee()
                                * amount20 / 1000) * 100)).doubleValue() / 100;
                            Long mount30 = Math.round(amount30 * fm30.getSubFee() / 1000
                                / (1 - fm30.getMainFee() / 1000));
                            Double price30 = ((Long) Math.round((mount30 * fm30.getMainFee() / 1000 + fm30.getSubFee()
                                * amount30 / 1000) * 100)).doubleValue() / 100;
                            // if (fm.getPayPeriod().equals("1")) {
                            // this.findFeePrice(fees, fm, amount5, price5, "500");
                            this.findFeePrice(fees, fm10, amount10, price10, "500");
                            this.findFeePrice(fees, fm20, amount20, price20, "500");
                            this.findFeePrice(fees, fm30, amount30, price30, "500");
                            // } else {
                            // this.findFeePrice(fees, fm, amount5, price5, "200");
                            // this.findFeePrice(fees, fm, amount10, price10, "200");
                            // this.findFeePrice(fees, fm, amount20, price20, "200");
                            // this.findFeePrice(fees, fm, amount30, price30, "200");
                            // }
                            j++;
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(fees)) {
                Collections.sort(fees, new Comparator<FeeMode>() {
                    @Override
                    public int compare(FeeMode o1, FeeMode o2) {
                        Double power1 = o1.getPrice();
                        Double power2 = o2.getPrice();
                        return power1.compareTo(power2);
                    }
                });
            }
            for (FeeMode f : fees) {
                System.out.println("保费：" + f.toString());
            }

            // System.out.println("最低保费：" + fees.get(0).toString());
            // System.out.println("最高保费：" + fees.get(fees.size() - 1).toString());
        }
    }

    /**
     * 解析寿险费率EXCEL表格，获取最低保费条件
     *
     * @param list
     */
    public void getSXLimitPriceCond(List<List<String>> list) {
        if (list != null) {
            System.out.println("    trial_data_tb: [");
            Map<String, FeeMode> fees = new HashMap<>();
            // 遍历行
            for (int i = 0; i < 20; i++) {
                if (i > 1) {
                    List<String> cellList = list.get(i);
                    // 遍历列
                    for (int j = 1; j < cellList.size(); j++) {
                        if (cellList.get(j).equals("")) {
                            continue;
                        } else {
                            FeeMode fm = new FeeMode();
                            fm.setAge((int) Double.parseDouble(cellList.get(0)));
                            fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(1).get(j)))).toString());
                            if (j < 7) {
                                fm.setSex(Short.valueOf("1"));
                            } else {
                                fm.setSex(Short.valueOf("0"));
                            }
                            fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
                            fm.setMainFee(Double.parseDouble(cellList.get(j)));
                            fm.setSubFee(Double.parseDouble(cellList.get(j + 1)));

                            // 打印保费计算额度
                            Long amount = Math.round(10 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
                            Double fee10 = ((Long) Math
                                .round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 10 * 10) * 100))
                                    .doubleValue()
                                / 100;
                            amount = Math.round(20 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
                            Double fee20 = ((Long) Math
                                .round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 20 * 10) * 100))
                                    .doubleValue()
                                / 100;
                            amount = Math.round(30 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
                            Double fee30 = ((Long) Math
                                .round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 30 * 10) * 100))
                                    .doubleValue()
                                / 100;

                            this.findLessFee(fm, fee10, "10", fees, "500");
                            this.findLessFee(fm, fee20, "20", fees, "500");
                            this.findLessFee(fm, fee30, "30", fees, "500");
                            j++;
                        }
                    }
                }
            }
            if (!MapUtils.isEmpty(fees)) {
                Iterator<Map.Entry<String, FeeMode>> errorEntryIter = fees.entrySet().iterator();
                while (errorEntryIter.hasNext()) {
                    Map.Entry<String, FeeMode> errorEntry = errorEntryIter.next();
                    FeeMode feeMode = errorEntry.getValue();
                    if (feeMode.getAge() != 18) {
                        String amount = errorEntry.getKey().split(":")[4];
                        System.out.print("        {\"age\": \"" + feeMode.getAge() + "\",");
                        System.out.print("  \"sex\": \"" + feeMode.getSex() + "\",");
                        System.out.print("  \"PayPeriod\": \"" + feeMode.getPayPeriod() + "\",");
                        System.out.print("  \"insurPeriod\": \"" + feeMode.getInsurPeriod() + "\",");
                        System.out.println("  \"amount\": \"" + amount + "\"},");
                    }

                }
            }
            System.out.println("    ],");
        }
    }

    /**
     * 解析保费
     *
     * @param fees
     * @param fm
     * @param amount
     * @param price
     * @param limit
     */
    private void findFeePrice(List<FeeMode> fees, FeeMode fm, int amount, Double price, String limit) {
        if (price.compareTo(Double.parseDouble(limit)) >= 0) {
            fm.setPrice(price);
            fm.setAmount(amount);
            fees.add(fm);
        }
    }

    /**
     * 解析保费条件
     *
     * @param fm
     * @param fee
     * @param feeValue
     * @param fees
     * @param limit
     */
    private void findLessFee(FeeMode fm, Double fee, String feeValue, Map<String, FeeMode> fees, String limit) {
        if (fm.getPayPeriod().equals("1") && fee.compareTo(Double.parseDouble(limit)) >= 0) {
            FeeMode value = fees.get("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":"
                + fm.getPayPeriod() + ":" + feeValue);
            if (null != value) {
                Integer age = value.getAge();
                if (age.compareTo(fm.getAge()) > 0) {
                    fees.put("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
                        + feeValue, fm);
                }
            } else {
                fees.put("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
                    + feeValue, fm);
            }
        } else if (!fm.getPayPeriod().equals("1") && fee.compareTo(Double.parseDouble(limit)) >= 0) {
            FeeMode value = fees.get("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":"
                + fm.getPayPeriod() + ":" + feeValue);
            if (null != value) {
                Integer age = value.getAge();
                if (age.compareTo(fm.getAge()) > 0) {
                    fees.put("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
                        + feeValue, fm);
                }
            } else {
                fees.put("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
                    + feeValue, fm);
            }
        }
    }
}
