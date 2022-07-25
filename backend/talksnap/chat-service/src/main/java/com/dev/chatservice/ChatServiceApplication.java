package com.dev.chatservice;

import com.dev.chatservice.websocket.nettyServer.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.dev.chatservice.websocket.repository")
@EntityScan(basePackages = "com.dev.chat")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.dev.chatservice.websocket", exclude = DataSourceAutoConfiguration.class)
public class ChatServiceApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new NettyServer().run(port);
    }
}
