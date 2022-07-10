package com.dev.chat;

import lombok.Data;

import java.nio.channels.Channel;
import java.util.Objects;

@Data
public class WebSocketChannel {

    private Long uuid;
    private Channel channel;

    public WebSocketChannel(Long uuid, Channel channel) {
        this.uuid = uuid;
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebSocketChannel that)) return false;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
