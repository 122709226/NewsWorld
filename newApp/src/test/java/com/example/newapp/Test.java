package com.example.newapp;

import android.bluetooth.BluetoothProfile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Test {
    @org.junit.Test
    public void test001() {
        String ss = "\"sdfsdf\\\"";
        String[] sd = ss.split("\"");

        System.out.println(ss + "  " + sd[1].replace('\\', ' ') + " fgf");

    }

    @org.junit.Test
    public void test002() {
        File file = new File("C:/Users/Administrator/Desktop/ews_log.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bf = new BufferedOutputStream(fileOutputStream);

            bf.write("sdfsdfsdfsdf".getBytes());
            bf.flush();
            bf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
