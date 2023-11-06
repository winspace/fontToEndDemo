const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8000/api/', // 后端服务器的地址和端口
        changeOrigin: true,
        pathRewrite: {
          '^/api': '', // 重写请求路径，将 /api 前缀去除
        },
      },
    },
  },
})