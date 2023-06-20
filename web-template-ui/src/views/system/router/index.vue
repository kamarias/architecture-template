<script setup lang="ts">
import {useRouter} from "@/views/system/router/hook";
import {useRenderIcon} from "@/components/ReIcon/src/hooks";
import {toRaw} from "vue";
import {routerFormRules} from "@/views/system/router/rule";
import exclamationMarkIcon from "@/assets/svg/exclamation_mark.svg?component";

const {
  loading,
  routerTreeData,
  updateRouterStatus,
  isExpandAll,
  refreshTable,
  toggleExpandAll,
  routerSelectData,
  openDialog,
  title,
  handleAddRouter,
  handleUpdateRouter,
  routerForm,
  routerFormRef,
  routerSelectTreeProp,
  roleOptions,
  handleSubmit,
  handleCancel,
  resetRouterForm,
  handleDeleteRouter
} = useRouter()

defineOptions({
  name: "Router"
});
</script>

<template>
  <div class="main">
    <el-row class="mt-5">
      <el-col class="p-1.5 flex justify-end" :span="24">
        <el-button
          type="primary"
          @click="toggleExpandAll"
        >
          {{ isExpandAll ? '全部折叠' : '全部展开' }}
        </el-button>
        <Auth :value="['system:router:add']">
          <el-button
            type="primary"
            @click="handleAddRouter()"
          >
            新增路由
          </el-button>
        </Auth>
      </el-col>
    </el-row>
    <el-table
      v-if="refreshTable"
      :default-expand-all="isExpandAll"
      :data="routerTreeData"
      v-loading="loading"
      row-key="id"
      style="width: 100%"
      :header-cell-style="{background: 'var(--el-table-row-hover-bg-color)',color: 'var(--el-text-color-primary)'}"
    >
      <el-table-column prop="title"
                       label="路由名称"
                       :show-overflow-tooltip="true"
                       width="200">
        <template #default="scope">
          <span>{{ $t(scope.row.title) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="icon"
                       width="60"
                       label="图标" align="center">
        <template #default="scope">
          <div
            v-if="toRaw(scope.row.icon)"
            class="sub-menu-icon">
            <component :is="useRenderIcon(toRaw(scope.row.icon))"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="rank"
                       label="排序"
                       :show-overflow-tooltip="true"
                       align="center"/>
      <el-table-column prop="path"
                       label="路由路径"
                       :show-overflow-tooltip="true"
                       align="center"/>
      <el-table-column prop="redirect"
                       :show-overflow-tooltip="true"
                       label="重定向地址"
                       align="center"/>
      <el-table-column prop="component"
                       label="组件路径"
                       :show-overflow-tooltip="true"
                       align="center"/>
      <Auth :value="['system:router:status']">
        <el-table-column prop="state" label="状态" width="100" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.state"
              size="large"
              :active-value="1"
              :inactive-value="0"
              inline-prompt
              active-text="启用"
              inactive-text="停用"
              @click="updateRouterStatus(scope)"
            />
          </template>
        </el-table-column>
      </Auth>
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <Auth :value="['system:router:add']">
            <el-button
              v-show="scope.row.type === 0"
              link
              type="primary"
              @click="handleAddRouter(scope.row)"
            >新增
            </el-button>
          </Auth>
          <Auth :value="['system:router:edit']">
            <el-button
              link
              type="success"
              @click="handleUpdateRouter(scope.row)"
            >修改
            </el-button>
          </Auth>
          <Auth :value="['system:router:del']">
            <el-button
              link
              type="danger"
              @click="handleDeleteRouter(scope)"
            >删除
            </el-button>
          </Auth>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-model="openDialog"
      :title="title"
      width="40%"
      draggable
      close-on-click-modal
      align-center
      center
      @close="resetRouterForm(routerFormRef)"
    >
      <el-form
        ref="routerFormRef"
        :model="routerForm"
        :rules="routerFormRules"
        label-width="82px"
      >
        <el-row class="mb-4">
          <span class="form-title-line">路由信息</span>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级路由" prop="parentRouterId">
              <el-tree-select
                :props="routerSelectTreeProp"
                v-model="routerForm.parentRouterId"
                :data="routerSelectData"
                check-strictly
                :render-after-expand="false"
                class="w-11/12"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="路由类型" prop="type">
              <el-radio-group v-model="routerForm.type">
                <el-radio :label="0">目录</el-radio>
                <el-radio :label="1">菜单</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="路由路径" prop="path">
              <el-input v-model="routerForm.path"
                        placeholder="请输入路由路径"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11" v-if="routerForm.type === 1">
            <el-form-item label="重定向地址" prop="redirect">
              <el-input v-model="routerForm.redirect"
                        placeholder="请输入重定向地址"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="routerForm.component"
                        placeholder="请输入组件路径"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="组件名称" prop="name">
              <el-input v-model="routerForm.name"
                        placeholder="请输入组件名称"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="mb-4">
          <span class="form-title-line">路由源数据</span>
        </el-row>

        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="路由名称" prop="title">
              <el-input v-model="routerForm.title"
                        placeholder="请输入路由名称"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="路由排序" prop="rank">
              <el-input-number
                v-model="routerForm.rank"
                controls-position="right"
                :min="0"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row class="flex justify-between">
          <el-col :span="11">
            <el-form-item label="路由图标" prop="icon">
              <el-input v-model="routerForm.icon"
                        placeholder="请输入路由图标"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="extraIcon">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>拓展图标</span>
                  </span>
              </template>
              <el-input v-model="routerForm.extraIcon"
                        placeholder="请输入拓展图标"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item prop="showLink">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>路由显示</span>
                  </span>
              </template>
              <el-radio-group v-model="routerForm.showLink">
                <el-radio :label="1">显示</el-radio>
                <el-radio :label="0">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label-width="100" prop="showParent">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>父目录显示</span>
                  </span>
              </template>
              <el-radio-group v-model="routerForm.showParent">
                <el-radio :label="1">显示</el-radio>
                <el-radio :label="0">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item prop="keepAlive">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>是否缓存</span>
                  </span>
              </template>
              <el-radio-group v-model="routerForm.keepAlive">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="hiddenTag">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>隐藏页签</span>
                  </span>
              </template>
              <el-radio-group v-model="routerForm.hiddenTag">
                <el-radio :label="0">否</el-radio>
                <el-radio :label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item prop="frameSrc">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="内嵌的弹窗页面的链接"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>内窗链接</span>
                  </span>
              </template>
              <el-input v-model="routerForm.frameSrc"
                        placeholder="请输入内嵌弹窗链接"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label-width="100" prop="dynamicLevel">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>动态路由数</span>
                  </span>
              </template>
              <el-input-number
                v-model="routerForm.dynamicLevel"
                controls-position="right"
                :min="1"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="mb-4" v-if="routerForm.type === 1">
          <span class="form-title-line">路由过度动画</span>
        </el-row>
        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item label="动画名称" prop="transitionName">
              <el-input v-model="routerForm.transitionName"
                        placeholder="请输入动画名称"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="frameLoading">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="内嵌的弹窗页面是否开启首次加载动画"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>内窗动画</span>
                  </span>
              </template>
              <el-radio-group v-model="routerForm.frameLoading">
                <el-radio :label="1">开启</el-radio>
                <el-radio :label="0">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="flex justify-between" v-if="routerForm.type === 1">
          <el-col :span="11">
            <el-form-item prop="enterTransition">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="路由组件进场动画，取值可参考：https://animate.style"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>进场动画</span>
                  </span>
              </template>
              <el-input v-model="routerForm.enterTransition"
                        placeholder="请输入进场动画"
                        clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="离场动画" prop="leaveTransition">
              <template #label>
                  <span class="flex items-center">
                    <el-tooltip
                      content="路由组件离场动画，取值可参考：https://animate.style"
                      placement="top">
                      <el-icon>
                        <component :is="useRenderIcon(exclamationMarkIcon)"/>
                      </el-icon>
                    </el-tooltip>
                    <span>离场动画</span>
                  </span>
              </template>
              <el-input v-model="routerForm.leaveTransition"
                        placeholder="请输入离场动画"
                        clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="mb-4" v-if="routerForm.type === 1">
          <span class="form-title-line">路由角色</span>
        </el-row>
        <el-row v-if="routerForm.type === 1">
          <el-col :span="24">
            <el-form-item label="路由角色" prop="roleIds" class="el-select-box">
              <el-select
                v-model="routerForm.roleIds"
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
        <el-button type="primary" @click="handleSubmit(routerFormRef)">
          保 存
        </el-button>
        <el-button @click="handleCancel(routerFormRef)">
          取 消
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped lang="scss">
:deep("el-select") {
  width: 100%;
}


</style>
