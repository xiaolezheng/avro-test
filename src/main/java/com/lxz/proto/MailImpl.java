package com.lxz.proto;

import org.apache.avro.AvroRemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaolezheng on 16/4/18.
 */
public class MailImpl implements Mail {
    private static final Logger logger = LoggerFactory.getLogger(MailImpl.class);

    public Message send(Message message) {
        logger.info("Sending message, msgFrom: {}", message.getFrom());

        message.setStatus(MessageStatus.SUCCESS);

        return message;
    }

    @Override
    public Message getMessage(CharSequence msgId) throws AvroRemoteException {
        return Message.newBuilder().setTo("system@qq.com").setFrom(msgId).setBody("welcome")
                .setStatus(MessageStatus.SUCCESS).build();
    }
}
