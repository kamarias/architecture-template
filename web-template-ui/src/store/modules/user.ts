import {defineStore} from "pinia";
import {store} from "@/store";
import {routerArrays} from "@/layout/types";
import {resetRouter} from "@/router";
import {storageSession} from "@pureadmin/utils";
import {useMultiTagsStoreHook} from "@/store/modules/multiTags";
import {removeToken, setToken} from "@/utils/token";
import {message} from "@/utils/message";
import {getLoginUserInfo, LoginUserInfo} from "@/api/system/user";
import {RoleOptions} from "@/api/system/role";

export const sessionKey = "user-info";

export const useUserStore = defineStore({
  id: "pure-user",
  state: (): LoginUserInfo => ({
    uuid: storageSession().getItem<LoginUserInfo>(sessionKey)?.uuid ?? "",
    name: storageSession().getItem<LoginUserInfo>(sessionKey)?.name ?? "",
    email: storageSession().getItem<LoginUserInfo>(sessionKey)?.email ?? "",
    permissions: storageSession().getItem<LoginUserInfo>(sessionKey)?.permissions ?? [],
    roles: storageSession().getItem<LoginUserInfo>(sessionKey)?.roles ?? [],
    roleOptions: [],
  }),
  actions: {
    SET_ROLE_OPTIONS(roleOptions: Array<RoleOptions>) {
      this.roleOptions = roleOptions
    },
    /** 存储用户名 */
    SET_USERNAME(username: string) {
      this.username = username;
    },
    /** 存储角色 */
    SET_ROLES(roles: Array<string>) {
      this.roles = roles;
    },
    /** 存储角色 */
    SET_PERMISSIONS(permissions: Array<string>) {
      this.permissions = permissions;
    },
    /**
     * 设置用户信息
     */
    SET_LOGIN_USER_INFO(userInfo: LoginUserInfo){
      this.uuid = userInfo.uuid
      this.name = userInfo.name
      this.permissions = userInfo.permissions
      this.roles = userInfo.roles
      this.email = userInfo.email
    },
    /**
     * 清除用户信息
     */
    CLEAR_LOGIN_USER_INFO(){
      this.uuid = ""
      this.name = ""
      this.email = ""
      this.roles = []
      this.permissions = []
    },
    /**
     * 注入token
     */
    injectToken(token) {
      setToken(token);
    },

    /**
     * 注入用户信息
     */
    async injectUserInfo() {
      const {data} = await getLoginUserInfo()
      this.SET_LOGIN_USER_INFO(data)
      storageSession().setItem(sessionKey,
        data
      );
    },

    /** 认证失败登出接口 */
    async logOut() {
      removeToken();
      this.CLEAR_LOGIN_USER_INFO()
      storageSession().removeItem(sessionKey)
      useMultiTagsStoreHook().handleTags("equal", [...routerArrays]);
      resetRouter();
      message("退出成功", {type: "success"});
      location.href = "/";
    }
  }
});

export function useUserStoreHook() {
  return useUserStore(store);
}
