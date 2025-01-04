import './assets/main.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import './assets/theme.css'
import { createApp } from 'vue'

import App from './App.vue'
import { registerPlugins } from './plugins/index'
const app = createApp(App)
registerPlugins(app)

app.mount('#app')
