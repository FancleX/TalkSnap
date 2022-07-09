package com.dev.chatservice.websocket.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BinaryWebSocketHandler extends SimpleChannelInboundHandler<BinaryWebSocketHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketHandler msg) throws Exception {

    }
}
