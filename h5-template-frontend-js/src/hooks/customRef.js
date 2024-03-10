import {customRef, ref} from 'vue'

/**
 * 防抖ref
 * @param data 防抖数据
 * @param delay  防抖时间
 * eg: let count = debounceRef(1, 500)
 */
export function debounceRef(data, delay = 300) {
    // 创建定时器
    let timer = null;
    return delay == null ? ref(data) : customRef((track, trigger) => {
        return {
            get() {
                track()
                return data
            }, set(value) {
                if (timer != null) {
                    clearTimeout(timer)
                    timer = null
                }
                timer = setTimeout(() => {
                    data = value;
                    trigger()
                }, delay)
            }
        }
    })
}

/**
 * 节流ref
 * @param data 节流数据
 * @param delay 节流时间
 * eg: let count = throttleRef(1, 500)
 */
export function throttleRef(data, delay = 300) {
    let timer = null;
    return delay == null ? ref(data) : customRef((track, trigger) => {
        return {
            get() {
                track()
                return data
            }, set(value) {
                if (timer == null) {
                    timer = setTimeout(() => {
                        data = value;
                        trigger()
                        clearTimeout(timer)
                        timer = null
                    }, delay)
                }
            }
        }
    })
}
