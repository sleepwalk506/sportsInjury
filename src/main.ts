// main.ts
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import LoginView from './views/LoginView.vue'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

// main.js
// const token = localStorage.getItem('authToken')
// if (token) {
//   // 使用 token 来验证用户身份，保持登录状态
// }

// const app = createApp(LoginView)
const app = createApp(App)

const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);
app.use(pinia)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(ElementPlus).use(store).use(router)
app.mount('#app')

