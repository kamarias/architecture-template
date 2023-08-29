<template>
  <vant-refresh-list
    :disabled-pull-refresh="false"
    :load="queryData"
    :top-pull-refresh="true"
    scroll-height="700px">
    <template #default="{data}">
      <waterfall
        :list="data"
        :gutter="10"
        :has-around-gutter="true"
        :width="200"
        :breakpoints="{1200: {rowPerView: 4,},
                          800: {
                            rowPerView: 3,
                          },
                        500: {
                          rowPerView: 2,
                        },}"
        :lazyload="true">
        <template #item="{item, url, index}">
          <div class="bg-gray-900 rounded-lg shadow-md overflow-hidden transition-all duration-300 ease-linear hover:shadow-lg hover:shadow-gray-600 group">
            <div class="overflow-hidden">
              <LazyImg :url="item.url"  class="cursor-pointer transition-all duration-300 ease-linear group-hover:scale-105" />
            </div>
            <div class="px-4 pt-2 pb-4 border-t border-t-gray-800">
              <h2 class="pb-4 text-gray-50 group-hover:text-yellow-300">
                {{ item.name }}
              </h2>
              <div class="pt-3 flex justify-between items-center border-t border-t-gray-600 border-opacity-50">
                <div class="text-gray-50">
                  $ {{ item.age }}
                </div>
              </div>
            </div>
          </div>
        </template>
      </waterfall>
    </template>
    <template #extra>
      <div class="text-3xl underline font-bold text-blue-400">
        111222
      </div>
    </template>
  </vant-refresh-list>
</template>

<script lang="ts" setup>
import VantRefreshList from "@/components/VantRefreshList/index.vue"
import {LazyImg, Waterfall} from 'vue-waterfall-plugin-next'
import 'vue-waterfall-plugin-next/dist/style.css'

const queryData = async ({pageNum}) => {
  if (pageNum < 70) {
    return await Promise.all([
      {
        name: "测试滚动顶顶顶顶顶发的大多数" + pageNum,
        age: 23,
        url: "https://resource.duotest.cn/young-card/api/ad1364520c174cf180a08d345fb6a241.png"
      }, {
        name: "苹果得到的答复" + pageNum,
        age: 24,
        url: "https://resource.duotest.cn/young-card/api/ad1364520c174cf180a08d345fb6a241.png"
      },
      {
        name: "测试滚动的烦烦烦烦烦烦对的大大大大大大" + pageNum,
        age: 23,
        url: "https://resource.duotest.cn/young-card/api/ad1364520c174cf180a08d345fb6a241.png"
      }, {
        name: "苹果" + pageNum,
        age: 24,
        url: "https://resource.duotest.cn/young-card/api/ad1364520c174cf180a08d345fb6a241.png"
      },
    ])
  } else {
    return Promise.all([])
  }
}

</script>

<style scoped lang="scss">

</style>
