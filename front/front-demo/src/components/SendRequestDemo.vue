<template>
    <div>
     <el-button size="small" type="primary" @click="sendRequest">发送请求</el-button>
    </div>
  </template>
  
  <script>
  
  import axios from "axios"

  export default {
    name: 'SendRequestDemo',
    methods:{
        sendRequest(){
            axios.post('/api/askme', { 
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
  