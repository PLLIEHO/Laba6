package com.company.client;

import com.company.common.Serializer;

import java.io.IOException;
import java.net.*;

public class Client {
    private final DatagramSocket socket = new DatagramSocket();
    private final InetAddress address = InetAddress.getByName("localhost");

    public Client() throws UnknownHostException, SocketException {
    }

    public static void main(String[] args) throws IOException {
        try{
            Client client = new Client();
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public void start() throws IOException {
        CommandChecker checker = new CommandChecker(address, socket);
        checker.check();
    }
}
