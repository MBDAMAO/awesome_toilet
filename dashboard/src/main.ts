import './assets/main.css'
import './assets/theme.css'
import { createApp } from 'vue'
import VueECharts from 'vue-echarts'
import * as echarts from 'echarts'

import App from './App.vue'
import { registerPlugins } from './plugins/index'
const app = createApp(App)
registerPlugins(app)
app.component('VueECharts', VueECharts)

app.mount('#app')
