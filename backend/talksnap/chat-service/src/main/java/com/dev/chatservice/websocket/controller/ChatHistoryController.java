package com.dev.chatservice.websocket.controller;

import com.dev.chat.MQObject;
import com.dev.chatservice.websocket.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Asynchronously process some service tasks
 */
public class ChatHistoryController {

    @Autowired
    private ChatHistoryService service;

    private final Executor executor = Executors.newCachedThreadPool();


    public void registry(Long userId, String username) {
        executor.execute(() -> service.createEntity(userId, username));
    }

    public void appendHistory(MQObject object) {
        executor.execute(() -> service.addHistory(object));
    }

    public HashMap<Long, List<Object>> fetchHistory(Long userId) {
        return service.getHistoryById(userId);
    }

    public void markRead(MQObject object) {
        executor.execute(() -> service.markRead(object));
    }

    public boolean isRegistered(Long userId) {
        return service.isRegistered(userId);
    }

}
