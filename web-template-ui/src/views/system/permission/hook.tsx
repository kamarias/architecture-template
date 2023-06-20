import {h, onMounted, reactive, ref, toRaw} from "vue";
import {isNumber} from "@pureadmin/utils";
import {
  addPermission,
  deletePermission,
  getPermissionList,
  PermissionData,
  updatePermission
} from "@/api/system/permission";
import {ElMessageBox} from "element-plus";
import {message} from "@/utils/message";
import {PermissionForm} from "@/views/system/permission/type";
import editForm from "@/views/system/permission/form.vue";
import {addDialog} from "@/components/ReDialog/index";
// import {addDialog} from "@/components/ReDialog/index.vue";

export function usePermission() {

  const form = reactive({
    name: '',
    code: '',
    pageNum: 1,
    pageSize: 10
  });
  const formRef = ref();
  const loading = ref(true);
  const dataList = ref<Array<PermissionData>>([])
  const total = ref(0)

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
    const {code, msg, data} = await getPermissionList(toRaw(form))
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
   * 处理删除
   * @param row
   */
  function handleDelete({row}) {
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
      const {msg, data} = await deletePermission({id})
      if (data === true) {
        message(`删除成功`, {
          type: "success"
        });
        await onSearch()
        return
      }
      message(`操作失败`, {
        type: "error"
      });
    }).catch(() => {
      message(`已取消删除`, {
        type: "warning"
      });
    })
  }

  const openDialog = (title = '新增', row?: PermissionForm) => {
    addDialog({
      title: `${title}权限`,
      props: {
        formInline: {
          id: row?.id ?? "",
          name: row?.name ?? "",
          code: row?.code ?? "",
          description: row?.description ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, {ref: formRef}),
      beforeSure: (done, {options}) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as PermissionForm;
        function chores() {
          message(`您${title}了权限名称为${curData.name}的这条数据`, {
            type: "success"
          });
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(async (valid) => {
            if (valid) {
              if (curData.id === "") {
                // 实际开发先调用新增接口，再进行下面操作
                const {data} = await addPermission(toRaw(curData))
                if (data) {
                  chores();
                  return
                }
                message(`操作失败`, {
                  type: "error"
                });
              } else {
                // 实际开发先调用编辑接口，再进行下面操作
                const {data} = await updatePermission(toRaw(curData))
                if (data) {
                  chores();
                  return;
                } else {
                  message(`操作失败`, {
                    type: "error"
                  });
                }
              }
            }
          }
        )
      }
    })
  }
  onMounted(async () => {
    await onSearch();
  });
  return {
    form,
    loading,
    dataList,
    onSearch,
    resetForm,
    total,
    changePagination,
    handleDelete,
    openDialog
  };
}
