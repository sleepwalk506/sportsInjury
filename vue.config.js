const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8082', // 后端服务地址
        ws: true,
        changeOrigin: true,
        pathRewrite: { '^/api': '' } // /api替换成空
      }
    }
  }
})
