package com.dev.chatservice.websocket.handlers;

import com.dev.auth.Auth;
import com.dev.chat.MQObject;
import com.dev.chat.Message;
import com.dev.chat.MessageType;
import com.dev.chat.WebSocketChannel;
import com.dev.chatservice.websocket.channel.WebSocketChannelPool;
import com.dev.chatservice.websocket.handlers.subHandlers.GeneralHandler;
import com.dev.chatservice.websocket.handlers.subHandlers.HeartBeatHandler;
import com.dev.chatservice.websocket.handlers.subHandlers.TextHandler;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;

@Slf4j
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // Json parser
    private final Gson JSON = new Gson();
    private final GeneralHandler textHandler = new TextHandler();
    private final HeartBeatHandler heartBeatHandler = new HeartBeatHandler();



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // get text
        String text = msg.text();
        // parse object, possibly throw JsonSyntaxException
        Message message = JSON.fromJson(text, Message.class);
        // verify if the token is valid
        Map<String, Object> auth = Auth.verify(message.getToken());
        // get sender info
        if (auth == null) {
            // break the connection
            ctx.channel().close();
            return;
        }
        Long userId = (Long) auth.get("userId");
        String username = (String) auth.get("nickname");
        // handle the message by its type
        MessageType type = message.getType();
        MQObject object = new MQObject(username, message.getTo(), message.getContent(), message.getTime(), message.getType());
        switch (type) {
            case LOGIN -> {
                // added to the pool
                // create a channel for the connection
                WebSocketChannel channel = new WebSocketChannel(message.getUuid(), ctx.channel());
                WebSocketChannelPool.bind(userId, channel);
            }
            case TEXT -> {
                // call text handler
                textHandler.handle(object);
            }
            case HEART_BEAT -> {
                // call heart beat handler
            }
            case MEDIA -> {
                // turn to binary handler
                ctx.fireChannelRead(msg);
            }
            default -> {
                // malformed request
                // throw exception
                throw new MalformedJsonException("Malformed request");
            }

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // remove channel
        WebSocketChannelPool.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
