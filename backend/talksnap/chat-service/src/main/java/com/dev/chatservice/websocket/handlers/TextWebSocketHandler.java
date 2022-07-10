package com.dev.chatservice.websocket.handlers;

import com.dev.auth.Auth;
import com.dev.chat.Message;
import com.dev.chatservice.websocket.messageQueue.RabbitMQ;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

@Slf4j
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // Json parser
    private final Gson JSON = new Gson();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // get text
        String text = msg.text();
        // parse object, possibly throw JsonSyntaxException
        Message message = JSON.fromJson(text, Message.class);
        // verify if the token is valid
        Map<String, Object> auth = Auth.verify(message.getToken());
        // get sender info
        if (auth != null) {
            // remove from channel pools
            // break the connection
        }
        // handle the message by its type

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("A client connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("A client disconnected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
