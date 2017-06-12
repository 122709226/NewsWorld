package com.example.newsapp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/5/28.
 */

public class DeepCopyList {


    @SuppressWarnings("unchecked")
    public static <T> LinkedList<T> deepCopyList(LinkedList<T> src)
    {
        LinkedList<T> dest = null;
        try
        {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (LinkedList<T>) in.readObject();
        }
        catch (IOException e)
        {

        }
        catch (ClassNotFoundException e)
        {

        }
        return dest;
    }
}
