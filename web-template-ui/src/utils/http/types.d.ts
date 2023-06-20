import Axios, {
  Method,
  AxiosError,
  AxiosResponse,
  AxiosRequestConfig
} from "axios";
import {User} from "@/api/system/user";

export type resultType = {
  accessToken?: string;
};

export type RequestMethods = Extract<
  Method,
  "get" | "post" | "put" | "delete" | "patch" | "option" | "head"
>;

export interface PureHttpError extends AxiosError {
  isCancelRequest?: boolean;
}

export interface PureHttpResponse extends AxiosResponse {
  config: PureHttpRequestConfig;
}

export interface PureHttpRequestConfig extends AxiosRequestConfig {
  beforeRequestCallback?: (request: PureHttpRequestConfig) => void;
  beforeResponseCallback?: (response: PureHttpResponse) => void;
}

export default class PureHttp {
  request<T>(
    method: RequestMethods,
    url: string,
    param?: AxiosRequestConfig,
    axiosConfig?: PureHttpRequestConfig
  ): Promise<T>;

  post<T, P>(
    url: string,
    params?: T,
    config?: PureHttpRequestConfig
  ): Promise<P>;

  get<T, P>(
    url: string,
    params?: T,
    config?: PureHttpRequestConfig
  ): Promise<P>;
}


/**
 * 接口数据响应类型
 */
export declare interface IResponse {
  code: number
  msg: string
  data?: any
}

/**
 * booleam值数据响应类型
 */
export declare interface BooleanResponse extends IResponse {
  data: boolean
}

/**
 * 表单id接口
 */
export declare interface IdForm {
  id: string
}


export declare interface PageForm {
  pageNum: number,
  pageSize: number
}


export declare interface BaseOperate {
  id: string,
  createBy: string,
  createTime: string,
  updateBy: string,
  updateTime: string,
  delFlag: number,
}

export declare interface PageVO {
  list?: Array<any>,
  total: number,
  totalPage: number,
}
