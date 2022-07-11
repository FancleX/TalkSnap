package com.dev.chatservice.websocket.messageQueue;


import com.dev.chat.MQObject;
import com.dev.chat.WebSocketChannel;
import com.dev.chatservice.websocket.channel.WebSocketChannelPool;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class RabbitMQ {

    private static final RabbitTemplate rabbitTemplate = new RabbitTemplate();


    public static void sendMessage(MQObject message) {
        rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, "ws-exchange", message);
    }

    public void receiveMessage(MQObject message) {
        // get the receiver id
        Long id = message.getTo();
        // query the receiver locally
        if (WebSocketChannelPool.isContain(id)) {
            // write the message to all channels of the id
            Set<WebSocketChannel> channels = WebSocketChannelPool.getChannels(id);
            channels.forEach(c -> {
                c.getChannel().writeAndFlush(message);
            });
        }
    }
}
