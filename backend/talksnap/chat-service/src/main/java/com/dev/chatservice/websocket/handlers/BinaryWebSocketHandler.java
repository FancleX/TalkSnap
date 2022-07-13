package com.dev.chatservice.websocket.handlers;

import com.dev.auth.Auth;
import com.dev.chat.MQObject;
import com.dev.chat.Message;
import com.dev.chat.MessageType;
import com.dev.chatservice.websocket.handlers.subHandlers.GeneralHandler;
import com.dev.chatservice.websocket.handlers.subHandlers.GeneralHandlerImp;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class BinaryWebSocketHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private final GeneralHandler binaryHandler = new GeneralHandlerImp();
    private final Gson JSON = new Gson();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        // get buffer
        ByteBuf byteBuf = msg.content();
        Message message = JSON.fromJson(byteBuf.toString(StandardCharsets.UTF_8), Message.class);
        String fileExtension = message.getFileExtension();
        String content = (String) message.getContent();
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        Map<String, Object> payload = Auth.verify(message.getToken());
        assert payload != null;
        Long userId = (Long) payload.get("userId");
        String username = (String) payload.get("nickname");
        // write file to local cache
        String path = System.getProperty("user.dir") + "/backend/talksnap/chat-service/cache";
        String fileName = username + userId + UUID.randomUUID() + "." + fileExtension;
        String filePath = path + "/" + fileName;
        // switch to file server instead
        File file = new File(filePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(bytes);
            MQObject object = new MQObject(message.getUuid(), userId, username, message.getTo(), filePath, message.getTime(), MessageType.MEDIA);
            binaryHandler.handle(object);
        } catch (IOException e) {
            log.error("File write failed of the incoming message: " + message);
            ctx.channel().writeAndFlush(new MQObject(message.getUuid(), -1L, "Server", userId, "failed to send the file", message.getTime(), MessageType.MEDIA));
        }


    }
}
