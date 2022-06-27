import { createStore } from 'vuex'

export default createStore({
  state: {
    token: "",
    userProfile: {},
    isLogin: false
  },
  getters: {
    getAuth(state) {
      return state.token;
    },
    getMyProfile(state) {

      // if (state.userProfile) {
      //   return JSON.parse(state.userProfile);
      // }
      try {
        return JSON.parse(state.userProfile);
      } catch (e) {
        return null;
      }
    },
    isLogin(state) {
      return state.isLogin;
    }
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
      localStorage.token = token;
    },
    deleteToken(state) {
      state.token = "";
      localStorage.removeItem("token");
    },
    setMyProfile(state, profile) {
      state.userProfile = JSON.stringify(profile);
    },
    deleteProfile(state) {
      state.userProfile = {};
    },
    login(state) {
      state.isLogin = true;
      sessionStorage.isLogin = true;
    },
    logout(state) {
      state.isLogin = false;
      sessionStorage.removeItem('isLogin');
    }
  },
  actions: {
    deleteToken(context) {
      context.commit("deleteToken");
      context.commit("deleteProfile");
      context.commit("logout");
    },
    setMyProfileAvatar(context, img) {
      let profile = context.getters.getMyProfile;
      profile.profile_img = img;
      context.commit('setMyProfile', profile);
    },
    setMyProfileBg(context, img) {
      let profile = context.getters.getMyProfile;
      profile.bg_img = img;
      context.commit('setMyProfile', profile);
    },
  },
  modules: {
  }
})
