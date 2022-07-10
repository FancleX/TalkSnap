package com.dev.chatservice.websocket.channel;

import com.dev.chat.WebSocketChannel;
import com.dev.exception.DuplicatedChannelException;

import java.nio.channels.Channel;
import java.util.*;
import java.util.stream.Collectors;

public class WebSocketChannelPool {

    private static final Map<Long, Set<WebSocketChannel>> pool = new HashMap<>();


    /**
     * bind a websocket channel with the user
     *
     * @param userId the userId
     * @param channel the user websocket channel
     */
    public static void bind(Long userId, WebSocketChannel channel) {
        // if pool doesn't have the id
        if (!pool.containsKey(userId)) {
            // initialize one
            HashSet<WebSocketChannel> webSocketChannels = new HashSet<>();
            webSocketChannels.add(channel);
            pool.put(userId, webSocketChannels);
        } else {
            // if the user already has a channel
            Set<WebSocketChannel> webSocketChannels = pool.get(userId);
            // check if the channel exited
            if (webSocketChannels.contains(channel)) {
                throw new DuplicatedChannelException("Duplicated channel connection, userId: " + userId + ", channelUUID: "+ channel.getUuid());
            }
            // add the channel in
            webSocketChannels.add(channel);
        }
    }

    /**
     * remove the channel when user is inactive
     *
     * @param channel the channel to be removed
     */
    public static void remove(Channel channel) {
        // get the modified entry
        Map.Entry<Long, Set<WebSocketChannel>> entry = null;
        for (Map.Entry<Long, Set<WebSocketChannel>> e : pool.entrySet()) {
            // get the target channel
            Set<WebSocketChannel> target = e.getValue().stream().filter(c -> c.getChannel().equals(channel)).collect(Collectors.toSet());
            // remove if it has
            if (!target.isEmpty()) {
                e.getValue().removeAll(target);
                entry = e;
                break;
            }
        }
        // check the entry
        assert entry != null;
        if (entry.getValue().isEmpty()) {
            pool.remove(entry.getKey());
        }
    }

    /**
     * return if the server has the channel of the id
     *
     * @param id user id
     * @return true if it has, otherwise false
     */
    public static boolean isContain(Long id) {
        return pool.containsKey(id);
    }

    /**
     * get channels of the id, call after isContain
     *
     * @param id user id
     * @return channels
     */
    public static Set<WebSocketChannel> getChannels(Long id) {
        return pool.get(id);
    }


}
