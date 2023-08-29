import {ConfigEnv, loadEnv, UserConfigExport} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from "@vitejs/plugin-vue-jsx";
import legacy from '@vitejs/plugin-legacy';
import vueSetupExtend from 'vite-plugin-vue-setup-extend';
import Components from 'unplugin-vue-components/vite';
import {VantResolver} from 'unplugin-vue-components/resolvers';
import {viteVConsole} from 'vite-plugin-vconsole';
import AutoImport from "unplugin-auto-import/vite";
import viteSentry from 'vite-plugin-sentry';
import styleImport, {createStyleImportPlugin, VantResolve} from 'vite-plugin-style-import';
import compressPlugin from "vite-plugin-compression";


import {resolve} from "path";

import {visualizer} from "rollup-plugin-visualizer";
import {Plugin} from "vue";
// @ts-ignore
import {wrapperEnv} from "./types/wrapperEnv";

const root: string = process.cwd();

export default ({command, mode, ssrBuild}: ConfigEnv): UserConfigExport => {
  console.log(mode)
  console.log(ssrBuild)
  console.log(command)
  const {VITE_ENV, VITE_PUBLIC_PATH, VITE_V_CONSOLE, VITE_PORT, VITE_COMPRESSION} = wrapperEnv(loadEnv(mode, root))
  const isProd = VITE_ENV === 'production'
  return {
    base: VITE_PUBLIC_PATH,
    root: root,
    server: {
      hmr: true,
      host: '0.0.0.0',
      port: VITE_PORT,
      https: false,
      cors: true,
    },
    resolve: {
      alias: {
        '@': resolve(__dirname, './src'),
        '~': resolve(__dirname, './src/assets')
      }
    },
    plugins: [
      vue(),
      // jsx、tsx语法支持
      vueJsx(),
      legacy({
        targets: ['defaults', 'not IE 11']
      }),
      vueSetupExtend(),
      Components({
        // dts: false,
        resolvers: [VantResolver()],
      }),
      viteVConsole({
        entry: [resolve('src/main.ts')],
        localEnabled: VITE_V_CONSOLE,
        enabled: VITE_V_CONSOLE,
        config: {
          log: {
            maxLogNumber: 1500
          }
        }
      }),
      AutoImport({
        dts: false,
        imports: ['vue', 'vue-router']
      }),
      createStyleImportPlugin({
        resolves: [VantResolve()],
        libs: [{
          libraryName: 'vant',
          esModule: true,
          resolveStyle: (name) => {
            // show-total 样式已全局注册，不需要在引入，引入会报错
            if (name === 'show-toast'){
              return
            }
            return `../es/${name}/style/index`;
          },
        }]
      }),
      isProd ? viteSentry(sentryConfig) : null,
      isProd ? null : visualizer({open: true, gzipSize: true, brotliSize: true,}),
      configCompressPlugin(VITE_COMPRESSION)

    ],
  }
}

// @ts-ignore
const configCompressPlugin = (compress: ViteCompression): Plugin | Plugin[] => {
  if (compress === "none") return null;

  const gz = {
    // 生成的压缩包后缀
    ext: ".gz",
    // 体积大于threshold才会被压缩
    threshold: 0,
    // 默认压缩.js|mjs|json|css|html后缀文件，设置成true，压缩全部文件
    filter: () => true,
    // 压缩后是否删除原始文件
    deleteOriginFile: false
  };
  const br = {
    ext: ".br",
    algorithm: "brotliCompress",
    threshold: 0,
    filter: () => true,
    deleteOriginFile: false
  };

  const codeList = [
    {k: "gzip", v: gz},
    {k: "brotli", v: br},
    {k: "both", v: [gz, br]}
  ];

  const plugins: Plugin[] = [];

  codeList.forEach(item => {
    if (compress.includes(item.k)) {
      if (compress.includes("clear")) {
        if (item.v instanceof Array) {
          item.v.forEach(vItem => {
            plugins.push(
              // @ts-ignore
              compressPlugin(Object.assign(vItem, {deleteOriginFile: true}))
            );
          });
        } else {
          plugins.push(
            // @ts-ignore
            compressPlugin(Object.assign(item.v, {deleteOriginFile: true}))
          );
        }
      } else {
        if (item.v instanceof Array) {
          item.v.forEach(vItem => {
            // @ts-ignore
            plugins.push(compressPlugin(vItem));
          });
        } else {
          // @ts-ignore
          plugins.push(compressPlugin(item.v));
        }
      }
    }
  });
  return plugins;
};

// sentry配置
const sentryConfig = {
  url: 'https://sentry.gogpay.cn/',
  authToken: '3a67767d03424db2bfecc93089d71366fc09f6866339400886dfbe816f3cf770',
  org: 'sentry',
  project: 'h5-vue3',
  release: process.env.npm_package_version || '0.0.1', // 版本号，每次都npm run build上传都修改版本号
  deploy: {
    env: 'production'
  },
  skipEnvironmentCheck: true, // 可以跳过环境检查
  sourceMaps: {
    include: ['./dist/assets'],
    ignore: ['node_modules', 'vite.config.js'],
    urlPrefix: '~/assets'
  }
}
