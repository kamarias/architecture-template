import {ref, onMounted, onUnmounted} from 'vue';

/**
 * 定时任务执行器
 * @param callback 回调函数
 * @param delay 延时时间
 * eg: useInterval(()=>{
 *     console.log("执行了定时任务")
 * }, 1000)
 */
export function useInterval(callback, delay = 1000) {
    const intervalId = ref(null);
    onMounted(() => {
        intervalId.value = setInterval(callback, delay);
    });
    onUnmounted(() => {
        clearInterval(intervalId.value);
    });

}
