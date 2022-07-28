import ws from '../websocket'
import store from '@/store'
import { send } from '../handlers/sendHandler' 

ws.onmessage = (message) => {
    // parse message
    const data = JSON.parse(message);
    const { fromId, fromName, to, content, time, type, isRead } = data;
    // check type
    // text or heartbeat or fetch
    switch (type) {
        case "TEXT":
            
            break;
        case "HEART_BEAT":
            // only type
            setTimeout(send(null, TypeEnum.HEART_BEAT, null, null), 5000);
            break;
        case "FETCH":
            // only content and type
            break;        
    }


    // save to vuex if text

}


// @NotNull
// private Long fromId;

// @Transient
// private String fromName;

// @NotNull
// private Long to;

// private Object content;

// @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
// private Date time;

// @NotNull
// private MessageType type;

// private boolean isRead;