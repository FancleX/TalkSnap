import { createStore } from 'vuex'


const history = {
  state: {
    // entity: {
    //   id: Number,
    //   context: [],
    //   unreads: Number
    // },
    chatHistory: []
  },
  getters: {
    getAllHistory(state) {
      return state.chatHistory;
    }
  },
  mutations: {
    // constructEntity(state, id, context, unreads) {
    //   const entity = {
    //     id: id,
    //     context: context,
    //     unreads: unreads
    //   }
    //   return entity;
    // }
    appendHistory(state, entity) {
      state.history.push(entity);
      state.history.forEach(element => {
        // check if the user is a sender or receiver
        // query the id, and push the history to the associated list
        // update unreads
      });
    }
  }
}


export default createStore({
  state: {
    token: "",
    userProfile: {},
    isLogin: false,
    chatHistory: []
  },
  getters: {
    getAuth(state) {
      return state.token;
    },
    getMyProfile(state) {
      try {
        return JSON.parse(state.userProfile);
      } catch (e) {
        return null;
      }
    },
    isLogin(state) {
      return state.isLogin;
    },
    getId(state) {
      return state.userProfile.id;
    },
    getAllHistory(state) {
      return state.chatHistory;
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
    },
    appendHistory(state, entity) {
      state.history.push(entity);
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
    h: history
  }
})
