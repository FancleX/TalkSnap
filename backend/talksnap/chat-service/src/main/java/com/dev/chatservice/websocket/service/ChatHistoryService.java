package com.dev.chatservice.websocket.service;

import com.dev.chat.ChatEntity;
import com.dev.chat.MQObject;
import com.dev.chatservice.websocket.repository.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    @Autowired
    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public List<MQObject> getHistoryById(Long userId) {
        Optional<ChatEntity> chatEntity = chatHistoryRepository.findById(userId);
        if (chatEntity.isPresent()) {
            return chatEntity.get().getHistory();
        }
        return new ArrayList<>();
    }

}
