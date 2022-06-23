import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'
import HomeView from '../views/HomeView.vue'
import LoginProcess from '@/service/user/login'
import ProfileView from '@/views/ProfileView.vue'

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
    name: 'profile',
    components: {
      default: ProfileView,

    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

let isLogin = false;
// router guard
router.beforeEach(async (to, from, next) => {
  if ((to.path !== "/login" && to.path !== "/signup" && to.path !== "/") && !store.getters.getAuth) {
    next({ name: 'login' });
  }
  // auto login
  if (to.path === "/login" && store.getters.getAuth && !isLogin) {
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
