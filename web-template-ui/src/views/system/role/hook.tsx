import {nextTick, onMounted, reactive, ref, toRaw} from "vue";
import {
  addRole, deleteRole,
  getRoleDetail,
  getRoleList,
  RoleData,
  RoleDetail,
  updateRole,
  updateRoleStatus
} from "@/api/system/role";
import {isNumber} from "@pureadmin/utils";
import {ElMessageBox, FormInstance} from "element-plus";
import {message} from "@/utils/message";
import {getAllPermission, PermissionData} from "@/api/system/permission";

export function useRole() {
  const form = reactive({
    name: '',
    code: '',
    status: '',
    permissionCode: '',
    pageNum: 1,
    pageSize: 10
  });
  const loading = ref(true);
  const dataList = ref<Array<RoleData>>([])
  const total = ref(0)


  const openDialog = ref<boolean>(false)
  const title = ref<string>("新增角色")

  const roleForm = reactive<RoleDetail>({
    code: "",
    name: "",
    description: "",
    permissions: []
  })

  const roleFormRef = ref();

  const permissionTreeRef = ref();

  /**
   * 翻页改变
   * @param pages
   */
  async function changePagination(pages: any) {
    let {page, limit} = pages
    if (!isNumber(page)) {
      page = page.value
    }
    if (!isNumber(limit)) {
      limit = limit.value
    }
    form.pageNum = page
    form.pageSize = limit
    await onSearch()
  }

  /**
   * 查询列表
   */
  async function onSearch() {
    loading.value = true;
    const {code, msg, data} = await getRoleList(toRaw(form))
    dataList.value = data.list
    total.value = data.total
    loading.value = false;
  }

  /**
   * 重置表单
   * @param formEl
   */
  const resetForm = async (formEl) => {
    formEl.resetFields();
    await onSearch();
  }

  /**
   * 更新角色状态
   * @param row
   */
  const updateStatus = ({row}) => {
    ElMessageBox.confirm(
      `确认要<strong>${
        row.status === 0 ? "停用" : "启用"
      }</strong><strong style='color:var(--el-color-primary)'>${
        row.name
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
      const {data} = await updateRoleStatus({id})
      if (data === true) {
        message(`已${row.status === 0 ? "停用" : "启用"}${row.name}`, {
          type: "success"
        });
        return
      }
      message(`操作失败`, {
        type: "error"
      });
      row.status === 0 ? (row.status = 1) : (row.status = 0);
    }).catch(() => {
      row.status === 0 ? (row.status = 1) : (row.status = 0);
    })
  }

  /**
   * 权限树 prop
   */
  const permissionTreeProp = {
    value: 'id',
    label: 'name',
    children: 'children',
  }

  /**
   * 权限数据
   */
  const permissionData = ref<Array<PermissionData>>([])

  /**
   * 获取所有权限
   */
  const getPermissionData = () => {
    getAllPermission().then(res => {
      const {data} = res
      permissionData.value = data
    })
  }

  /**
   * 新增角色
   */
  const handleAddRole = () => {
    openDialog.value = true
  }

  /**
   * 编辑角色
   */
  const handleEditRole = async ({row}) => {
    const {data} = await getRoleDetail({id: row.id})
    openDialog.value = true
    await nextTick( () => {})
    Object.assign(roleForm, data);
    setPermissionCheckKey(roleForm.permissions)
  }

  /**
   * 删除角色
   */
  const handleDeleteRole = ({row}) => {
    ElMessageBox.confirm(
      `确定要<strong>删除</strong><strong style='color:var(--el-color-primary)'>${
        row.name
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
      const {msg, data} = await deleteRole({id})
      if (data === true) {
        message(`删除成功`, {
          type: "success"
        });
        await onSearch()
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

  /**
   * 清空权限树的选中状态
   */
  const clearPermissionCheckKey = () => {
    permissionTreeRef.value.setCheckedKeys([]);
  }

  /**
   * 设置权限树的选中状态
   * @param keys 选中的数组
   */
  const setPermissionCheckKey = (keys: Array<string>) => {
    permissionTreeRef.value.setCheckedKeys(keys);
  }

  /**
   * 处理提交
   */
  const handleSubmit = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return
    }
    await formEl.validate(async (valid) => {
      if (valid) {
        roleForm.permissions = permissionTreeRef.value.getCheckedKeys(false)
        if (roleForm.id === undefined) {
          const {data, msg} = await addRole(toRaw(roleForm))
          if (data) {
            message(`您新增了角色名称为${roleForm.name}的这条数据`, {
              type: "success"
            });
            openDialog.value = false
            await onSearch()
            return
          }
          message(msg, {
            type: "warning"
          });
        } else {
          const {data, msg} = await updateRole(toRaw(roleForm))
          if (data) {
            message(`您更新了角色名称为${roleForm.name}的这条数据`, {
              type: "success"
            });
            openDialog.value = false
            await onSearch()
            return
          }
          message(msg, {
            type: "warning"
          });
        }
      }
    })
  }
  const handleCancel = async (formEl: FormInstance | undefined) => {
    openDialog.value = false
  }

  /**
   * 重置新增表单
   * @param formEl
   */
  const resetRoleForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return
    }
    roleForm.id = undefined
    formEl.resetFields();
    clearPermissionCheckKey();
  }

  onMounted(() => {
    onSearch();
    getPermissionData();
  });

  return {
    form,
    loading,
    dataList,
    onSearch,
    resetForm,
    total,
    changePagination,
    updateStatus,
    openDialog,
    title,
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
  };

}


