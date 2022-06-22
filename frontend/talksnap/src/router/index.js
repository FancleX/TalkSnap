import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'
import HomeView from '../views/HomeView.vue'
import LoginProcess from '@/service/user/login'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/components/Login.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('@/components/Signup.vue')
  },
  {
    path: '/home',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

let isLogin = false;
// router guard
router.beforeEach(async (to, from, next) => {
  if ((to.path !== "/login" && to.path !== "/signup" && to.path !== "/") && !localStorage.token) {
    next({ name: 'login' });
  }
  // auto login
  if (to.path === "/login" && localStorage.token && !isLogin) {
    if (LoginProcess.loginWithToken()) {
      isLogin = true;
      next('/home');
    } else {
      next();
    }
  }
  else {
    next();
  }
})

export default router
