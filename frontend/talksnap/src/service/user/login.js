import axios from "axios"
import MsgIndicator from "@/utils/msgIndicator";
import Notification from "@/utils/notification";
import store from "@/store";
import router from "@/router";

const LoginProcess = {

    async signup(signupForm) {
        // get current time
        let date = new Date();
        await axios.post("/user/signup", {
            nickname: signupForm.nickname,
            email: signupForm.email,
            password: signupForm.password,
            joinTime: date.getTime(),
        })
        .then(res => {
            // determine http code
            if (res.data.code == 200) {
                MsgIndicator.success(res.data.data);
                router.push('/login');
            } else {
                MsgIndicator.error(res.data.message);
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async loginWithAccount(loginForm) {
        await axios.post("/user/login", {
            email: loginForm.email,
            password: loginForm.password
        })
        .then(res => {
            // check http code
            if (res.data.code == 200) {
                // store the token locally
                store.commit("setToken", res.data.data);
                // print successful msg
                MsgIndicator.success("Welcome back!");
                // redirect to media home page
                router.push("/home");
            } else {
                // print the error message
                MsgIndicator.error(res.data.message);
                // redirect to login page
                router.push("/login");
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async loginWithToken() {
        // get token
        let auth = localStorage.token;
        await axios.post("/user/login", {
            token: auth
        })
        .then(res => {
            // check http code
            if (res.data.code == 200) {
                // print successful msg
                MsgIndicator.success("Welcome back!");
                // commit to vuex store cache
                store.commit("setToken", localStorage.token);
                return 1;
            } else {
                // print error msg
                MsgIndicator.error(res.data.message);
                return 0;
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    }
};

export default LoginProcess;
