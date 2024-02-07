import { createStore } from 'vuex'

const store = createStore({
  state: {
    userData: {},
    anotherUserData: {},
    sportsRecordsData: [],
    blogData: [],
    injuryRecordsData: [],
  },
  mutations: {
    setUser(state, responseData) {
      state.userData = responseData
    },
    setAnotherUser(state, responseData) {
      state.anotherUserData = responseData
    },
    setSportsRecords(state, responseData) {
      state.sportsRecordsData = responseData
    },
    setBlogData(state, responseData) {
      state.blogData = responseData
    },
    setInjuryRecords(state, responseData) {
      state.injuryRecordsData = responseData
    },
    // ����mutations
  },
  actions: {
    // ����actions
  }
})

export default store
