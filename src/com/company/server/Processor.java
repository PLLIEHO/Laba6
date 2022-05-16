package com.company.server;

import com.company.common.Serializer;
import com.company.server.core.Collection;
import com.company.server.core.Parser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;


public class Processor {
    public static SocketAddress address;

    public void begin(DatagramChannel channel) {
        try {
                ByteBuffer clientRequest = ByteBuffer.allocate(Serializer.SIZE);
                Selector selector = Selector.open();
                SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
                while(true) {
                    int readyChannels = selector.selectNow();
                    if (readyChannels == 0) continue;
                    else{break;}
                }
                if (key.isReadable()) {
                address = channel.receive(clientRequest);
                System.out.println("Сообщение получено.");
                Serializer serializer = new Serializer();
                System.out.println(clientRequest.toString());
                clientRequest.flip();
                    Collection collection = new Collection();
                    Parser parser = new Parser(collection);
                    parser.start("src/XML.xml");
                    System.out.println(collection.getCollection().toString());
                CommandCore core = new CommandCore(serializer.deserialize(clientRequest.array()), collection, channel);
                core.commandSearch();
                }


        } catch(IOException e){}
    }
}
