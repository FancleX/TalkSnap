import axios from "axios"
import { MsgIndicator } from "@/utils/MsgIndicator";
import { Notification } from "@/utils/Notification";

const LoginProcess = {

    async signup(nickname, email, password) {
        // get current time
        let date = new Date();
        await axios.post("user/signup", {
            nickname: nickname,
            email: email,
            password: password,
            joinTime: date.toLocaleString,
        })
        .then(res => {
            // determine http code
            if (res.data.code == 200) {
                MsgIndicator.success(res.data.data);
            } else {
                MsgIndicator.error(res.data.message);
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async login(email, password) {
        
    }


};

export default LoginProcess;
