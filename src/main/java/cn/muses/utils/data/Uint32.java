/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.data;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jervis
 * @date 2021/12/1.
 */
public class Uint32 {

    public static final int SIZE = 32;

    private static final long MIN = 0L;

    private static final long MAX = ((long)1 << SIZE) - 1;

    public static final Uint32 MIN_VALUE = new Uint32(MIN);

    public static final Uint32 MAX_VALUE = new Uint32(MAX);

    private long value;

    public Uint32() {
        this(0L);
    }

    public Uint32(long value) {
        setValue(value);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        check(value);
        this.value = value & MAX;
    }

    private void check(long value) {
        if (value < MIN || value > MAX) {
            throw new IllegalArgumentException(value + " 值必须在 " + MIN + " 到 " + MAX + " 范围内");
        }
    }

    @Override
    public int hashCode() {
        return ((Long)value).hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int compareTo(Uint32 obj) {
        if (obj == null) {
            return -1;
        }
        if (this.value == obj.value) {
            return 0;
        }

        return this.value > obj.value ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.value != ((Uint32)obj).value) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Uint32> x = new ArrayList<>(1000);
        for (int i = 0;i<1000;i++){
            x.add(new Uint32(i));
        }

        System.out.println(ClassLayout.parseInstance(x).toPrintable());
    }
}
