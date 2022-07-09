package com.dev.chatservice.websocket.nettyServer;

import com.dev.chatservice.websocket.channel.HttpChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyServer {

    public void run(int port) {
        // create parent and child threads
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent, child)
                    .channel(NioServerSocketChannel.class)
                    // the maximum queue length for incoming connection
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new HttpChannelInitializer());
            // synchronously bind port to the server
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("Websocket server started at port: [{}]", port);
            // listening channel shutdown
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            // on exit, release system resource
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }

    }

}
