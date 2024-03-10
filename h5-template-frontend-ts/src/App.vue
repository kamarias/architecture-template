<template>
  <router-view v-slot="{ Component }">
    <keep-alive :include="KeepAliveList">
      <component :is="Component"></component>
    </keep-alive>
  </router-view>
</template>

<script setup>
import {useRouter} from "vue-router";
import {ref} from "vue";

const route = useRouter()
const KeepAliveList = ref([])
route.beforeEach((to, from, next) => {
  // 路由标题
  const title = to?.meta?.title || ''
  const keepAlive = to?.meta?.keepAlive
  if (keepAlive) {
    if (KeepAliveList.value.indexOf(to.name) === -1) {
      KeepAliveList.value.push(to.name)
    } else {
      const index = KeepAliveList.value.findIndex((name) => name === from.name)
      index > -1 ? KeepAliveList.value.splice(index, 1) : null
    }
  }
  next()
})
</script>


<style lang="scss">
#app {
  width: 100%;
  height: 100%;
  overflow: hidden;
  @media only screen and (min-width: 500px) {
    max-width: 500px;
    margin: auto;
    transform: scale(1);
  }
}
</style>
