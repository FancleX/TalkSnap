import axios from "axios";
import Notification from "@/utils/notification";
import store from "@/store";

const ProfileFetcher = {

    // fetch with token
    async fetchUserProfile() {
        // check if profile exits in storage
        let profile = store.getters.getUserProfile;
        if (profile != null) {
            return profile;
        }
        await axios.get("/user/profile/fetchUser")
        .then(res => {
            if (res.data.code == 200) {
                // create a user profile
                profile = res.data.data;
                // store the profile
                store.commit("setUserProfile", userProfile);
                return userProfile;
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async searchUser(input) {
        let data = [];
        await axios.get("/user/profile/search/" + input)
        .then(res => {
            data = res.data.data
        })
        .catch(err => {
            Notification.alert(err);
        })
        return data;
    }
    

}

export default ProfileFetcher;