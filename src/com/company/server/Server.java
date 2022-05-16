package com.company.server;

import com.company.common.Serializer;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class Server {

    public static void main(String[] args) throws IOException {

            DatagramChannelBuilder builder = new DatagramChannelBuilder();
            InetSocketAddress address = new InetSocketAddress(Serializer.PORT);
            DatagramChannel channel = builder.build();
            channel.bind(address);
            channel.configureBlocking(false);
            Server server = new Server();
            System.out.println("Сервер запущен и ждет указаний.");
            Processor processor = new Processor();
            processor.begin(channel);


    }
}

