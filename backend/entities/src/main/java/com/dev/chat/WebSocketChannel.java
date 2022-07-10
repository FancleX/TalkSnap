package com.dev.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

import io.netty.channel.Channel;
import java.util.Objects;

@Data
@AllArgsConstructor
public class WebSocketChannel {

    private Long uuid;

    private Channel channel;

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
