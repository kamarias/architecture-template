import {reactive} from "vue";
import type {FormRules} from "element-plus";

/** 自定义表单规则校验 */
export const userFormRules = reactive(<FormRules>{
  account: [{required: true, message: "账号为必填项", trigger: "blur"}],
  email: [{required: true, message: "邮箱为必填项", trigger: "blur"}],
  phone: [{required: true, message: "手机号为必填项", trigger: "blur"}],
  passWord: [{required: true, message: "登录密码为必填项", trigger: "blur"}],
  gender: [{required: true, message: "性别为必填项", trigger: "change"}],
});

