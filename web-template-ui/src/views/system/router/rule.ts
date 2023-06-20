import {reactive} from "vue";
import type {FormRules} from "element-plus";

/** 自定义表单规则校验 */
export const routerFormRules = reactive(<FormRules>{
  title: [{required: true, message: "路由名称为必填项", trigger: "blur"}],
  path: [{required: true, message: "路由路径为必填项", trigger: "blur"}],
  rank: [{required: true, message: "路由排序为必填项", trigger: "blur"}],
  description: [{required: false}],
});
