

type Recordable<T = any> = Record<string, T>;

type ViteCompression =
  | "none"
  | "gzip"
  | "brotli"
  | "both"
  | "gzip-clear"
  | "brotli-clear"
  | "both-clear";


interface ViteEnv {
  // 平台本地运行端口
  VITE_PORT: number;

  // 激活环境
  VITE_ENV: string;

  // 是否打开 vconsole
  VITE_V_CONSOLE: boolean;

  // 接口请求地址
  VITE_BASE_API: string;

  // 加解密开关
  VITE_CYPTO: boolean;

  // 服务授权 APP Key
  VITE_APP_KEY: string

  // 服务授权 APP Secret
  VITE_APP_SECRET: string

  // 配置文件路径
  VITE_PUBLIC_PATH: string;

  // 路由模式
  VITE_ROUTER_HISTORY: string;

  // 是否启用CDN
  VITE_CDN: boolean;

  // 打包压缩模式
  VITE_COMPRESSION: ViteCompression;
}

/** 处理环境变量 */
const wrapperEnv = (envConf: Recordable): ViteEnv => {
  /** 此处为默认值 */
  const ret: ViteEnv = {
    VITE_PORT: 80,
    VITE_ENV: "development",
    VITE_V_CONSOLE: true,
    VITE_BASE_API: "http://localhost:9000",
    VITE_CYPTO: false,
    VITE_APP_KEY: "",
    VITE_APP_SECRET: "",
    VITE_PUBLIC_PATH: "/",
    VITE_ROUTER_HISTORY: "hash",
    VITE_CDN: false,
    VITE_COMPRESSION: "none"
  };
  for (const envName of Object.keys(envConf)) {
    let realName = envConf[envName].replace(/\\n/g, "\n");
    realName =
      realName === "true" ? true : realName === "false" ? false : realName;

    if (envName === "VITE_PORT") {
      realName = Number(realName);
    }
    ret[envName] = realName;
    if (typeof realName === "string") {
      process.env[envName] = realName;
    } else if (typeof realName === "object") {
      process.env[envName] = JSON.stringify(realName);
    }
  }
  return ret;
};

export { wrapperEnv };
