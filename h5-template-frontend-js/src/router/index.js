//历史记录模式，除了hash外，还有html5的history
import {createRouter, createWebHashHistory} from 'vue-router'
import {default as routeIndexRaw} from '@/router/modules/index';
import {default as routeUserRaw} from '@/router/modules/user';

const routeComponents = [
    ...routeIndexRaw,
    ...routeUserRaw
]

// 路由重定向
const routes = [
    //重定向
    {
        path: '/',
        redirect: '/index'
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: [...routes, ...routeComponents],
    scrollBehavior(to, from, savedPosition) {
        return savedPosition ? savedPosition : {x: 0, y: 0, behavior: 'smooth'};
    },
})

export default router
