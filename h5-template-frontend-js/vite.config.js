import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import {viteVConsole} from 'vite-plugin-vconsole'
import {resolve} from "path";
import AutoImport from "unplugin-auto-import/vite";
import legacy from '@vitejs/plugin-legacy';
import vueSetupExtend from 'vite-plugin-vue-setup-extend';
import Components from 'unplugin-vue-components/vite';
import {VantResolver} from 'unplugin-vue-components/resolvers';
import postCssPxToRem from 'postcss-pxtorem';
import {createStyleImportPlugin, VantResolve} from 'vite-plugin-style-import';
import viteSentry from 'vite-plugin-sentry';
import {visualizer} from 'rollup-plugin-visualizer';


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

// https://vitejs.dev/config/
export default defineConfig(({command, mode, ssrBuild}) => {
    const {VITE_ENV, VITE_V_CONSOLE, VITE_BASE_API} = loadEnv(mode, process.cwd())
    const isProd = VITE_ENV === 'production'
    const isDev = VITE_ENV === 'development'
    const isvConsole = VITE_V_CONSOLE === 'true'
    return {
        base: isProd ? './' : './',
        build: {
            cssCodeSplit: false,
            chunkSizeWarningLimit: 2048,
            sourcemap: isProd,
        },
        esbuild: {
            drop: isProd ? ['console', 'debugger'] : []
        },
        plugins: [
            vue(),
            legacy({
                targets: ['defaults', 'not IE 11']
            }),
            vueSetupExtend(),
            Components({
                dts: false,
                resolvers: [VantResolver()],
            }),
            viteVConsole({
                entry: [resolve('src/main.js')],
                localEnabled: isvConsole,
                enabled: isvConsole,
                config: {
                    maxLogNumber: 1000,
                    theme: 'light'
                }
            }),
            AutoImport({
                dts: false,
                imports: ['vue', 'vue-router']
            }),
            createStyleImportPlugin({
                resolves: [VantResolve],
                libs: [{
                    libraryName: 'vant',
                    esModule: true,
                    resolveStyle: (name) => {
                        return `../es/${name}/style/index`;
                    },
                }]
            }),
            isProd ? viteSentry(sentryConfig) : null,
            isProd ? null : visualizer({
                open: true,
                gzipSize: true,
                brotliSize: true,
            })
        ],
        server: {
            hmr: true,
            host: '0.0.0.0',
            port: 8701,
            https: false,
            open: true,
            cors: true,
        },
        resolve: {
            alias: {
                '@': resolve(__dirname, './src'),
                '~': resolve(__dirname, './src/assets')
            }
        },
    }
})
