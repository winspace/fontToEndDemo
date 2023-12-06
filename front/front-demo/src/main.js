import Vue from 'vue'
import App from './App.vue'
import IdiomPage from './components/Idiom.vue'
import SendRequestDemo from './components/SendRequestDemo.vue'
import elementui from 'element-ui'
import VueRouter from 'vue-router';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

Vue.use(elementui)
Vue.use(VueRouter)

const routes = [
  { path: '/', component: SendRequestDemo },
  { path: '/idiomPage', component: IdiomPage },
  { path: '/error', component: IdiomPage },
  // 其他路由配置...
];

const router = new VueRouter({
  routes // 等同于 routes: routes
});


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
