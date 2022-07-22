package com.dev.chatservice.websocket.channel;

import com.dev.chatservice.websocket.handlers.BinaryWebSocketHandler;
import com.dev.chatservice.websocket.handlers.TextWebSocketHandler;
import com.dev.chatservice.websocket.service.ChatHistoryService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // http decoder
        pipeline.addLast(new HttpServerCodec());
        // process with chunk in writing
        pipeline.addLast(new ChunkedWriteHandler());
        // the maximum length of the aggregated content in bytes
        // full http request
        pipeline.addLast(new HttpObjectAggregator(1024*10));
        // compression extensions
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // protocol
        pipeline.addLast((new WebSocketServerProtocolHandler("/chat", null, true, 10485760)));
        // text handler
        pipeline.addLast(new TextWebSocketHandler());
        // binary handler
        pipeline.addLast(new BinaryWebSocketHandler());
        // set timeout
        pipeline.addLast(new ReadTimeoutHandler(10, TimeUnit.MINUTES));
    }
}
