import type { App } from 'vue'
import { install as elementInstall } from './element'
import { install as elementIconInstall } from './element-icon'
import { install as piniaInstall } from './pinia'
import { install as routerInstall } from './router'
export function registerPlugins(app: App) {
  elementInstall(app)
  elementIconInstall(app)
  piniaInstall(app)
  routerInstall(app)
}
