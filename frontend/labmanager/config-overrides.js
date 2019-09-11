const {override, fixBabelImports, addLessLoader,addWebpackAlias} = require('customize-cra');
const path = require("path");
const api = require("./config");

/**
 * build-pro为线上地址
 * build-test为测试地址
 * 其他情况为开发地址
 */
if( process.env.npm_lifecycle_event === "build-pro"){
  process.env.REACT_APP_BASE_API = api.production
}else if( process.env.npm_lifecycle_event === "build-test"){
  process.env.REACT_APP_BASE_API = api.test
}else{
  process.env.REACT_APP_BASE_API = api.development
}
process.env.GENERATE_SOURCEMAP = false//去掉map文件


module.exports = override(
  // 针对antd实现按需打包: 根据import来打包(使用babel-plugin-import)
  fixBabelImports('import', {
    libraryName: 'antd',
    libraryDirectory: 'es',
    style: true,  // 自动打包相关的样式
  }),
    //别名配置
  addWebpackAlias({        
      ["@"]: path.resolve(__dirname, "./src"),             
      ["@views"]: path.resolve(__dirname, "./src/views"),
      ["@components"]: path.resolve(__dirname, "./src/components"),
      ["@api"]: path.resolve(__dirname, "./src/api") ,
      ["@utils"]: path.resolve(__dirname, "./src/utils")
  }),

  //使用less-loader对源码中的less的变量进行重新指定
  addLessLoader({
    javascriptEnabled: true,
    modifyVars: {
      '@primary-color': '#5C8CE6',  //全局主色  //修改
      '@link-color': '#1890ff', // 链接色
      '@success-color': '#52c41a', // 成功色
      '@warning-color': '#faad14', // 警告色
      '@error-color': '#f5222d', // 错误色
      '@font-size-base': '14px', // 主字号
      '@heading-color': 'rgba(0, 0, 0, 0.85)', // 标题色
      '@text-color': 'rgba(0, 0, 0, 0.65)', // 主文本色
      '@text-color-secondary' : 'rgba(0, 0, 0, .45)', // 次文本色
      '@disabled-color' : 'rgba(0, 0, 0, .25)', // 失效色
      '@border-radius-base': '4px', // 组件/浮层圆角
      '@border-color-base': '#d9d9d9', // 边框色
      '@box-shadow-base': '0 2px 8px rgba(0, 0, 0, 0.15)', // 浮层阴影
      "@btn-primary-bg":'#5C8CE6',
    },
  }),
)