/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.write;

import java.io.File;

/**
 * @author miaoqiang
 * @date 2020/7/14.
 */
public interface IFileWrite<T> {

    /**
     * Writes data to a file creating the file if it does not exist.
     *
     * @param outFile
     * @param data
     * @param <T>
     */
    public void writeToFile(File outFile, T data);
}
