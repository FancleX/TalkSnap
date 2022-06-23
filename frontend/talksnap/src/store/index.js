import { createStore } from 'vuex'

export default createStore({
  state: {
    token: "",
    userProfile: {}
  },
  getters: {
    getAuth(state) {
      return state.token;
    },
    getUserProfile(state) {
      if (state.userProfile) {
        return JSON.parse(state.userProfile);
      }
      return null;
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
    setUserProfile(state, profile) {
      state.userProfile = JSON.stringify(profile);
    },
    deleteProfile(state) {
      state.userProfile = {};
    }
  },
  actions: {
    deleteToken(context) {
      context.commit("deleteToken");
      context.commit("deleteProfile");
    },
    // use for user change something with a new token issued
    updateToken(context, token, profile) {
      context.commit("setToken", token);
      context.commit("setUserProfile", profile);
    }
  },
  modules: {
  }
})
