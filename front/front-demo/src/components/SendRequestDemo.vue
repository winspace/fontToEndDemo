<template>
    <div>
     <el-button size="small" type="primary" @click="sendRequest">发送请求hello</el-button>
     <el-button size="small" type="primary" @click="sendRequestGreet">打招呼json</el-button>
     <el-button size="small" type="primary" @click="sendRequestDatabase">数据库请求</el-button>
    </div>
  </template>
  
  <script>
  
  import axios from "axios"

  export default {
    name: 'SendRequestDemo',
    methods:{
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
        }
    }
  }
  </script>
  