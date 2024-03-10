import { createApp } from 'vue'

import App from '@/App.vue'
import router from '@/router/'

import "@/styles/css/tailwind.css";
import '@/styles/scss/index.scss'
//  自定义的全局样式
import '@/common/flexible'

// 导入Vant4 不会按需引入的样式
import 'vant/es/toast/style'
import 'vant/es/dialog/style'
import 'vant/es/notify/style'
import 'vant/es/image-preview/style'

import {createPinia} from 'pinia';
const app = createApp(App)
app.use(router)
app.use(createPinia());

app.mount('#app')

