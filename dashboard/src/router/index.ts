import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '',
      redirect: '/page/dashboard',
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
          path: 'questionnaire',
          name: 'questionnaire',
          component: () => import('@/views/questionnaire/index.vue'),
        },
        {
          path: 'sso-callback',
          name: 'sso-callback',
          component: () => import('@/views/SsoCallback/index.vue'),
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/dashboard/index.vue'),
        },
        {
          path: 'room',
          name: 'room',
          component: () => import('@/views/toilet_room/index.vue'),
        },
      ],
    },
  ],
})

export default router
