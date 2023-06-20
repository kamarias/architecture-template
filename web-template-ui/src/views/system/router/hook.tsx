import {nextTick, onMounted, reactive, ref, toRaw, watch, watchEffect} from "vue";
import {
  addRouterInfo, deleteRouterInfo,
  getRouterInfo,
  getRouterList,
  getRouterSelect,
  updateRouterInfo,
  updateRouterState
} from "@/api/system/router";
import {ElMessageBox, FormInstance} from "element-plus";
import {message} from "@/utils/message";
import {$t, transformI18n} from "@/plugins/i18n";
import {getRoleOptions, RoleOptions, RoleOptionsResponse} from "@/api/system/role";
import {RouterInfo} from "@/api/system/router";

export function useRouter() {

  const loading = ref<boolean>(true);
  const refreshTable = ref<boolean>(true);

  const isExpandAll = ref<boolean>(false);

  const routerTreeData = ref<Array<any>>([]);

  const routerSelectData = ref<Array<any>>([]);

  /**
   * 路由树 prop
   */
  const routerSelectTreeProp = {
    value: 'id',
    label: 'title',
    children: 'children',
  }

  const openDialog = ref<boolean>(false)

  const title = ref<string>("新增路由")

  const routerForm = reactive<RouterInfo>({
    path: "",
    component: "",
    name: "",
    redirect: "",
    type: 0,
    parentRouterId: "0",
    title: "",
    icon: "",
    extraIcon: "",
    showLink: 1,
    showParent: 1,
    keepAlive: 0,
    frameSrc: "",
    frameLoading: 1,
    hiddenTag: 0,
    dynamicLevel: null,
    rank: 0,
    transitionName: "",
    enterTransition: "",
    leaveTransition: "",
    roleIds: [],
  })

  const routerFormRef = ref();

  const roleOptions = ref<Array<RoleOptions>>([])

  const getRouterTreeData = async () => {
    loading.value = true;
    const {data} = await getRouterList();
    routerTreeData.value = data;
    loading.value = false;
  }

  const updateRouterStatus = ({row}) => {
    ElMessageBox.confirm(
      `确认要<strong>${
        row.state === 0 ? "停用" : "启用"
      }</strong><strong style='color:var(--el-color-primary)'>${
        transformI18n(row.title)
      }</strong>吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }).then(async () => {
      const {id} = row
      const {data} = await updateRouterState({id})
      if (data === true) {
        message(`已${row.state === 0 ? "停用" : "启用"}${transformI18n(row.title)}`, {
          type: "success"
        });
        return
      }
      message(`操作失败`, {
        type: "error"
      });
      row.state === 0 ? (row.state = 1) : (row.state = 0);
    }).catch(() => {
      row.state === 0 ? (row.state = 1) : (row.state = 0);
    })
  }

  const toggleExpandAll = async () => {
    refreshTable.value = false;
    isExpandAll.value = !isExpandAll.value;
    await nextTick(() => {
      refreshTable.value = true;
    });
  }

  const getRouterSelectTree = async () => {
    let {data} = await getRouterSelect()
    routerSelectData.value = transformRouterSelectTreeI18n(data)
  }

  /**
   * 路由名称翻译
   * @param data
   */
  function transformRouterSelectTreeI18n(data: Array<any>): Array<any> {
    data.forEach(x => {
      x.title = transformI18n(x.title)
      if (x.children !== null || x.children.length > 0) {
        x.children = transformRouterSelectTreeI18n(x.children)
      }
    })
    return data
  }

  const getRoleOptionData = async () => {
    const {data} = await getRoleOptions()
    roleOptions.value = data
  }

  /**
   * 新增角色
   */
  const handleAddRouter = async (row: any = undefined) => {
    title.value = "新增路由"
    openDialog.value = true
    await nextTick(() => {})
    if (row !== undefined) {
      routerForm.parentRouterId = row.id
    }
  }

  const handleUpdateRouter = async (row: any) => {
    const {id} = row
    const {data} = await getRouterInfo({id})
    title.value = "修改路由"
    openDialog.value = true
    await nextTick(() => {})
    Object.assign(routerForm, data);
  }

  /**
   * 处理提交
   * @param formEl 表单实例
   */
  const handleSubmit = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return
    }
    await formEl.validate(async (valid) => {
      if (valid) {
        if (routerForm.id === undefined) {
          const {data, msg} = await addRouterInfo(toRaw(routerForm))
          if (data){
            message(`您新增了路由名称为${transformI18n(routerForm.title)}的这条数据`, {
              type: "success"
            });
            openDialog.value = false
            await getRouterTreeData();
            await getRouterSelectTree();
            return
          }
          message(msg, {
            type: "warning"
          });
        }else {
          const {data, msg} = await updateRouterInfo(toRaw(routerForm))
          if (data){
            message(`您更新了路由名称为${transformI18n(routerForm.title)}的这条数据`, {
              type: "success"
            });
            openDialog.value = false
            await getRouterTreeData();
            await getRouterSelectTree();
            return
          }
          message(msg, {
            type: "warning"
          });
        }
      }
    })
  }

  /**
   * 处理保存
   * @param formEl 表单实例
   */
  const handleCancel = async (formEl: FormInstance | undefined) => {
    openDialog.value = false
  }

  const resetRouterForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return
    }
    routerForm.id = undefined
    formEl.resetFields();
  }

  /**
   * 路由类型改变监听器
   */
  watch(() => routerForm.type, async (newVal, oldVal) => {
    await nextTick(() => {})
    // 菜单切换目录时触发
    if (oldVal === 1 && newVal === 0){
      routerForm.component = ""
      routerForm.name = ""
      routerForm.redirect = ""
      routerForm.showLink = 1
      routerForm.showParent = 1
      routerForm.keepAlive = 0
      routerForm.frameSrc = ""
      routerForm.frameLoading = 1
      routerForm.hiddenTag = 0
      routerForm.dynamicLevel = null
      routerForm.transitionName = ""
      routerForm.enterTransition = ""
      routerForm.leaveTransition = ""
      routerForm.roleIds = []
    }
  })

  /**
   * 删除路由详细
   */
  const handleDeleteRouter = ({row}) => {
    ElMessageBox.confirm(
      `确定要<strong>删除</strong><strong style='color:var(--el-color-primary)'>${
        transformI18n(row.title)
      }</strong>吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    ).then(async () => {
      const {id} = row
      const {msg, data} = await deleteRouterInfo({id})
      if (data) {
        message(`删除成功`, {
          type: "success"
        });
        await getRouterTreeData();
        await getRouterSelectTree();
        return
      }
      message(msg, {
        type: "error"
      });
    }).catch(() => {
      message(`已取消删除`, {
        type: "warning"
      });
    })
  }


  onMounted(async () => {
    await getRouterTreeData();
    await getRouterSelectTree();
    await getRoleOptionData();
  })

  return {
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
  };
}
