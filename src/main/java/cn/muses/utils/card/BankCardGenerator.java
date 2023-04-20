/**
 * Copyright 2022 XYZ Co., Ltd. All Rights Reserved
 */
package cn.muses.utils.card;

/**
 * @author jervis
 * @date 2023/4/20.
 */

import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * 随机生成仿信用卡的会员号
 * 
 * @author jervis
 * @date 2023/6/17.
 */
public class BankCardGenerator {

    public static final String[] VISA_PREFIX_LIST = new String[] {"4539",
        "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4"};

    public static final String[] MASTERCARD_PREFIX_LIST = new String[] {"51",
        "52", "53", "54", "55"};

    public static final String[] AMEX_PREFIX_LIST = new String[] {"34", "37"};

    public static final String[] DISCOVER_PREFIX_LIST = new String[] {"6011"};

    public static final String[] DINERS_PREFIX_LIST = new String[] {"300",
        "301", "302", "303", "36", "38"};

    public static final String[] ENROUTE_PREFIX_LIST = new String[] {"2014",
        "2149"};

    public static final String[] JCB_PREFIX_LIST = new String[] {"35"};

    public static final String[] VOYAGER_PREFIX_LIST = new String[] {"8699"};

    static String strrev(String str) {
        if (str == null) {
            return "";
        }
        String revstr = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            revstr += str.charAt(i);
        }

        return revstr;
    }

    /**
     * 生成卡号
     * 
     * @param prefix 前缀
     * @param length 卡号长度 13或者16位
     */
    static String completed_number(String prefix, int length) {

        String ccnumber = prefix;
        while (ccnumber.length() < (length - 1)) {
            ccnumber += new Double(Math.floor(Math.random() * 10)).intValue();
        }
        String reversedCCnumberString = strrev(ccnumber);
        List<Integer> reversedCCnumberList = new Vector<Integer>();
        for (int i = 0; i < reversedCCnumberString.length(); i++) {
            reversedCCnumberList.add(new Integer(String.valueOf(reversedCCnumberString.charAt(i))));
        }

        int sum = 0;
        int pos = 0;

        Integer[] reversedCCnumber = reversedCCnumberList
            .toArray(new Integer[reversedCCnumberList.size()]);
        while (pos < length - 1) {
            int odd = reversedCCnumber[pos] * 2;
            if (odd > 9) {
                odd -= 9;
            }
            sum += odd;
            if (pos != (length - 2)) {
                sum += reversedCCnumber[pos + 1];
            }
            pos += 2;
        }

        int checkdigit = new Double(
            ((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue();
        ccnumber += checkdigit;

        return ccnumber;
    }

    /**
     * 信用卡号码
     * 
     * @param prefixList 前缀的数组
     * @param length 长度
     * @param howMany 数量
     * @return
     */
    public static String[] credit_card_number(String[] prefixList, int length, int howMany) {
        Stack<String> result = new Stack<String>();
        for (int i = 0; i < howMany; i++) {
            int randomArrayIndex = (int)Math.floor(Math.random() * prefixList.length);
            String ccnumber = prefixList[randomArrayIndex];
            result.push(completed_number(ccnumber, length));
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * 生成一个卡号的数组
     * 
     * @param howMany 卡号的数量
     * @return String[] 数组
     */
    public static String[] generateMasterCardNumbers(int howMany) {
        return credit_card_number(MASTERCARD_PREFIX_LIST, 16, howMany);
    }

    public static String generateMasterCardNumber() {
        return credit_card_number(MASTERCARD_PREFIX_LIST, 16, 1)[0];
    }

    public static boolean isValidCreditCardNumber(String creditCardNumber) {
        boolean isValid = false;

        try {
            String reversedNumber = new StringBuffer(creditCardNumber).reverse().toString();
            int mod10Count = 0;
            for (int i = 0; i < reversedNumber.length(); i++) {
                int augend = Integer.parseInt(String.valueOf(reversedNumber
                    .charAt(i)));
                if (((i + 1) % 2) == 0) {
                    String productString = String.valueOf(augend * 2);
                    augend = 0;
                    for (int j = 0; j < productString.length(); j++) {
                        augend += Integer.parseInt(String.valueOf(productString.charAt(j)));
                    }
                }

                mod10Count += augend;
            }

            if ((mod10Count % 10) == 0) {
                isValid = true;
            }
        } catch (NumberFormatException e) {
        }

        return isValid;
    }

    public static void main(String[] args) {
        int howMany = 500;
        String[] creditcardnumbers = generateMasterCardNumbers(howMany);
        for (int i = 0; i < creditcardnumbers.length; i++) {
            System.out.println(creditcardnumbers[i]);
        }
    }
}
