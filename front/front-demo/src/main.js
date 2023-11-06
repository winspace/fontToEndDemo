import Vue from 'vue'
import App from './App.vue'
import elementui from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

Vue.use(elementui)

new Vue({
  render: h => h(App),
}).$mount('#app')
