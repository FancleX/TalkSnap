import { ws } from '../websocket'
import store from '@/store'
import { send } from '../handlers/sendHandler' 

ws.onmessage = (message) => {
    // parse message
    const data = JSON.parse(message);
    const { type } = data;
    // construct single message entity or assign all history
    // check type
    // text or heartbeat or fetch
    switch (type) {
        case "TEXT":
            store.dispatch('appendHistory', data);
            break;
        case "HEART_BEAT":
            // only type
            setTimeout(send(null, TypeEnum.HEART_BEAT, null, null), 5000);
            break;
        case "FETCH":
            // only content {contactId: [[history], unreads]} and type
            console.log(data.content);
            store.commit("setChatHistory", data.content);
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