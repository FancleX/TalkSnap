import axios from "axios";
import Notification from "@/utils/notification";
import store from "@/store";
import MsgIndicator from "@/utils/msgIndicator";

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
            } else {
                MsgIndicator.error(res.data.message);
            }
        })
        .catch(err => {
            Notification.alert(err);
        })
    },

    async searchUser(input) {
        const data = await axios.get("/user/profile/search/" + input)
        .then(res => {
            if (res.data.code == 200) {
                return res.data.data;
            }
            MsgIndicator.error(res.data.message);
            return null;
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
            if (res.data.code == 200) {
                return res.data.data;
            }
            MsgIndicator.error(res.data.message);
            return null;
        })
        .catch(err => {
            Notification.alert(err);
        })
        return profile;
    }  
}

const ProfileEditor = {

    async editProfile(form) {
        const { nickname, email, bio } = form;
        await this.editNickname(nickname);
    },

    async editNickname(nickname) {
        const currentProfile = store.getters.getMyProfile;
        if (nickname !== currentProfile.nickname) {
            const result = await axios.put('/user/profile/edit/name', { 'nickname': nickname })
            .then(res => {
                if (res.data.code == 200) {
                    return res.data.data;
                }
                MsgIndicator.error(res.data.message);
                return null;
            })
            .catch(err => {
                Notification.alert(err);
            });
            
            if (result != null) {
                // parse result
                const { token, nickname } = result;
                // reset token and nickname
                // get old profile
                let profile = store.getters.getMyProfile;
                profile.nickname = nickname;
                // update token
                store.dispatch('updateToken', token, profile);
            }
        }
    }
}

export { ProfileFetcher, ProfileEditor };