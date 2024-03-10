
module.exports = {
  plugins: {
    "postcss-import": {},
    "tailwindcss/nesting": {},
    tailwindcss: {},
    autoprefixer: {
      overrideBrowserslist: ["Android >= 4.0", "iOS >= 7"]
    },
    "postcss-pxtorem": {
      rootValue: 37.5,  // 设计稿尺寸除以10
      unitPrecision: 5, // 精度
      propList: ['*'], // 需要转换的属性列表
      selectorBlackList: [], // 不进行转换的选择器列表
      replace: true, // 是否直接替换原始值
      mediaQuery: false,
      minPixelValue: 0,
    },
    ...(process.env.NODE_ENV === "production" ? {cssnano: {}} : {})
  }
};
