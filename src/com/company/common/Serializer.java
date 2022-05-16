package com.company.common;

import com.company.client.CommandChecker;

import java.io.*;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Serializer{

    public static final int SIZE = 32 * 1024;
    public static final int PORT = 3131;

    public ByteArrayOutputStream serialize(Request request) throws IOException {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(SIZE);
        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
        objectOutput.writeObject(request);
        objectOutput.close();
        return byteOutput;
    }

    public Object deserialize(byte[] requestArray) {
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(requestArray, 0, requestArray.length);
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            return objectInput.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.out.println("Произошла ошибка, в переданных данных оказался объект неизвестного класса.");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

}
