package com.dev.chatservice.websocket.service;

import com.dev.chat.ChatEntity;
import com.dev.chat.MQObject;
import com.dev.chatservice.websocket.repository.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    @Autowired
    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    /**
     * invoke on registry
     *
     * @param userId
     * @param username
     */
    public void createEntity(Long userId, String username) {
        // determine if the user already get the chat entity from addHistory
        Optional<ChatEntity> id = chatHistoryRepository.findById(userId);
        if (id.isPresent()) {
            // update the username
            ChatEntity chatEntity = id.get();
            chatEntity.setUsername(username);
            chatHistoryRepository.save(chatEntity);
        } else {
            // if a new user
            chatHistoryRepository.save(new ChatEntity(userId, username));
        }
    }


    /**
     * add a chat history to both sender and receiver
     *
     * @param object
     */
    public void addHistory(MQObject object) {
        // add the message to both sender and receiver
        Long from = object.getFromId();
        Long to = object.getTo();

        // update the sender history
        ChatEntity fromEntity = chatHistoryRepository.findById(from).get();
        // get history
        HashMap<Long, PriorityQueue<MQObject>> map = fromEntity.getHistory();
        // if the receiver is in history then add the object to that history, otherwise create a new list to add
        PriorityQueue<MQObject> fromQueue = map.containsKey(to) ? map.get(to) : new PriorityQueue<>(Comparator.comparing(MQObject::getTime));
        fromQueue.add(object);
        // save the update
        chatHistoryRepository.save(fromEntity);

        // update the receiver history
        Optional<ChatEntity> toId = chatHistoryRepository.findById(to);
        // if the receiver doesn't have an entity yet
        ChatEntity entity = toId.isEmpty() ? new ChatEntity(to, null) : toId.get();
        HashMap<Long, PriorityQueue<MQObject>> history = entity.getHistory();
        if (history == null) {
            history = new HashMap<>();
        }
        PriorityQueue<MQObject> toQueue = map.containsKey(to) ? map.get(to) : new PriorityQueue<>(Comparator.comparing(MQObject::getTime));
        toQueue.add(object);
        history.put(from, toQueue);
        chatHistoryRepository.save(entity);
    }

    /**
     * return a chat history classified by different chat individual with time ascending order
     * and count unread messages
     *
     * @param userId the user id
     * @return all history will be like {contactId: [chatEntities of the id, the number of unread of the chat entities]}
     */
    public HashMap<Long, List<Object>> getHistoryById(Long userId) {
        Optional<ChatEntity> chatEntity = chatHistoryRepository.findById(userId);
        if (chatEntity.isEmpty()) {
            return new HashMap<>();
        }
        HashMap<Long, List<Object>> res = new HashMap<>();
        // count unread
        HashMap<Long, PriorityQueue<MQObject>> history = chatEntity.get().getHistory();
        history.forEach((key, value) -> {
            int counts = 0;
            List<Object> objects = new ArrayList<>();
            // if the value is not null then count
            if (!value.isEmpty()) {
                counts = value.stream().mapToInt(o -> o.isRead() ? 0 : 1).reduce(Integer::sum).getAsInt();
            }
            objects.add(value);
            objects.add(counts);
            res.put(key, objects);
        });
        return res;
    }

    public void markRead(MQObject object) {
       List<MQObject> content = (List<MQObject>) object.getContent();
        Long fromId = object.getFromId();
        // query those read message and change status in db
        ChatEntity entity = chatHistoryRepository.findById(fromId).get();
        HashMap<Long, PriorityQueue<MQObject>> history = entity.getHistory();
        PriorityQueue<MQObject> objects = history.get(object.getTo());
        content.forEach(c -> {
            objects.stream().filter(c::equals).forEachOrdered(o -> o.setRead(true));
        });
        // save
        chatHistoryRepository.save(entity);
    }

    /**
     * determine if the user has account record in database
     *
     * @param userId
     * @return
     */
    public boolean isRegistered(Long userId) {
        Optional<ChatEntity> id = chatHistoryRepository.findById(userId);
        if (id.isPresent()) {
            ChatEntity entity = id.get();
            return entity.getUsername() != null;
        }
        return false;
    }



    /**
     * return a specific history and classified by the target id
     *
     * @param userId the user id
     * @param targetId the target that the user wants to query
     * @return the history that happened associated with the target id
     */
    public PriorityQueue<MQObject> getHistoryById(Long userId, Long targetId) {
        Optional<ChatEntity> chatEntity = chatHistoryRepository.findById(userId);
        return chatEntity.isPresent() ? chatEntity.get().getHistory().get(targetId) : new PriorityQueue<>();
    }

}
