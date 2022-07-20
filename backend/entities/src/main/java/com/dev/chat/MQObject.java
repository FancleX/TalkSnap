package com.dev.chat;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Document
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

    private Date time;

    @NotNull
    private MessageType type;

    private boolean isRead;
}
