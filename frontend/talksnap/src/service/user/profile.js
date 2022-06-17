import axios from "axios";
import { Notification } from "@/utils/notification";

const ProfileFetcher = {

    // fetch with token
    async fetchUserProfile() {
        // check if profile exits in storage
        const profile = this.$store.getters["getUserProfile"];
        if (profile != null) {
            return profile;
        }
        await axios.get("/user/profile/fetchUser")
        .then(res => {
            if (res.data.code == 200) {
                // create a user profile
                const { userProfile } = res.data.data;
                // store the profile
                this.$store.commit("setUserProfile", userProfile);
                return userProfile;
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    }

}

export default ProfileFetcher;