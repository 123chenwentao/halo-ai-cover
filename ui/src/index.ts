import { definePlugin } from '@halo-dev/console-shared'
import HomeView from './views/HomeView.vue'
import SettingsView from './views/SettingsView.vue'
import { IconPlug } from '@halo-dev/components'
import { markRaw } from 'vue'

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: 'Root',
      route: {
        path: '/ai-cover',
        name: 'AiCover',
        component: HomeView,
        meta: {
          title: 'AI 封面生成器',
          searchable: true,
          menu: {
            name: 'AI 封面',
            group: 'AI 封面生成器',
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
    {
      parentName: 'Root',
      route: {
        path: '/ai-cover/settings',
        name: 'AiCoverSettings',
        component: SettingsView,
        meta: {
          title: 'AI 封面配置',
          searchable: true,
          menu: {
            name: '配置',
            group: 'AI 封面生成器',
            icon: markRaw(IconPlug),
            priority: 1,
          },
        },
      },
    },
  ],
  extensionPoints: {},
})
