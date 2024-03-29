import axios from "axios";
import MsgIndicator from "../utils/msgIndicator";
import router from '@/router'
import store from '@/store'

// global url 
let protocol = window.location.protocol;
let host = window.location.host;
let reg = /^localhost+/;
if (reg.test(host)) {
    // gateway
    axios.defaults.baseURL = 'http://localhost:9003';
} else {
    axios.defaults.baseURL = protocol + "//" + host + ":9003";
}

axios.defaults.timeout = 10000;

// request interceptor
axios.interceptors.request.use((config) => {
    // exclude the login and home and signup page
    let path = router.path;
    if (path != "/" || path != "/login" || path != "/signup") {
        // get token from web storage
        if (localStorage.token) {
            config.headers.authorization = localStorage.token;
        }
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// response interceptor
axios.interceptors.response.use((response) => {
    return response.status === 200 ? Promise.resolve(response) : Promise.reject(response);
},  (error) => {
    // response error
    const { response } = error;
    if (response) {
        errorHandle(response.status, response.data.message)
        return Promise.reject(response.data);
    } else {
        MsgIndicator.error("Server busy or Internet traffic");
    }
});

// handle error
const errorHandle = (status, other) => {
    switch (status) {
        // bad request
        case 400:
            MsgIndicator.error("Request failed.");
            break;
        // Unauthorized
        case 401:
            MsgIndicator.error(other);
            // clear invalid token
            store.dispatch("deleteToken");
            // redirect to login page
            router.go(0);
            break;
        // Forbidden
        case 403:
            MsgIndicator.error("You don't have permission to access this site.");
            // redirect to home page
            router.push("/home");
            break;
        // redirect to 404 page  
        case 404:
            router.push("/notFoundPage");
            break;
        default:
            MsgIndicator.warning(other);
            break;
    }
}



