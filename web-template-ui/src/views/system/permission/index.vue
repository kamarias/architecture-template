<script setup lang="ts">
import {ref} from "vue";
import {usePermission} from "@/views/system/permission/hook";
import {useRenderIcon} from "@/components/ReIcon/src/hooks";
import Pagination from "@/layout/components/pagination/index.vue";
import Refresh from "@iconify-icons/ep/refresh";
import Search from "@iconify-icons/ep/search";

const formRef = ref();
const {
  form,
  loading,
  dataList,
  onSearch,
  resetForm,
  total,
  changePagination,
  handleDelete,
  openDialog
} = usePermission()
defineOptions({
  name: "Permission"
});
</script>

<template>
  <div class="main">
    <el-form
      ref="formRef"
      :inline="true"
      :model="form"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
    >
      <el-form-item label="权限名称：" prop="name">
        <el-input
          v-model="form.name"
          placeholder="请输入权限名称"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="权限编码：" prop="code">
        <el-input
          v-model="form.code"
          placeholder="请输入权限编码"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon(Search)"
          :loading="loading"
          @click="onSearch"
          v-auth="['system:permission:query']"
        >
          搜索
        </el-button>
        <el-button
          v-auth="['system:permission:query']"
          :icon="useRenderIcon(Refresh)"
          @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row class="mt-5">
      <el-col class="p-1.5 flex justify-end" :span="24">
        <el-button
          v-auth="['system:permission:edit']"
          type="primary"
          @click="openDialog()"
        >
          新增权限
        </el-button>
      </el-col>
    </el-row>
    <el-table
      class="mt-5"
      :data="dataList"
      style="width: 100%"
      :header-cell-style="{background: 'var(--el-table-row-hover-bg-color)',color: 'var(--el-text-color-primary)'}">
      <el-table-column type=index prop="index" align="center" width="55" label="序号"/>
      <el-table-column prop="name" label="权限名称" align="center"/>
      <el-table-column prop="code" label="权限编码" align="center"/>
      <el-table-column prop="description" label="权限描述" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column prop="createBy" label="创建人" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            text
            size="small"
            @click="openDialog('编辑', scope.row)"
            v-auth="['system:permission:edit']"
            type="primary">
            编辑
          </el-button>
          <el-button
            text
            size="small"
            v-auth="['system:permission:del']"
            @click="handleDelete(scope)"
            type="danger">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      v-show="total > 0"
      :total="total"
      :page="form.pageNum"
      :limit="form.pageSize"
      @pagination="changePagination"
    />
  </div>
</template>
