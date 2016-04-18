package com.lxz.proto;

import com.google.common.base.Stopwatch;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by xiaolezheng on 16/4/18.
 */
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    private static final int port = 65111;
    private static final String hostName = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        final NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(hostName, port));
        // client code - attach to the server and send a message
        Mail proxy = SpecificRequestor.getClient(Mail.class, client);
        logger.info("Client built, got proxy");

        Stopwatch stopwatch = Stopwatch.createStarted();

        for(int i=1; i<10000000;i++) {
            // fill in the Message record and send it
            Message message = new Message();
            message.setTo("jim@qq.com");
            message.setFrom(i+"@qq.com");
            message.setBody("hello world");
            message.setStatus(MessageStatus.NEW);

            logger.info("Calling proxy.send with message:  " + message.toString());

            Message result = proxy.send(message);

            logger.info("result: {}", result);

            Message responseMsg = proxy.getMessage("1001"+i);
            logger.info("responseMsg: {}", responseMsg);
        }

        stopwatch.stop();

        logger.info("watch: {}", stopwatch.toString());

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                client.close();
            }
        }));
    }
}
