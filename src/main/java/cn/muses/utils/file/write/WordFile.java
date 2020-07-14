/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;

/**
 * @author miaoqiang
 * @date 2020/7/14.
 */
public class WordFile implements IFileWrite<InputStream> {

    @Override
    public void writeToFile(File file, InputStream data) {
        FileOutputStream fis = null;
        try {
            HWPFDocument doc = new HWPFDocument(data);
            doc.write(fis = new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != data) {
                try {
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
