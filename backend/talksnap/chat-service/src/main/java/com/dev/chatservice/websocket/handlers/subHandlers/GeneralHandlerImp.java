package com.dev.chatservice.websocket.handlers.subHandlers;

import com.dev.chat.MQObject;
import com.dev.chat.WebSocketChannel;
import com.dev.chatservice.websocket.channel.WebSocketChannelPool;
import com.dev.chatservice.websocket.messageQueue.RabbitMQ;

import java.util.Set;

public class GeneralHandlerImp implements GeneralHandler {

    @Override
    public boolean deliver(MQObject object) {
        // get receiver
        Long to = object.getTo();
        // if the receiver doesn't exist locally
        if (!WebSocketChannelPool.isContain(to, object.getUuid())) {
            return false;
        }
        // deliver the object
        Set<WebSocketChannel> channels = WebSocketChannelPool.getChannels(to);
        // send to all available channels
        channels.forEach(c -> {
            c.getChannel().writeAndFlush(object);
        });
        return true;
    }

    @Override
    public void commit(MQObject object) {
        // commit to message queue
        RabbitMQ.sendMessage(object);
    }

    @Override
    public void handle(MQObject object) {
        // try delivery
        if (!deliver(object)) {
            // otherwise commit
            commit(object);
        }
    }


}
