import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { title: '管理员登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: { title: '管理员注册' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/books',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'books',
        name: 'Books',
        component: () => import('@/views/Books.vue'),
        meta: { title: '图书管理', requiresAuth: true }
      },
      {
        path: 'students',
        name: 'Students',
        component: () => import('@/views/Students.vue'),
        meta: { title: '学生管理', requiresAuth: true }
      },
      {
        path: 'borrows',
        name: 'Borrows',
        component: () => import('@/views/Borrows.vue'),
        meta: { title: '借阅管理', requiresAuth: true }
      },
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/Books.vue'),
        meta: { title: '首页', requiresAuth: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const isLogin = localStorage.getItem('isLogin') === 'true'
  
  if (to.meta.requiresAuth && !isLogin) {
    next('/login')
  } else if ((to.path === '/login' || to.path === '/register') && isLogin) {
    next('/home')
  } else {
    next()
  }
})

export default router
