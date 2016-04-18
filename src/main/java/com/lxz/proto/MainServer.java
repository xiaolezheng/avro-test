package com.lxz.proto;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start a server, attach a client, and send a message.
 */
public class MainServer {
    private static final Logger logger = LoggerFactory.getLogger(MainServer.class);
    private static final int port = 65111;
    private static final String hostName = "127.0.0.1";

    private static Server server;

    private static void startServer() throws IOException {
        server = new NettyServer(new SpecificResponder(Mail.class, new MailImpl()),
                new InetSocketAddress(hostName, port));
        // the server implements the Mail protocol (MailImpl)
    }

    public static void main(String[] args) throws IOException {
        logger.info("Starting server");
        // usually this would be another app, but for simplicity
        startServer();

        logger.info("Server started");

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.close();
            }
        }));

    }
}
