import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '',
      redirect: '/page/login',
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
          path: 'message',
          name: 'message',
          component: () => import('@/views/message/index.vue'),
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
      ],
    },
  ],
})

export default router
