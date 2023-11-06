<template>
    <div>
     <el-button size="small" type="primary">发送请求</el-button>
    </div>
  </template>
  
  <script>
  
  
  export default {
    name: 'SendRequestDemo',
    mounted(){
        this.sendRequest()
    },  
    methods:{
        sendRequest(){
            // alert(1)
        }
    }
  }
  </script>
  