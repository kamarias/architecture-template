// import { http } from "@/utils/http";
import { http } from "@/utils/http/request";
import {IdForm, IResponse} from "@/utils/http/types";

export type LoginResult = IResponse & {
  data: string;
};

/** 登录 */
export const login = (data: object) => {
  return http.request<LoginResult>("post", "/login", { data });
};

export const switchRole = (data: IdForm) => {
  return http.post<IdForm, LoginResult>( "/switchRole", { data });
};

/** 登出 */
export const loginOut = () => {
  return http.request<IResponse>("get", "/loginOut");
};

