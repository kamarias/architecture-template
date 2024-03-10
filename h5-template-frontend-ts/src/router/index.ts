//历史记录模式，除了hash外，还有html5的history
import {
  createRouter,
  createWebHashHistory,
  RouteRecordRaw
} from 'vue-router'
import {default as routeIndexRaw} from '@/router/modules/index';
import {default as routeUserRaw} from '@/router/modules/user';

const routeComponents = [
  ...routeIndexRaw,
  ...routeUserRaw
] as Array<RouteRecordRaw>;

const routes: Array<RouteRecordRaw> = [
  //重定向
  {
    path: '/',
    redirect: '/index'
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: [...routes, ...routeComponents],

  // @ts-ignore 不校验是否使用
  scrollBehavior: (to, from, savedPosition) => {
    return savedPosition ? savedPosition : {x: 0, y: 0, behavior: 'smooth'};
  }
})

export default router
