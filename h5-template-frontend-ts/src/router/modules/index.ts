import {RouteRecordRaw} from "vue-router";

export default [
  {
    path: '/index',
    name: 'index',
    component: () => import('@/views/index.vue'),
    meta: {
      title: '首页', // 自动设置当前页面的标题
      keepAlive: true
    }
  },
  {
    path: '/notFound',
    name: 'notFound',
    component: () => import('@/views/notFound.vue'),
    meta: {
      title: '页面不存在',
      keepAlive: true
    }
  },
] as Array<RouteRecordRaw>;
