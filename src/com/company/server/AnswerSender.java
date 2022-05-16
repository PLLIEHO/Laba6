package com.company.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class AnswerSender {
    public void send(DatagramChannel channel, String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        channel.send(buffer, Processor.address);
    }
}
