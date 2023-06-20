import {http} from "@/utils/http/request";
import {BooleanResponse, IdForm, IResponse} from "@/utils/http/types";
import {getLoginUserRoleOptions} from "@/api/system/user";

/**
 * 响应数据类型
 */
export type RoleData = {
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
   * 角色名称
   */
  name: string,
  /**
   * 角色编码
   */
  code: string,
  /**
   * 启用状态(1-启用，0-停用)
   */
  status: number,
  /**
   * 角色描述
   */
  description: string
}

export type RoleResult = IResponse & {
  data: {
    list: Array<RoleData>,
    total: number,
    totalPage: number
  };
};

export type RoleDetail = {
  /**
   * 主键Id
   */
  id?: string,
  /**
   * 角色名称
   */
  name: string,
  /**
   * 角色编码
   */
  code: string,
  /**
   * 角色描述
   */
  description?: string

  /**
   * 权限列表
   */
  permissions?: Array<string>
}

export type RoleDetailResult = IResponse & {
  data: RoleDetail
}

export interface RoleOptions {
  /**
   * 主键Id
   */
  id: string,
  /**
   * 角色名称
   */
  name: string,
  /**
   * 角色编码
   */
  code: string,
  /**
   * 角色描述
   */
  description: string
}

export interface RoleOptionsResponse extends IResponse {
  data: Array<RoleOptions>
}

/**
 * 查询角色列表
 * @param data 查询对象
 */
export const getRoleList = (data?: object) => {
  return http.request<RoleResult>("post", "/role/list", {data});
};

/**
 * 更新角色状态
 * @param data 角色id对象
 */
export const updateRoleStatus = (data?: IdForm) => {
  return http.post<IdForm, BooleanResponse>("/role/status", {data});
};

/**
 * 新增角色
 * @param data 新增角色
 */
export const addRole = (data?: RoleDetail) => {
  return http.post<RoleDetail, BooleanResponse>("/role/add", {data});
};

/**
 * 更新角色
 * @param data 更新角色
 */
export const updateRole = (data?: RoleDetail) => {
  return http.put<RoleDetail, BooleanResponse>("/role/update", {data});
};

/**
 * 删除角色
 * @param data 角色id对象
 */
export const deleteRole = (data?: IdForm) => {
  return http.delete<IdForm, BooleanResponse>("/role/delete", {data});
};

/**
 * 查询角色详细详细
 * @param data 角色信息对象
 */
export const getRoleDetail = (data?: IdForm) => {
  return http.post<IdForm, RoleDetailResult>("/role/detail", {data});
};

/**
 * 获取可选的角色列表
 */
export const getRoleOptions = () => {
  return http.get<undefined, RoleOptionsResponse>("/role/options");
}
