import {http} from "@/utils/http/request";
import {BooleanResponse, IdForm, IResponse} from "@/utils/http/types";
import {PermissionForm} from "@/views/system/permission/type";

/**
 * 响应数据类型
 */
export type PermissionData = {
  /**
   * 主键Id
   */
  id: string,
  /**
   * 创建人
   */
  createBy: string,
  /**
   * 创建时间
   */
  createTime: string,
  /**
   * 更新人
   */
  updateBy: string,
  /**
   * 更新时间
   */
  updateTime: string,
  /**
   * 删除状态(1-删除，0-未删除)
   */
  delFlag: number,
  /**
   * 权限名称
   */
  name: string,
  /**
   * 权限编码
   */
  code: string,

  /**
   * 权限描述
   */
  description: string
}

export type PermissionResult = IResponse & {
  data: {
    list: Array<PermissionData>,
    total: number,
    totalPage: number
  };
};

export type PermissionResultResponse = IResponse & {
  data: Array<PermissionData>
}

/**
 * 查询权限列表
 * @param data 查询对象
 */
export const getPermissionList = (data?: object) => {
  return http.request<PermissionResult>("post", "/permission/list", {data});
};


/**
 * 新增权限
 * @param data 新增权限
 */
export const addPermission = (data?: PermissionForm) => {
  return http.post<PermissionForm, BooleanResponse>("/permission/add", {data});
};

/**
 * 更新角色
 * @param data? 更新角色
 */
export const updatePermission = (data?: PermissionForm) => {
  return http.put<PermissionForm, BooleanResponse>("/permission/update", {data});
};

/**
 * 删除角色
 * @param data 角色id对象
 */
export const deletePermission = (data?: IdForm) => {
  return http.delete<IdForm, BooleanResponse>("/permission/delete", {data});
};


/**
 * 删除角色
 * @param data 角色id对象
 */
export const getAllPermission = () => {
  return http.post<undefined, PermissionResultResponse>("/permission/listAll");
};
