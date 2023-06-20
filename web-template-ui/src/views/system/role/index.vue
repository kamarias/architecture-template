<script setup lang="ts">
import {ref} from "vue";
import {useRole} from "@/views/system/role/hook";
import {useRenderIcon} from "@/components/ReIcon/src/hooks";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import Pagination from "@/layout/components/pagination/index.vue";
import {roleFormRules} from "@/views/system/role/rule";
import {hasAuth} from "@/router/utils";

defineOptions({
  name: "Role"
});
const {
  form,
  loading,
  dataList,
  onSearch,
  resetForm,
  total,
  openDialog,
  title,
  changePagination,
  updateStatus,
  roleForm,
  permissionData,
  permissionTreeProp,
  handleAddRole,
  handleEditRole,
  handleDeleteRole,
  roleFormRef,
  permissionTreeRef,
  handleSubmit,
  handleCancel,
  resetRoleForm
} = useRole()

const formRef = ref();

</script>

<template>
  <div class="main">
    <el-form
      ref="formRef"
      :inline="true"
      :model="form"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
    >
      <el-form-item label="角色名称：" prop="name">
        <el-input
          v-model="form.name"
          placeholder="请输入角色名称"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="角色编码：" prop="code">
        <el-input
          v-model="form.code"
          placeholder="请输入角色编码"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-select
          v-model="form.status"
          placeholder="请选择状态"
          clearable
          class="!w-[180px]"
        >
          <el-option label="已启用" value="1"/>
          <el-option label="已停用" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="权限编码：" prop="permissionCode">
        <el-input
          v-model="form.permissionCode"
          placeholder="请输入权限编码"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          v-if="hasAuth('system:role:query')"
          type="primary"
          :icon="useRenderIcon(Search)"
          :loading="loading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button
          v-if="hasAuth(['system:role:query'])"
          :icon="useRenderIcon(Refresh)"
          @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row class="mt-5">
      <el-col class="p-1.5 flex justify-end" :span="24">
        <el-button
          v-if="hasAuth(['system:role:add'])"
          type="primary"
          @click="handleAddRole"
        >
          新增角色
        </el-button>
      </el-col>
    </el-row>
    <el-table
      class="mt-5"
      :data="dataList"
      style="width: 100%"
      :header-cell-style="{background: 'var(--el-table-row-hover-bg-color)',color: 'var(--el-text-color-primary)'}">
      <el-table-column type=index prop="index" align="center" width="55" label="序号"/>
      <el-table-column prop="name" label="角色名称" align="center"/>
      <el-table-column prop="code" label="角色编码" align="center"/>
      <el-table-column prop="description" label="角色描述" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column prop="createBy" label="创建人" align="center"/>
      <Auth :value="['system:role:status']">
        <el-table-column prop="status" label="状态" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              size="large"
              :active-value="1"
              :inactive-value="0"
              inline-prompt
              active-text="启用"
              inactive-text="停用"
              @click="updateStatus(scope)"
            />
          </template>
        </el-table-column>
      </Auth>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleEditRole(scope)"
            v-if="hasAuth(['system:role:edit'])"
          >
            编辑
          </el-button>
          <el-button
            size="small"
            type="danger"
            v-if="hasAuth(['system:role:del'])"
            @click="handleDeleteRole(scope)">
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

    <el-dialog
      v-model="openDialog"
      :title="title"
      width="40%"
      draggable
      close-on-click-modal
      @close="resetRoleForm(roleFormRef)"
      align-center
      center>
      <el-form
        ref="roleFormRef"
        :model="roleForm"
        :rules="roleFormRules"
        label-width="82px"
      >
        <el-row class="mb-4">
          <span class="form-title-line">角色信息</span>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="角色名称" prop="name">
              <el-input
                v-model="roleForm.name"
                clearable
                placeholder="请输入角色名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色标识" prop="code">
              <el-input
                v-model="roleForm.code"
                clearable
                placeholder="请输入角色标识"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="description">
              <el-input
                v-model="roleForm.description"
                placeholder="请输入备注信息"
                type="textarea"
              />
            </el-form-item>
          </el-col>
        </el-row>


        <el-row class="mb-4">
          <span class="form-title-line">角色权限</span>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-tree-v2
              ref="permissionTreeRef"
              :data="permissionData"
              :props="permissionTreeProp"
              show-checkbox
              :height="208">
              <template #default="{ node,data }">
                <el-row class="tree-node-content-box flex justify-between">
                  <el-col :span="6">
                    <span>{{ data.name }}</span>
                  </el-col>
                  <el-col :span="6">
                    <span>{{ data.code }}</span>
                  </el-col>
                  <el-col :span="12" class="flex justify-center">
                    <span>{{ data.description }}</span>
                  </el-col>
                </el-row>
              </template>
            </el-tree-v2>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit(roleFormRef)">
          保存
        </el-button>
        <el-button @click="handleCancel(roleFormRef)">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped lang="scss">
.tree-node-content-box {
  width: 100%;

  span {
    width: 90%;
    height: 100%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis
  }
}


</style>
