import {ref} from "vue";


export function usePagination() {
  const small = ref<boolean>(false)
  const background = ref<boolean>(true)

  const layout = ref<string>('total, sizes, prev, pager, next, jumper')
  const pageSizes = ref<Array<number>>([10, 20, 30, 50])
  const pageSize = ref<number>(10)


  return {
    small,
    background,
    layout,
    pageSizes,
    pageSize
  };
}
