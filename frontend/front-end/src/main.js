import './assets/main.css'
import RayPlugin from 'vue-ray';

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(RayPlugin, { interceptErrors: true })

app.mount('#app')
