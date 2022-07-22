package com.dev.chatservice.websocket.controller;

import com.dev.chatservice.websocket.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Asynchronously process service task
 */
public class ChatHistoryController {

    @Autowired
    private ChatHistoryService service;

    private final Executor executor = Executors.newCachedThreadPool();


    public void registry(Long userId, String username) {
        executor.execute(() -> {
            service.createEntity(userId, username);
        });
    }

    public void appendHistory() {

    }

    public void fetchHistory() {

    }

    public void markRead() {

    }

}
