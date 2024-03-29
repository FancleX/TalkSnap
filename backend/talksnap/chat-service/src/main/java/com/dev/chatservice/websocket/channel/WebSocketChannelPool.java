package com.dev.chatservice.websocket.channel;

import com.dev.chat.WebSocketChannel;
import com.dev.exception.DuplicatedChannelException;
import io.netty.channel.Channel;


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
     * remove the channel from the pool when user is inactive
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
        // remove if no channel bind with the user
        if (entry.getValue().isEmpty()) {
            pool.remove(entry.getKey());
        }
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

    /**
     * Determine if the server has the user and its specific channel
     *
     * @param id the user id
     * @param uuid the user channel id
     * @return true if meet requirements
     */
    public static boolean isContain(Long id, Long uuid) {
        Set<WebSocketChannel> webSocketChannels = pool.get(id);
        if (webSocketChannels.isEmpty()) {
            return false;
        }
        return webSocketChannels.stream().anyMatch(c -> c.getUuid().equals(uuid));
    }

}
