package com.myweb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteClass {
    public static void main(String[] args) {
        FileOutputStream fos = null;
        try {
            //覆盖原内容
//            fos = new FileOutputStream("test");
            //在原内容基础上追加
            fos = new FileOutputStream("test",true);
            String s = "\n hello world";
            fos.write(s.getBytes());
            s = "耶耶耶";
            fos.write(s.getBytes());
            //写完之后，最后一定要刷新。
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
