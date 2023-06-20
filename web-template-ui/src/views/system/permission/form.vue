<script setup lang="ts">
import {
  ref,
  defineProps
} from "vue";
import {PermissionFormProps} from "@/views/system/permission/type";
import {permissionFormRules} from "@/views/system/permission/rule";


const props = withDefaults(defineProps<PermissionFormProps>(), {
  formInline: () => ({
    name: "",
    code: "",
    description: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({getRef});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="permissionFormRules"
    label-width="82px"
  >
    <el-form-item label="权限名称" prop="name">
      <el-input
        v-model="newFormInline.name"
        clearable
        placeholder="请输入权限名称"
      />
    </el-form-item>

    <el-form-item label="权限编码" prop="code">
      <el-input
        v-model="newFormInline.code"
        clearable
        placeholder="请输入权限编码"
      />
    </el-form-item>

    <el-form-item label="描述">
      <el-input
        v-model="newFormInline.description"
        placeholder="请输入权限描述"
        type="textarea"
      />
    </el-form-item>
  </el-form>
</template>
