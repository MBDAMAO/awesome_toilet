import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '',
      redirect: '/root',
    },
    {
      path: '/sso-callback',
      redirect: '/page/sso-callback',
    },
    {
      path: '/root',
      component: () => import('@/views/headerSidePage/index.vue'),
      children: [
        {
          path: '',
          redirect: '/root/dashboard',
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/dashboard/index.vue'),
        },
        {
          path: 'water',
          name: 'water',
          component: () => import('@/views/data_stat/water/index.vue'),
        },
        {
          path: 'alarm_settings',
          name: 'alarm_settings',
          component: () => import('@/views/alarm_settings/index.vue'),
        },
        {
          path: 'device_test',
          name: 'device_test',
          component: () => import('@/views/device_test/index.vue'),
        },
        {
          path: 'msg_push_settings',
          name: 'msg_push_settings',
          component: () => import('@/views/msg_push_settings/index.vue'),
        },
        {
          path: 'paper',
          name: 'paper',
          component: () => import('@/views/data_stat/paper/index.vue'),
        },
        {
          path: 'elect',
          name: 'elect',
          component: () => import('@/views/data_stat/elect/index.vue'),
        },
        {
          path: 'people',
          name: 'people',
          component: () => import('@/views/data_stat/people/index.vue'),
        },
        {
          path: 'satify',
          name: 'satify',
          component: () => import('@/views/data_stat/satify/index.vue'),
        },
        {
          path: 'alarm',
          name: 'alarm',
          component: () => import('@/views/data_stat/alarm/index.vue'),
        },
        {
          path: 'device',
          name: 'device',
          component: () => import('@/views/device/index.vue'),
        },
        {
          path: 'toilet',
          name: 'toilet',
          component: () => import('@/views/toilet/index.vue'),
        },
        {
          path: 'map',
          name: 'map',
          component: () => import('@/views/map/index.vue'),
        },
        {
          path: 'message',
          name: 'message',
          component: () => import('@/views/message/index.vue'),
        },
        {
          path: 'editor',
          name: 'editor',
          component: () => import('@/views/toilet_editor/index.vue'),
        },
        {
          path: 'user',
          name: 'user',
          children: [
            {
              path: '',
              redirect: '/root/user/info',
            },
            {
              path: 'info',
              name: 'info',
              component: () => import('@/views/user/info/index.vue'),
            },
            {
              path: 'sub',
              name: 'sub',
              component: () => import('@/views/user/sub/index.vue'),
            },
          ],
        },
      ],
    },
    {
      path: '/page',
      name: 'page',
      component: () => import('@/views/purePage/index.vue'),
      children: [
        {
          path: 'login',
          name: 'login',
          component: () => import('@/views/login/index.vue'),
        },
        {
          path: 'auth',
          name: 'auth',
          component: () => import('@/views/auth_center/index.vue'),
        },
        {
          path: 'sso-callback',
          name: 'sso-callback',
          component: () => import('@/views/SsoCallback/index.vue'),
        },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token')
  const requiresAuth = to.meta.requiresAuth

  if (requiresAuth && !isAuthenticated) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
