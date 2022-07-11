package com.dev.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class MQObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456L;

    private String from;

    private Long to;

    private Object content;

    private Date time;

    private MessageType type;
}
