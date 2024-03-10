import {defineStore} from 'pinia'


const useUserStore = defineStore('user', {
  // 定义状态
  state: () => {
    return {
      token: '',
      userInfo: '用户信息',
      counter: 0
    }
  },
  // 获取状态
  getters: {
    isLogin(state) {
      return state.token
    },
    getToken(state) {
      return state.token
    },
    getCounter(state) {
      return state.counter
    }
  },
  // 改变状态
  actions: {
    increment() {
      this.counter++
    },
    setCount(counter) {
      this.counter = counter
    },
    reSetCount() {
      this.counter = 0
    },
    setToken(str) {
      this.token = str
    }
  }
})

export {useUserStore}
