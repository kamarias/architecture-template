<template>
  <van-pull-refresh
      v-model="refreshing"
      :disabled="vanPullRefreshDisabled"
      success-text="刷新成功"
      :success-duration="1000"
      @refresh="onRefresh"
  >
    <div v-if="empty">
      <slot name="empty">
        <van-empty
            image="https://fastly.jsdelivr.net/npm/@vant/assets/custom-empty-image.png"
            image-size="100"
            description="暂无更多数据"
        />
      </slot>
    </div>
    <div v-else>
      <div :style="`height:`+ scrollHeight + `;`" ref="vatListRef">
        <van-list
            :loading="loading"
            :finished="finished"
            finished-text="已经到底啦~"
            :immediate-check="false"
            @load="handleLoad"
        >
          <div>
            <div v-if="refreshing">
              <!-- 兼容下拉时dom页面不会滚动问题 -->
              <slot name="empty">
                <van-empty
                    image="https://fastly.jsdelivr.net/npm/@vant/assets/custom-empty-image.png"
                    image-size="100"
                    description="暂无更多数据"
                />
              </slot>
            </div>
            <div v-else>
              <slot :data="listData"/>
            </div>
          </div>
        </van-list>
      </div>

    </div>
    <div>
      <slot name="extra">
      </slot>
    </div>
  </van-pull-refresh>
</template>

<script setup lang="ts">
import {computed, defineProps, onMounted, ref} from "vue";

const props = defineProps({
  // 加载函数(请求数据对的函数)
  load: {
    type: Function,
    required: true
  },
  // 滚动高度(css height的值，需要带上单位)
  scrollHeight: {
    type: String,
    required: true
  },
  // 起始翻页num
  startPageNum: {
    type: Number,
    default: 1
  },
  // 是否禁用下拉刷新
  disabledPullRefresh: {
    type: Boolean,
    default: false,
  },
  // 是否开启到顶刷新（既到到列表最上方才刷新，默认值为 false）
  topPullRefresh: {
    type: Boolean,
    default: false,
  },
})

// 下拉加载
const refreshing = ref<boolean>(false)
// 列表加载（上拉加载）
const loading = ref<boolean>(false)
// 加载完成
const finished = ref<boolean>(false)
// 展示的数据
const listData = ref<Array<any>>([])
// 起始翻页
const pageNum = ref<number>(props.startPageNum)
// 监听的dom对象
const vatListRef = ref()
// 禁用下拉刷新
const pullRefreshDisabled = ref<boolean>(false)

const scroll = (event) => {
  pullRefreshDisabled.value = !event.target.scrollTop <= 0;
}

// 挂载时刷新
onMounted(async () => {
  await onRefresh()
  // 存在dom元素，并且需要在顶部才刷新时，加入top监听
  if (vatListRef.value && props.topPullRefresh) {
    vatListRef.value.addEventListener('scroll', scroll, true)
  }
})


// 上拉刷新
const onRefresh = async () => {
  pageNum.value = props.startPageNum
  loading.value = true;
  finished.value = false
  // 最低延时一秒，让刷新更加丝滑
  await Promise.all([
    handleLoad(),
    new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve()
        reject()
      }, 1000)
    })
  ])
  refreshing.value = false
}

// 下拉加载
const handleLoad = async () => {
  const res = await props.load({
    // 当前查询页的参数返回给父组件
    pageNum: pageNum.value
  })
  const slice = Array.isArray(res) ? res : res.data || res.list || res.content || [];
  listData.value = pageNum.value === props.startPageNum ? [...slice] : [...listData.value, ...slice]
  // 最后一次请求
  if (slice.length === 0) {
    finished.value = true
  }
  // 翻页加一
  pageNum.value = pageNum.value + 1
  loading.value = false
}

// 计算数据是否为空
const empty = computed<boolean>(() => {
  return !loading.value && listData.value.length === 0;
})

// 计算是否启动下拉刷新
const vanPullRefreshDisabled = computed<boolean>(() => {
  return props.disabledPullRefresh || pullRefreshDisabled.value;
})

</script>

<style scoped lang="scss">
:deep(.van-pull-refresh) {
  height: calc(100vh - 100px) !important;
  overflow: auto !important;
}

:deep(.van-pull-refresh__head) {
  pointer-events: none !important;
}

:deep(.van-list) {
  height: 100% !important;
  overflow: scroll !important;
}
</style>
