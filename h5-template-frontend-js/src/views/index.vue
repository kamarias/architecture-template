<template>
  <h1>这里是首页</h1>
  <div class="text-cyan-300" >Hello,World</div>
  <van-button type="primary">主要按钮</van-button>
  <van-button type="success">成功按钮</van-button>
  <van-button type="default">默认按钮</van-button>
  <van-button type="warning">警告按钮</van-button>
  <van-button type="danger">危险按钮</van-button>

  <van-button type="danger" @click="countAdd">危险按钮</van-button>
  <div>{{ count }}</div>

  <van-badge :content="5">
    <div class="child" />
  </van-badge>
  <van-badge :content="10">
    <div class="child" />
  </van-badge>
  <van-badge content="Hot">
    <div class="child" />
  </van-badge>
  <van-badge dot>
    <div class="child" />
  </van-badge>
  <div>{{ dataList }}</div>
  <div>{{ total }}</div>
</template>

<script setup>


import {login} from "@/api/login.js";
import {reactive, ref, watchEffect} from "vue";
import {throttleRef, debounceRef} from "@/hooks/customRef.js";
import {useInterval} from "@/hooks/interval.js";

let total = ref(0)
let dataList = ref([])
let count = debounceRef(1, 500)

const test = async (queryParam) => {
  const {code, data, msg} = await login(queryParam)
  dataList.value = data.list
  total = data.total
}
const countAdd = () => {
  console.log(111)
  count.value = count.value + 1
  test({pageNum: 1, pageSize: count.value})
}


</script>

<style scoped lang="scss">
.home {
  height: 100%;
  overflow-y: scroll;
  text-align: center;
}
.child {
  width: 40px;
  height: 40px;
  background: #f2f3f5;
  border-radius: 4px;
}
</style>
