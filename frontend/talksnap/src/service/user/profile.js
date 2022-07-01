import axios from "axios";
import store from "@/store";
import MsgIndicator from "@/utils/msgIndicator";
import router from "@/router";

const ProfileFetcher = {

    // fetch with token
    fetchMyProfile: async () => {
        let profile = await axios.get("/user/profile/fetchUser")
            .then(res => {
                return res.data.code == 200 ? res.data.data : null;
            })

            if (profile) {
                if (profile.subscriptions) {
                    // parse the subscriptions
                    const keys = Object.keys(profile.subscriptions);
                    const values = Object.values(profile.subscriptions);
                    profile.subscriptions_keys = keys;
                    profile.subscriptions_values = values;
                }
                // store the profile
                store.commit("setMyProfile", profile);
                console.log(profile)
            }
            
    },

    searchUser: async (input) => {
        const data = await axios.get("/user/profile/search/" + input)
            .then(res => {
                return res.data.code == 200 ? res.data.data : null;
            })
        return data;
    },

    // query a user and get the profile
    fetchUserProfile: async (id) => {
        const profile = await axios.get("/user/profile/fetch/" + id)
            .then(res => {
                return res.data.code == 200 ? res.data.data : null;
            })
        return profile;
    }
}

const ProfileEditor = {

    editNickname: async (nickname) => {
        const currentProfile = store.getters.getMyProfile;
        if (nickname !== currentProfile.nickname) {
            const result = await axios.put('/user/profile/edit/name', { 'nickname': nickname })
                .then(res => {
                    return res.data.code == 200 ? res.data.data : null;
                })

            if (result) {
                // parse result
                const { token, nickname } = result;
                // reset token and nickname
                // get old profile
                let profile = store.getters.getMyProfile;
                profile.nickname = nickname;
                // update token and profile
                store.commit('setToken', token);
                store.commit('setMyProfile', profile);
                return 1;
            }
            return 0;
        }
        return 1;
    },

    editEmail: async (email) => {
        const currentProfile = store.getters.getMyProfile;
        if (email !== currentProfile.email) {
            const result = await axios.put('/user/profile/edit/email', { 'email': email })
                .then(res => {
                    return res.data.code == 200 ? res.data.data : null;
                })

            if (result) {
                // parse result
                const { email } = result;
                // reset token and email
                // get old profile
                let profile = store.getters.getMyProfile;
                profile.email = email;
                // update token
                store.commit('setMyProfile', profile);
                return 1;
            }
            return 0;
        }
        return 1;
    },

    editPassword: async (form) => {
        const { oldPassword, newPassword } = form;
        const result = await axios.put('/user/profile/edit/password', {
            'oldPassword': oldPassword,
            'newPassword': newPassword
        })
            .then(res => {
                if (res.data.code == 200) {
                    return res.data.data;
                }
                MsgIndicator.error(res.data.message);
                return null;
            })

        if (result) {
            MsgIndicator.success("Edit completed");
            // ask for relogin 
            store.dispatch('deleteToken');
            router.go(0);
            return 1;
        }
        return 0;
    },

    editBio: async (bio) => {
        const currentProfile = store.getters.getMyProfile;
        if (bio !== currentProfile.bio) {
            const result = await axios.put('/user/profile/edit/bio', { 'bio': bio })
                .then(res => {
                    return res.data.code == 200 ? res.data.data : null;
                })

            if (result) {
                // parse result
                const { bio } = result;
                // reset token and bio
                // get old profile
                let profile = store.getters.getMyProfile;
                profile.bio = bio;
                // update token
                store.commit('setMyProfile', profile);
                MsgIndicator.success('Edit completed');
            }
        }
    },

    deleteAccount: async () => {
        await axios.delete('/user/profile/delete')
            .then(res => {
                if (res.data.code == 200) {
                    MsgIndicator.success(res.data.data);
                }
            })
    },

    subscribe: async (user) => {
        const { id, nickname } = user;
        const result = await axios.put('/user/profile/subscribe', {
            userId: id,
            nickname: nickname
        })
        .then(res => {
            return res.data.code == 200 ? res.data.data.subscriptions : {};
        })
        
        // determine if subscribe or unsubscribe
        // get profile from store
        let profile = store.getters.getMyProfile;
        const { subscriptions } = profile;
        // finish code
        let code;
        // unsubscribe
        if (result.length < subscriptions.length) {
            MsgIndicator.warning("You unsubscribed " + nickname);
            code = 0;
        } else {
            MsgIndicator.success("You subscribed " + nickname);
            code = 1;
        }
        // update store
        profile.subscriptions = result;
        profile.subscriptions_keys = Object.keys(result);
        profile.subscriptions_values = Object.values(result);
        store.commit('setMyProfile', profile);
        return code;
    }

}

export { ProfileFetcher, ProfileEditor };