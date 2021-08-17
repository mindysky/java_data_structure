package com.hackrank.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileInputOutputStream {
    @Test
    public void testFileInputStream() throws IOException {
        File file = new File("src/main/java/com/hackrank/file/hello.txt");
        try(FileInputStream fis = new FileInputStream(file);) {

            byte[] buffer = new byte[5];
            int len;
            while ((len = fis.read(buffer))!=-1){
                String str = new String(buffer,0,len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
