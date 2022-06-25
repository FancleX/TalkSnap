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
      if (state.userProfile) {
        return JSON.parse(state.userProfile);
      }
      return null;
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
  },
  actions: {
    deleteToken(context) {
      context.commit("deleteToken");
      context.commit("deleteProfile");
    },
    // use for user change something with a new token issued
    updateToken(context, token, profile) {
      context.commit("setToken", token);
      context.commit("setMyProfile", profile);
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
    }
  },
  modules: {
  }
})
