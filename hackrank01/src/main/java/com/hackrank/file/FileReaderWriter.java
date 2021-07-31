package com.hackrank.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderWriter {
    public static void main(String[] args) throws IOException {
//        readFile();
        readFile2();
    }


    public static void readFile2() throws IOException {

        File file = new File("hello.txt");
//        System.out.println(file.getAbsolutePath());

        try (FileReader fileReader = new FileReader(file)) {
            char[] cbuf = new char[5];
            int len = fileReader.read(cbuf);
            while (len != -1) {
                for (int i = 0; i < len; i++) {
                    System.out.print(cbuf[i]);
                }
                len = fileReader.read(cbuf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //从硬盘读取文件到内存
    public static void readFile() throws IOException {

        File file = new File("hello.txt");
//        System.out.println(file.getAbsolutePath());

        try (FileReader fileReader = new FileReader(file)) {
            int data = fileReader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fileReader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
