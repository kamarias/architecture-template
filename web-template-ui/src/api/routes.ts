import {http} from "@/utils/http/request";
import {IResponse} from "@/utils/http/types";

type Result = IResponse & {
  data: Array<any>;
};

export const getAsyncRoutes = () => {
  return http.request<Result>("get", "/router/getRouters");
};
