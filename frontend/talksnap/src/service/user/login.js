import axios from "axios"
import MsgIndicator from "@/utils/msgIndicator";
import Notification from "@/utils/notification";

const LoginProcess = {

    async signup(signupForm) {
        // get current time
        let date = new Date();
        await axios.post("/user/signup", {
            nickname: signupForm.nickname,
            email: signupForm.email,
            password: signupForm.password,
            joinTime: date.toLocaleString,
        })
        .then(res => {
            // determine http code
            if (res.data.code == 200) {
                MsgIndicator.success(res.data.data);
                this.$router.push('/login');
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
                this.$store.commit("setToken", res.data.data);
                // print successful msg
                MsgIndicator.success("Welcome back!");
                // redirect to media home page
                this.$router.push("/home");
            } else {
                // print the error message
                MsgIndicator.error(res.data.message);
                // redirect to login page
                this.$router.push("/login");
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async loginWithToken() {
        // get token
        let auth = this.$store.getters.getAuth;
        await axios.post("/user/login", {
            token: auth
        })
        .then(res => {
            // check http code
            if (res.data.code == 200) {
                // print successful msg
                MsgIndicator.success("Welcome back!");
                // redirect to home
                this.$router.push("/home");
            } else {
                // print error msg
                MsgIndicator.error(res.data.message);
                // redirect to login
                this.$router.push("/login");
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    }
};

export default LoginProcess;
