import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '',
      redirect: '/page/dashboard',
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
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/dashboard/index.vue'),
        },
      ],
    },
  ],
})

export default router
