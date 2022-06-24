import axios from "axios";
import Notification from "@/utils/notification";
import store from "@/store";

const ProfileFetcher = {

    // fetch with token
    async fetchMyProfile() {
        await axios.get("/user/profile/fetchUser")
        .then(res => {
            if (res.data.code == 200) {
                // create a user profile
                const profile = res.data.data;
                // store the profile
                store.commit("setMyProfile", profile);
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async searchUser(input) {
        const data = await axios.get("/user/profile/search/" + input)
        .then(res => {
            return res.data.data;
        })
        .catch(err => {
            Notification.alert(err);
        })
        return data;
    },

    // query a user and get the profile
    async fetchUserProfile(id) {
        const profile = await axios.get("/user/profile/fetch/" + id)
        .then(res => {
            return res.data.data;
        })
        .catch(err => {
            Notification.alert(err);
        })
        return profile;
    }
    

}

export default ProfileFetcher;