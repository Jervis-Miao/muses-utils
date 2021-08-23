/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.read.excel.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.muses.utils.file.read.excel.Cell;

/**
 * @author miaoqiang
 * @date 2020/7/2.
 */
public class CashValueDTO implements Serializable {
    private static final long serialVersionUID = 1045494058954426864L;

    /**
     * 被保险人性别（M or F）
     */
    @Cell(cellnum = 0)
    private Integer sex;

    /**
     * 投保时的年龄
     */
    @Cell(cellnum = 2)
    private Integer age;

    /**
     * 交费年期 注：3代表三年期交
     */
    @Cell(cellnum = 1)
    private Integer np;

    /**
     * 保险期间 注：88@ 表示保至88周岁
     */
    @Cell(cellnum = 6)
    private String bp;

    /**
     * 保单年度(首个保单年度为1)
     */
    @Cell(cellnum = 4)
    private Integer poly;

    /**
     * 各保单年度末1000元保险费对应的现金价值
     */
    @Cell(cellnum = 5)
    private BigDecimal cv;

    /**
     * 领取年龄
     */
    @Cell(cellnum = 3)
    private Integer ca;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNp() {
        return np;
    }

    public void setNp(Integer np) {
        this.np = np;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public Integer getPoly() {
        return poly;
    }

    public void setPoly(Integer poly) {
        this.poly = poly;
    }

    public BigDecimal getCv() {
        return cv;
    }

    public void setCv(BigDecimal cv) {
        this.cv = cv;
    }

    public Integer getCa() {
        return ca;
    }

    public void setCa(Integer ca) {
        this.ca = ca;
    }

    @Override
    public String toString() {
        return new StringBuilder("[")
            .append(String.format("\"\"%s\"\", ", poly))
            .append(String.format("\"\"%s\"\"", cv))
            .append("]")
            .toString();
    }
}
