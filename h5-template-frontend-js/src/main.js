import {createApp} from 'vue'
import App from './App.vue'
import router from '@/router/'

//  自定义的全局样式
import '@/style/scss/index.scss'

import '@/common/flexible'
// 导入Vant4 不会按需引入的样式
import 'vant/es/toast/style'
import 'vant/es/dialog/style'
import 'vant/es/notify/style'
import 'vant/es/image-preview/style'

const isProd = import.meta.env.VITE_ENV === 'production'
import * as Sentry from '@sentry/vue'

const app = createApp(App)

import {createPinia} from 'pinia';

app.use(createPinia());

app.use(router)
if (isProd) {
    Sentry.init({
        app,
        dsn: 'https://8227ed09a1d9428cab91ab4bed97f992@sentry.gogpay.cn/10',
        integrations: [
            new BrowserTracing({
                routingInstrumentation: Sentry.vueRouterInstrumentation(router),
                tracingOrigins: ['localhost', /^\//]
            })
        ],
        tracesSampleRate: 1.0
    })
}

app.mount('#app')
