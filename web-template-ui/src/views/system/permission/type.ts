interface PermissionForm {
  /**
   * id
   */
  id?: string
  /** 权限名称 */
  name: string;
  /** 权限编号 */
  code: string;
  /** 描述 */
  description: string;
}
interface PermissionFormProps {
  formInline: PermissionForm;
}

export type { PermissionForm, PermissionFormProps };
