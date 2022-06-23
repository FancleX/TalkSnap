import axios from "axios";
import Notification from "@/utils/notification";
import store from "@/store";

const ProfileFetcher = {

    // fetch with token
    async fetchUserProfile() {
        await axios.get("/user/profile/fetchUser")
        .then(res => {
            if (res.data.code == 200) {
                // create a user profile
                const profile = res.data.data;
                // store the profile
                store.commit("setUserProfile", profile);
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
    }
    

}

export default ProfileFetcher;