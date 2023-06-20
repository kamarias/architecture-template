<script setup lang="ts">
import {useRenderIcon} from "@/components/ReIcon/src/hooks";
import {ref} from "vue";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import {useUser} from "@/views/system/user/hook";
import Pagination from "@/layout/components/pagination/index.vue";
import {userFormRules} from "@/views/system/user/rule";

defineOptions({
  name: "User"
});
const {
  queryForm,
  queryLoading,
  dataList,
  total,
  onSearch,
  resetQueryForm,
  handleAddUser,
  updateUserStatus,
  handleUpdateUser,
  handleDeleteUser,
  changePagination,
  title,
  openEditDialog,
  resetUserForm,
  userForm,
  roleOptions,
  handleSubmit,
  handleCancel,
  handleResetPassWord
} = useUser()

const queryFormRef = ref();
const userFormRef = ref();
</script>

<template>
  <div class="main">
    <el-form
      ref="queryFormRef"
      :inline="true"
      :model="queryForm"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
    >
      <el-form-item label="昵称：" prop="nickName">
        <el-input
          v-model="queryForm.nickName"
          placeholder="请输入昵称"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="账号：" prop="account">
        <el-input
          v-model="queryForm.account"
          placeholder="请输入账号"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input
          v-model="queryForm.email"
          placeholder="请输入邮箱"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input
          v-model="queryForm.phone"
          placeholder="请输入手机号"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-select
          v-model="queryForm.status"
          placeholder="请选择状态"
          clearable
          class="!w-[180px]"
        >
          <el-option label="已启用" :value="1"/>
          <el-option label="已停用" :value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <Auth :value="['system:user:query']">
          <el-button
            type="primary"
            :icon="useRenderIcon(Search)"
            :loading="queryLoading"
            @click="onSearch"
          >
            搜索
          </el-button>
        </Auth>
        <Auth :value="['system:user:query']">
          <el-button
            :icon="useRenderIcon(Refresh)"
            @click="resetQueryForm(queryFormRef)">
            重置
          </el-button>
        </Auth>
      </el-form-item>
    </el-form>

    <el-row class="mt-5">
      <el-col class="p-1.5 flex justify-end" :span="24">
        <Auth :value="['system:user:add']">
          <el-button
            type="primary"
            @click="handleAddUser"
          >
            新增用户
          </el-button>
        </Auth>
      </el-col>
    </el-row>
    <el-table
      class="mt-5"
      :data="dataList"
      style="width: 100%"
      v-loading="queryLoading"
      :header-cell-style="{background: 'var(--el-table-row-hover-bg-color)',color: 'var(--el-text-color-primary)'}">
      <el-table-column type=index prop="index" align="center" width="55" label="序号"/>
      <el-table-column prop="nickName" label="昵称" align="center"/>
      <el-table-column prop="account" label="账号" align="center"/>
      <el-table-column prop="email" label="邮箱" align="center"/>
      <el-table-column prop="phone" label="电话号码" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <Auth :value="['system:user:status']">
        <el-table-column label="状态" width="100" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              size="large"
              :active-value="1"
              :inactive-value="0"
              inline-prompt
              active-text="启用"
              inactive-text="停用"
              @click="updateUserStatus(scope)"
            />
          </template>
        </el-table-column>
      </Auth>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <Auth :value="['system:user:edit']">
            <el-button
              link
              type="primary"
              @click="handleUpdateUser(scope)"
            >编辑
            </el-button>
          </Auth>
          <Auth :value="['system:user:resetPwd']">
            <el-button
              link
              type="warning"
              @click="handleResetPassWord(scope)"
            >重置密码
            </el-button>
          </Auth>
          <Auth :value="['system:user:del']">
            <el-button
              link
              type="danger"
              @click="handleDeleteUser(scope)"
            >删除
            </el-button>
          </Auth>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      v-show="total > 0"
      :total="total"
      :page="queryForm.pageNum"
      :limit="queryForm.pageSize"
      @pagination="changePagination"
    />

    <el-dialog
      v-model="openEditDialog"
      :title="title"
      width="40%"
      draggable
      close-on-click-modal
      @close="resetUserForm(userFormRef)"
      align-center
      center>
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userFormRules"
        label-width="82px"
      >
        <el-row class="mb-4">
          <span class="form-title-line">用户信息</span>
        </el-row>
        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="userForm.nickName"
                        placeholder="请输入昵称"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="账号" prop="account">
              <el-input v-model="userForm.account"
                        placeholder="请输入账号"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userForm.email"
                        placeholder="请输入邮箱"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userForm.phone"
                        placeholder="请输入手机号"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="userForm.gender">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="密码" prop="passWord" v-show="userForm.id === undefined">
              <el-input v-model="userForm.passWord"
                        placeholder="请输入密码"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述"  prop="remark">
              <el-input
                v-model="userForm.remark"
                placeholder="请输入权限描述"
                type="textarea"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="mb-4">
          <span class="form-title-line">角色信息</span>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="用户角色" prop="roleIds" class="el-select-box">
              <el-select
                v-model="userForm.roleIds"
                multiple
                placeholder="请选择需要分配的角色"
                style="width: 100%;">
                <el-option v-for="item in roleOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit(userFormRef)">
          保 存
        </el-button>
        <el-button @click="handleCancel(userFormRef)">
          取 消
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>
