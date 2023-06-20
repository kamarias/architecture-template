// 相关配置请参考：www.axios-js.com/zh-cn/docs/#axios-request-config-1
import Axios, {
  AxiosInstance,
  AxiosRequestConfig,
  CustomParamsSerializer,
  AxiosRequestHeaders,
  AxiosResponse
} from "axios";
import {stringify} from "qs";
import NProgress from "@/utils/progress";
import {formatToken, getToken} from "@/utils/token";
import {IResponse, PureHttpRequestConfig, RequestMethods} from "./types.d";
import {useUserStoreHook} from "@/store/modules/user";
import {message} from "@/utils/message";
import {Action, ElMessageBox} from "element-plus";

const defaultConfig: AxiosRequestConfig = {
  baseURL: "http://localhost:8990",
  // 请求超时时间
  timeout: 10000,
  headers: {
    Accept: "application/json, text/plain, */*",
    "Content-Type": "application/json",
    "X-Requested-With": "XMLHttpRequest"
  },
  // 数组格式参数序列化（https://github.com/axios/axios/issues/5142）
  paramsSerializer: {
    serialize: stringify as unknown as CustomParamsSerializer
  }
};

class Http {
  constructor() {
    this.httpInterceptorsRequest();
    this.httpInterceptorsResponse();
  }

  /** 保存当前Axios实例对象 */
  private static axiosInstance: AxiosInstance = Axios.create(defaultConfig);

  /**
   * 请求错误处理方法
   * @param error 错误信息
   */
  private static errorHandler: (error) => Promise<never> = (error) => {
    let {message} = error;
    if (message == "Network Error") {
      message = "网络异常，请稍后再试";
    }
    if (message.includes("timeout")) {
      message = "接口请求超时";
    }
    if (message.includes("Request failed with status code")) {
      message = "请求接口" + message.substr(message.length - 3) + "异常";
    }
    return Promise.reject(error);
  };

  /**
   * token 过期处理方法
   */
  private static tokenExpiredHandler = async () => {
    await ElMessageBox.alert(
      '登录已过期，请重新登录',
      '警告',
      {
        confirmButtonText: '确认',
        type: 'warning',
        draggable: true,
        showClose: false,
        callback: async (action: Action) => {
          await useUserStoreHook().logOut();
        },
      },
    )
  }

  /**
   * 请求拦截方法
   */
  private httpInterceptorsRequest(): void {
    // 开启进度条动画
    NProgress.start();
    const instance = Http.axiosInstance;
    instance.interceptors.request.use(async config => {
      const headers = {...config.headers} as AxiosRequestHeaders;
      const {url} = config;
      const token = getToken();
      if (token === undefined && url != "/login") {
        await useUserStoreHook().logOut();
      }
      headers.Authorization = formatToken(token);
      return {...config, headers};
    }, Http.errorHandler);
  }

  /**
   * 响应拦截方法
   */
  private httpInterceptorsResponse(): void {
    const instance = Http.axiosInstance;
    instance.interceptors.response.use(
      async (response: AxiosResponse<IResponse>): Promise<any> => {
        // 关闭进度条动画
        NProgress.done();
        const {data} = response;
        // 返回401，并且msg包含认证失败代表，token过期
        if (data.code === 401 && data.msg.includes("认证失败")) {
          await Http.tokenExpiredHandler()
          return Promise.reject(data.msg);
        }
        // 返回值不等于 200 存在错误统一拦截
        if (data.code !== 200) {
          message(data.msg, {
            type: "error"
          });
          return Promise.reject(data.msg);
        }
        return Promise.resolve(data);
      },
      Http.errorHandler
    );
  }

  /** 通用请求工具函数 */
  public request<T>(
    method: RequestMethods,
    url: string,
    param?: AxiosRequestConfig,
    axiosConfig?: PureHttpRequestConfig
  ): Promise<T> {
    const config = {
      method,
      url,
      ...param,
      ...axiosConfig
    } as PureHttpRequestConfig;

    // 单独处理自定义请求/响应回掉
    return new Promise((resolve, reject) => {
      Http.axiosInstance
        .request(config)
        .then((response: undefined) => {
          resolve(response);
        })
        .catch(error => {
          reject(error);
        });
    });
  }

  /** 单独抽离的post工具函数 */
  public post<T, P>(
    url: string,
    params?: AxiosRequestConfig<T>,
    config?: PureHttpRequestConfig
  ): Promise<P> {
    return this.request<P>("post", url, params, config);
  }

  /** 单独抽离的get工具函数 */
  public get<T, P>(
    url: string,
    params?: AxiosRequestConfig<T>,
    config?: PureHttpRequestConfig
  ): Promise<P> {
    return this.request<P>("get", url, params, config);
  }

  /** 单独抽离的get工具函数 */
  public put<T, P>(
    url: string,
    params?: AxiosRequestConfig<T>,
    config?: PureHttpRequestConfig
  ): Promise<P> {
    return this.request<P>("put", url, params, config);
  }

  /** 单独抽离的get工具函数 */
  public delete<T, P>(
    url: string,
    params?: AxiosRequestConfig<T>,
    config?: PureHttpRequestConfig
  ): Promise<P> {
    return this.request<P>("delete", url, params, config);
  }
}

export const http = new Http();
