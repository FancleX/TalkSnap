import axios from "axios"
import MsgIndicator from "@/utils/msgIndicator";
import Notification from "@/utils/notification";
import store from "@/store";
import router from "@/router";
import  { ProfileFetcher } from "./profile";

const LoginProcess = {

    signup: async(signupForm) => {
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
    },

    loginWithAccount: async(loginForm) => {
        const response = await axios.post("/user/login", {
            email: loginForm.email,
            password: loginForm.password
        })
        .then(res => {
            // check http code
            if (res.data.code == 200) {
                // store the token locally
                store.commit("setToken", res.data.data);
                store.commit("login");
                return 1;
            } else {
                // print the error message
                MsgIndicator.error(res.data.message);
                return 0;
            }
        })

        if (response) {
            // fetch the user profile
            await ProfileFetcher.fetchMyProfile();
            // print successful msg
            MsgIndicator.success("Welcome back " + store.getters.getMyProfile.nickname +"!");
            // redirect to media home page
            router.push("/home");
        } else {
            // redirect to login page
            router.push("/login");
        }
    },

    loginWithToken: async() => {
        // get token
        let auth = store.getters.getAuth;
        const status = await axios.post("/user/login", {
            token: auth
        })
        .then(res => {
            // check http code
            return res.data.code == 200 ? 1 : 0; 
        })

        if (status) {
            // fetch the user profile
            await ProfileFetcher.fetchMyProfile();
            // print successful msg
            MsgIndicator.success("Welcome back " + store.getters.getMyProfile.nickname +"!");
            return 1;
        }
        return 0;
    }
};

export default LoginProcess;
