package com.dev.chatservice.websocket.repository;

import com.dev.chat.ChatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatEntity, Long> {

}
