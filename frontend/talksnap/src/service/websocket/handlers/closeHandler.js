import ws from '../websocket'
import { send } from '../handlers/sendHandler' 
import { TypeEnum } from '../handlers/type'
import Notification from '@/utils/notification'

const reconnect = () => {
    // notification
    Notification.alert("Lost connection with server, reconnecting...");
    return setInterval(send(null, TypeEnum.HEART_BEAT, null, null), 5000);
}

const close = (event) => {
    clearInterval(event);
    ws.close();
}

ws.onclose = () => {
    // retry connection
    const re = reconnect();
    setTimeout(close(re), 20*5000);
}