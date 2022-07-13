package com.dev.chat;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456L;

    // device id
    private long uuid;
    // sender
    private String token;
    // receiver
    private Long to;
    // message type
    private MessageType type;
    // content
    private Object content;
    // timestamp
    private Date time;
    // total length
    private int length;
    // file extension
    private String fileExtension;

}
