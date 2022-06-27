import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'
import HomeView from '../views/HomeView.vue'
import LoginProcess from '@/service/user/login'
import ProfileView from '@/views/ProfileView.vue'
import BioCard from '@/components/BioCard.vue'

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
    name: 'mainpage',
    component: ProfileView,
    children: [
      {
        // params: {id: xxx, nickname: xxx}
        path: '/profile/:user',
        name: 'biocard',
        component: BioCard
      },
      {
        path: '/profile/settings',
        name: 'settings',
        component:() => import('@/components/Settings.vue')
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router guard
router.beforeEach(async (to, from, next) => {
  if ((to.path !== "/login" && to.path !== "/signup" && to.path !== "/") && !store.getters.getAuth) {
    next({ name: 'login' });
  }
  // auto login
  else if (to.path === "/login" && store.getters.getAuth && !store.getters.isLogin) {
    if (LoginProcess.loginWithToken()) {
      store.commit('login');
      next('/home');
    } else {
      next('/login');
    }
  }
  else {
    next();
  }
})

export default router
