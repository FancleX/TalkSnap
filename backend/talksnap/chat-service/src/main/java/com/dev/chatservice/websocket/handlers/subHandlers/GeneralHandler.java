package com.dev.chatservice.websocket.handlers.subHandlers;

import com.dev.chat.MQObject;

/**
 * Sub handler uses for dealing with different type of request object
 * takes care of delivering message to the receiver
 */
public interface GeneralHandler {

    /**
     * deliver message object in local channel pool
     *
     * @param object object to be delivered
     * @return true if the successfully deliver
     */
    boolean deliver(MQObject object);

    /**
     * another method for delivery failed
     *
     * @param object object to be committed
     */
    void commit(MQObject object);

    void handle(MQObject object);
}
