package com.company.client;

import com.company.common.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Sender {

    public void send(byte[] byteOutput, InetAddress address, DatagramSocket socket) {

        DatagramPacket toServer = new DatagramPacket(byteOutput, byteOutput.length, address, Serializer.PORT);
        DatagramPacket packetIn = new DatagramPacket(new byte[Serializer.SIZE], Serializer.SIZE);
        try {
                //пробуем достучаться до сервера 10 раз, отправляя пакеты каждую секунду
                System.out.println("Пакет отправлен.");
                    socket.send(toServer);
                    socket.receive(packetIn);
                    System.out.println("Пакет доставлен.");
                    System.out.println(packetIn.toString());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
