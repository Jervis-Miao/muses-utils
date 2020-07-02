package cn.muses.utils.excel.dto;

public class FeeMode implements Cloneable {

    // 年龄
    private int age;
    // 性别
    private Short sex;
    // 缴费期间
    private String payPeriod;
    // 保险期间
    private String insurPeriod;
    // 保费费率
    private double fee;
    // 主险费率
    private double mainFee;
    // 附加险费率
    private double subFee;
    // 保额
    private int amount;
    // 保单价格
    private Double price;

    public FeeMode() {}

    public FeeMode(int age, Short sex, String payPeriod, String insurPeriod, double fee, double mainFee, double subFee,
        int amount, Double price) {
        this.age = age;
        this.sex = sex;
        this.payPeriod = payPeriod;
        this.insurPeriod = insurPeriod;
        this.fee = fee;
        this.mainFee = mainFee;
        this.subFee = subFee;
        this.amount = amount;
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getInsurPeriod() {
        return insurPeriod;
    }

    public void setInsurPeriod(String insurPeriod) {
        this.insurPeriod = insurPeriod;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getMainFee() {
        return mainFee;
    }

    public void setMainFee(double mainFee) {
        this.mainFee = mainFee;
    }

    public double getSubFee() {
        return subFee;
    }

    public void setSubFee(double subFee) {
        this.subFee = subFee;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new FeeMode(this.age, this.sex, this.payPeriod, this.insurPeriod, this.fee, this.mainFee, this.subFee,
            this.amount, this.price);
    }
}
