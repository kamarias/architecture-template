import {nextTick, onMounted, reactive, ref, toRaw} from "vue";
import {
  addUser, deleteUser,
  EditUser,
  getUserInfoById,
  getUserList,
  QueryUserForm, resetUserPassWord,
  updateUser,
  updateUserState,
  User
} from "@/api/system/user";
import {isNumber} from "@pureadmin/utils";
import {ElMessageBox, FormInstance} from "element-plus";
import {getRoleOptions} from "@/api/system/role";
import {message} from "@/utils/message";
import {REGEXP_PWD} from "@/views/login/utils/rule";


export function useUser() {

  // 查询 loading
  const queryLoading = ref<boolean>(true);

  // 查询表单
  const queryForm: QueryUserForm = reactive({
    pageNum: 1,
    pageSize: 10
  });

  // 数据集合
  const dataList = ref<Array<User>>([])
  // 数据总数
  const total = ref(0)

  const openEditDialog = ref<boolean>(false)
  const title = ref<string>("新增用户")

  const userForm = reactive<EditUser>({
    account: null,
    email: null,
    gender: 1,
    passWord: null,
    phone: null
  })

  const roleOptions = ref<Array<any>>([])

  const getRoleOptionData = async () => {
    const {data} = await getRoleOptions()
    roleOptions.value = data
  }

  /**
   * 查询列表方法
   */
  const onSearch = async () => {
    queryLoading.value = true;
    const {code, msg, data} = await getUserList(toRaw(queryForm))
    dataList.value = data.list
    total.value = data.total
    queryLoading.value = false;
  }

  /**
   * 重置查询表单
   * @param formEl
   */
  const resetQueryForm = async (formEl) => {
    formEl.resetFields();
    await onSearch();
  }

  const handleAddUser = async () => {
    console.log("添加用户")
    title.value = "添加用户"
    openEditDialog.value = true
  }

  const updateUserStatus = ({row}) => {
    ElMessageBox.confirm(
      `确认要<strong>${
        row.status === 0 ? "停用" : "启用"
      }</strong><strong style='color:var(--el-color-primary)'>${
        row.account
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
      const {data} = await updateUserState({id})
      if (data === true) {
        message(`已${row.status === 0 ? "停用" : "启用"}${row.account}`, {
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


  const handleUpdateUser = async ({row}) => {
    const {id} = row
    const {data} = await getUserInfoById({id})
    title.value = "修改用户"
    openEditDialog.value = true
    await nextTick(() => {
    })
    Object.assign(userForm, data);
  }

  const handleDeleteUser = ({row}) => {
    ElMessageBox.confirm(
      `确定要<strong>删除</strong><strong style='color:var(--el-color-primary)'>${
        (row.account)
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
      const {msg, data} = await deleteUser({id})
      if (data) {
        message(`删除成功`, {
          type: "success"
        });
        await onSearch();
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
    queryForm.pageNum = page
    queryForm.pageSize = limit
    await onSearch()
  }

  const resetUserForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return
    }
    // 重置id，因为id不属于 prop
    userForm.id = undefined
    formEl.resetFields();
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
        if (userForm.id === undefined) {
          const {data, msg} = await addUser(toRaw(userForm))
          if (data) {
            message(`您新增了账号为${(userForm.account)}的这条数据`, {
              type: "success"
            });
            openEditDialog.value = false
            await onSearch();
            return
          }
          message(msg, {
            type: "warning"
          });
        } else {
          const {data, msg} = await updateUser(toRaw(userForm))
          if (data) {
            message(`您更新了账号为${(userForm.account)}的这条数据`, {
              type: "success"
            });
            openEditDialog.value = false
            await onSearch();
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
    openEditDialog.value = false
  }

  const handleResetPassWord = async ({row}) => {
    ElMessageBox.prompt(
      `请输入<strong style='color:var(--el-color-primary)'>${
        (row.account)
      }</strong>的新密码?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        dangerouslyUseHTMLString: true,
        draggable: true,
        closeOnClickModal: false,
        center: true,
        inputPattern: REGEXP_PWD,
        inputErrorMessage: "密码格式应为8-18位数字、字母、符号的任意两种组合",
      }
    ).then(({value}) => {
      resetUserPassWord({
        id: row.id,
        passWord: value
      }).then(({msg, data}) => {
        if (data) {
          message(`重置成功`, {
            type: "success"
          });
          return
        }
        message(msg, {
          type: "error"
        });
      })
    }).catch(()=>{})
  }
  onMounted(async () => {
    await onSearch();
    await getRoleOptionData();
  })

  return {
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
  }

}
