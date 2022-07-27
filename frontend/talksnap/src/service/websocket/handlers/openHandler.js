import ws from '../websocket'
import { send } from '../handlers/sendHandler' 
import { TypeEnum } from '../handlers/type'


ws.onopen = () => {
    console.log("Connect to server")
    // start login
    send(null, TypeEnum.LOGIN, null, null);
    // fetch history
    send(null, TypeEnum.FETCH, null, null);
    // start heartbeat
    send(null, TypeEnum.HEART_BEAT, null, null);
}