package com.hackrank.file;

import org.junit.Test;

import java.io.*;

public class FileReaderWriterDemo {
    @Test
    public void testFileReaderFileWriter() throws IOException {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            File srcFile = new File("src/main/java/com/hackrank/file/hello.txt");
            File destFile = new File("src/main/java/com/hackrank/file/hello3.txt");

            fr = new FileReader(srcFile);
            fw = new FileWriter(destFile);


            //读入
            char[] cbuf = new char[5];
            int len; //记录每次读入到cbuf数组中的字符的个数
            while ((len = fr.read(cbuf)) != -1) {
                //每次写出len个字符
                fw.write(cbuf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }
}
