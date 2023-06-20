import {http} from "@/utils/http/request";
import {BaseOperate, BooleanResponse, IdForm, IResponse, PageForm, PageVO} from "@/utils/http/types";
import {RoleOptions, RoleOptionsResponse} from "@/api/system/role";


export interface LoginUserInfo {
  uuid: string,

  name: string,

  email: string,

  permissions: Array<string>,

  roles: Array<string>,

  roleOptions: Array<RoleOptions>
}

/**
 * 登录用户信息返回类型
 */
type LoginUserInfoResponse = IResponse & {
  data: LoginUserInfo
}

/**
 * 查询用户表单类型
 */
export interface QueryUserForm extends PageForm {
  nickName?: string,
  account?: string,
  email?: string,
  phone?: string,
  status?: number,
}

export interface User extends BaseOperate {
  nickName: string,
  account: string,
  email: string,
  phone: string,
  passWord: string,
  avatar: string,
  gender: number,
  birthday: string,
  status: number,
  remark: string,
}

export interface PageUser extends PageVO {
  list: Array<User>
}

export interface UserListResponse extends IResponse {
  data: PageUser
}

export interface EditUser {
  id?: string,
  nickName?: string,
  account: string,
  email: string,
  phone: string,
  passWord: string,
  avatar?: string,
  gender: number,
  remark?: string,
  roleIds?: Array<string>,
}

export interface EditUserResponse extends IResponse {
  data: EditUser
}

export interface ResetPassWordForm{
  id: string,
  passWord: string
}

/**
 * 获取登录的用户信息
 */
export const getLoginUserInfo = () => {
  return http.request<LoginUserInfoResponse>("post", "/user/getLoginUserInfo");
};


export const getUserList = (data: QueryUserForm) => {
  return http.post<QueryUserForm, UserListResponse>(
    "/user/list",
    {data}
  );
}

export const addUser = (data: EditUser) => {
  return http.post<EditUser, BooleanResponse>(
    "/user/add",
    {data}
  );
}

export const updateUser = (data: EditUser) => {
  return http.put<EditUser, BooleanResponse>(
    "/user/update",
    {data}
  );
}

export const updateUserState = (data: IdForm) => {
  return http.post<IdForm, BooleanResponse>(
    "/user/state",
    {data}
  );
}

export const deleteUser = (data: IdForm) => {
  return http.delete<IdForm, BooleanResponse>(
    "/user/delete",
    {data}
  );
}

export const getUserInfoById = (data: IdForm) => {
  return http.post<IdForm, EditUserResponse>(
    "/user/getUserInfo",
    {data}
  );
}


export const resetUserPassWord = (data: ResetPassWordForm) => {
  return http.post<ResetPassWordForm, BooleanResponse>(
    "/user/resetPassWord",
    {data}
  );
}


/**
 * 获取可选的角色列表
 */
export const getLoginUserRoleOptions = () => {
  return http.get<undefined, RoleOptionsResponse>("/user/getLoginUserRole");
}
