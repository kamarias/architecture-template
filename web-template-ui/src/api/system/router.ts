import {http} from "@/utils/http/request";
import {BooleanResponse, IdForm, IResponse} from "@/utils/http/types";


type RouterTree = IResponse & {
  data: Array<any>;
};

/**
 * 路由详细信息实体
 */
export interface RouterInfo {
  id?: string
  path: string
  component: string
  name: string
  redirect?: string
  type: number
  parentRouterId: string
  title: string
  icon?: string
  extraIcon?: string
  showLink?: number
  showParent: number
  keepAlive?: number
  frameSrc?: string
  frameLoading?: number
  hiddenTag?: number
  dynamicLevel?: number
  rank?: number
  transitionName?: string
  enterTransition?: string
  leaveTransition?: string
  roleIds?: Array<string>
}

type RouterInfoResponse = IResponse & {
  data: RouterInfo;
};

/**
 * 获取路由列表
 */
export const getRouterList = () => {
  return http.request<RouterTree>("get", "/router/list");
};

/**
 * 获取路由选择树
 */
export const getRouterSelect = () => {
  return http.request<RouterTree>("get", "/router/getRouterSelect");
};

/**
 * 更新路由状态
 * @param data 路由id对象
 */
export const updateRouterState = (data?: IdForm) => {
  return http.post<IdForm, BooleanResponse>("/router/state", {data});
};


/**
 * 添加路由信息
 * @param data 路由信息对象
 */
export const addRouterInfo = (data?: RouterInfo) => {
  return http.post<RouterInfo, BooleanResponse>("/router/add", {data});
};

/**
 * 更新路由信息
 * @param data 路由信息对象
 */
export const updateRouterInfo = (data?: RouterInfo) => {
  return http.put<RouterInfo, BooleanResponse>("/router/update", {data});
};

/**
 * 删除路由信息
 * @param data 路由id对象
 */
export const deleteRouterInfo = (data?: IdForm) => {
  return http.delete<IdForm, BooleanResponse>("/router/delete", {data});
};

/**
 * 获取路由详细详细
 * @param data 路由id对象
 */
export const getRouterInfo = (data?: IdForm) => {
  return http.post<IdForm, RouterInfoResponse>("/router/getRouterInfo", {data});
};
