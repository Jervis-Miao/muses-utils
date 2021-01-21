/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.utils.file.write;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

/**
 * @author miaoqiang
 * @date 2020/7/14.
 */
public class TxtFile implements IFileWrite<File> {
    public static final String extensionSeparator = "\\.";
    public static final List<String> allowedExtension = new ArrayList<>(8);
    private static final Random r = new Random();

    static {
        allowedExtension.add("js");
        allowedExtension.add("jsx");
        allowedExtension.add("less");
        allowedExtension.add("java");
    }

    @Override
    public void writeToFile(File outFile, File data) {
        write(outFile, data);
    }

    private final void write(File outFile, File inFile) {
        if (inFile.isDirectory()) {
            final File[] files = inFile.listFiles();
            Arrays.stream(files).forEach(f -> write(outFile, f));
        } else if (inFile.isFile()) {
            if (r.nextBoolean()) {
                return;
            }
            final String[] fileNames;
            if ((fileNames = inFile.getName().split(extensionSeparator)).length == 1
                || !allowedExtension.contains(fileNames[1])) {
                return;
            }
            try {
                FileUtils.writeStringToFile(outFile, FileUtils.readFileToString(inFile), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        File out = new File("C:\\Users\\miaoqiang\\Desktop\\code.txt");
        File in = new File("D:\\workspace\\io-all\\koni\\io-koni-compose\\src\\main\\java\\cn\\xyz");
        new TxtFile().writeToFile(out, in);
    }
}
