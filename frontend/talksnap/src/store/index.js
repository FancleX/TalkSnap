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
    setChatHistory(state, history) {
      state.chatHistory = history;
    },
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
    chatHistory: new Map()
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
    getHistoryById(state, id) {
      return state.chatHistory.get(id);
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
    setChatHistory(state, history) {
      if (history.length !== 0) {
        let keys = Object.keys(history);
        let values = Object.values(history);
        for (let i = 0; i < keys.length; i++) {
          state.chatHistory.set(keys[i], values[i]);
        }
      } 
    },
    markRead(state, id, entity) {
      state.chatHistory.get(id).forEach(e => {
        e[0].forEach(h => {
          if (h === entity) {
            e[1]--;
          }
        });
      });
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
    appendHistory(context, entity) {
      let id = context.getters.getId;
      // check if the user is a sender or receiver
      let target = id === entity.fromId ? entity.to : entity.fromId;
      let history = context.getters.getHistoryById(target);
      if (history != null) {
        // add the entity
        history[0].push(entity);
        // increase unreads
        history[1]++;
        context.getters.getHistoryById(target) = history;
      } else {
        // create an entity to map
        context.getters.getAllHistory.set(target, [[entity], 1]);
      }
    }
  },
})
