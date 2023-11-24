<template>
    <div>
     <el-button size="small" type="primary" @click="sendRequest">发送请求hello</el-button>
     <el-button size="small" type="primary" @click="sendRequestGreet">打招呼json</el-button>
     <el-button size="small" type="primary" @click="sendRequestDatabase">数据库请求</el-button>
     <el-button size="small" type="primary" @click="refreshFileList">刷新文件列表</el-button>
     <el-upload
      action="/api/upload"  
      :before-upload="beforeUpload"
      :on-success="onSuccess" 
      :on-error="onError"
    >
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
    <el-table :data="fileList" height="300">
      <el-table-column prop="fileName" label="文件名"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="downloadFile(scope.row.fileId,scope.row.fileName)">下载</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>
  </template>
  
  <script>
  
  import axios from "axios"

  export default {
    name: 'SendRequestDemo',
    data(){
        return {
            fileList:[{"fileName":1}]
        }
    },  
    methods:{
        beforeUpload(file) {
            // 在上传前对文件进行验证，例如文件类型、大小等
            // 如果验证失败，返回 false 可以阻止文件上传
            // 如果验证成功，返回 true 允许文件上传
            console.log('beforeUpload', file);
            return true;
        },
        onSuccess(response, file) {
            // 上传成功后的回调方法
            console.log('onSuccess', response, file);
        },
        onError(error, file) {
            // 上传失败后的回调方法
            console.log('onError', error, file);
        },
        sendRequestGreet(){
            axios.get('/greeting?name="john"', { 
            // 请求体数据
            // 可以是对象、数组或其他类型的数据
            })
            .then(response => {
                // 请求成功，处理响应数据
                console.log(response.data);
            })
            .catch(error => {
                // 请求失败，处理错误
                console.error(error);
            });
        },
        sendRequest(){
            axios.get('/hello', { 
            // 请求体数据
            // 可以是对象、数组或其他类型的数据
            })
            .then(response => {
                // 请求成功，处理响应数据
                console.log(response.data);
            })
            .catch(error => {
                // 请求失败，处理错误
                console.error(error);
            });
        },
        sendRequestDatabase(){
            axios.get('/api/data', { 
            // 请求体数据
            // 可以是对象、数组或其他类型的数据
            })
            .then(response => {
                // 请求成功，处理响应数据
                console.log(response.data);
            })
            .catch(error => {
                // 请求失败，处理错误
                console.error(error);
            });
        },
        refreshFileList(){
            axios.get('/api/filelist', { 
            // 请求体数据
            // 可以是对象、数组或其他类型的数据
            })
            .then(response => {
                // 请求成功，处理响应数据
                this.fileList = response.data
                
            })
            .catch(error => {
                // 请求失败，处理错误
                console.error(error);
            });
        },
        downloadFile(fileId,fileName) {
            // 下载文件的逻辑
            // 创建一个链接，并设置其 href 属性为下载文件的后端接口路径
            const link = document.createElement('a');
            link.href = `/api/download/${fileId}`;
            link.download = fileName;
            // 触发点击事件来下载文件
            link.click();
        }

    }
  }
  </script>
  