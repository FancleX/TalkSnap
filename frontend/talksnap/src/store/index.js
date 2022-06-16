import { createStore } from 'vuex'

export default createStore({
  state: {
    token: ""
  },
  getters: {
    getAuth(state) {
      return state.authorization;
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
    }
  },
  actions: {
  },
  modules: {
  }
})
