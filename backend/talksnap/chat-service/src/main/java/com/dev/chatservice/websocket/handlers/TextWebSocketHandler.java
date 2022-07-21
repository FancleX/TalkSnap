package com.dev.chatservice.websocket.handlers;

import com.dev.auth.Auth;
import com.dev.chat.MQObject;
import com.dev.chat.Message;
import com.dev.chat.MessageType;
import com.dev.chat.WebSocketChannel;
import com.dev.chatservice.websocket.channel.WebSocketChannelPool;
import com.dev.chatservice.websocket.handlers.subHandlers.GeneralHandler;
import com.dev.chatservice.websocket.handlers.subHandlers.GeneralHandlerImp;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;
import java.util.Map;

@Slf4j
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // Json parser
    private final Gson JSON = new Gson();
    private final GeneralHandler textHandler = new GeneralHandlerImp();

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
        MQObject object = new MQObject(message.getUuid(), userId, username, message.getTo(), message.getContent(), message.getTime(), message.getType(), false);
        switch (type) {
            case LOGIN -> {
                // added to the pool
                // create a channel for the connection
                WebSocketChannel channel = new WebSocketChannel(message.getUuid(), ctx.channel());
                WebSocketChannelPool.bind(userId, channel);
                // db registry



            }
            case TEXT ->
                // call text handler
                textHandler.handle(object);
            case HEART_BEAT -> {
                MQObject res = new MQObject(message.getUuid(), -1L,"Server", object.getFromId(), "Pong", new Date(System.currentTimeMillis()), MessageType.HEART_BEAT, false);
                ctx.channel().writeAndFlush(res);
                // check if the channel exist or not
                if (!WebSocketChannelPool.isContain(userId, message.getUuid())) {
                    // the client lost connection due to the server crash and reconnected now
                    WebSocketChannelPool.bind(userId, new WebSocketChannel(message.getUuid(), ctx.channel()));
                }
            }
            case MEDIA ->
                // turn to binary handler
                ctx.fireChannelRead(msg);
            // fetch history
            case FETCH -> {
                // get history



            }
            // trigger when the user read the message, change the state
            case ACK_READ -> {

            }
            default ->
                // malformed request
                // throw exception
                throw new MalformedJsonException("Malformed request");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // remove channel
        WebSocketChannelPool.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        // close the channel connection
        Channel channel = ctx.channel();
        // remove from the channel pool
        WebSocketChannelPool.remove(channel);
        channel.close();
    }
}
