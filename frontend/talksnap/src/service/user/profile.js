import axios from "axios";
import Notification from "@/utils/notification";
import store from "@/store";
import MsgIndicator from "@/utils/msgIndicator";
import router from "@/router";

const ProfileFetcher = {

    // fetch with token
    // fetch with token
    fetchMyProfile: async () => {
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
            });
    },

    searchUser: async (input) => {
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
    fetchUserProfile: async (id) => {
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

    editNickname: async (nickname) => {
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
    },

    editEmail: async (email) => {
        const currentProfile = store.getters.getMyProfile;
        if (email !== currentProfile.email) {
            const result = await axios.put('/user/profile/edit/email', { 'email': email })
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
            .catch(err => {
                Notification.alert(err);
            });

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
                    if (res.data.code == 200) {
                        return res.data.data;
                    }
                    MsgIndicator.error(res.data.message);
                    return null;
                })
                .catch(err => {
                    Notification.alert(err);
                });

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
                } else {
                    MsgIndicator.error(res.data.message);
                }
            })
            .catch(err => {
                Notification.alert(err);
            })
    }

}

export { ProfileFetcher, ProfileEditor };