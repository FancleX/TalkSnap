package com.dev.chat;

import com.sun.istack.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@Document("chat_message")
public class MQObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456L;

    @Id
    private ObjectId id;

    @Transient
    // client uuid
    private Long uuid;

    @NotNull
    private Long fromId;

    @Transient
    private String fromName;

    @NotNull
    private Long to;

    private Object content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date time;

    @NotNull
    private MessageType type;

    private boolean isRead;

    public MQObject(Long uuid, Long fromId, String fromName, Long to, Object content, Date time, MessageType type, boolean isRead) {
        this.uuid = uuid;
        this.fromId = fromId;
        this.fromName = fromName;
        this.to = to;
        this.content = content;
        this.time = time;
        this.type = type;
        this.isRead = isRead;
    }

    public MQObject(MessageType type) {
        this.type = type;
    }

    public MQObject(Object content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MQObject mqObject)) return false;
        return id.equals(mqObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
