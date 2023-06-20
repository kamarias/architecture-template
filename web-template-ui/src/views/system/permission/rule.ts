import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const permissionFormRules = reactive(<FormRules>{
  name: [{ required: true, message: "权限名称为必填项", trigger: "blur" }],
  code: [{ required: true, message: "权限编码为必填项", trigger: "blur" }]
});
