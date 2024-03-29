package com.dev.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.PriorityQueue;

@Document(collation = "chat_history")
@Data
@NoArgsConstructor
public class ChatEntity {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private Long userId;

    @Field("nickname")
    private String username;

    @DBRef
    private HashMap<Long, PriorityQueue<MQObject>> history;

    public ChatEntity(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
