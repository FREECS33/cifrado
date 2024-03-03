import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/',
    component: {
      render(c) {
        return c('router-view')
      }
    },
    children: [
      {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
      },
    ]
  }
]
const router = new VueRouter({
  mode: 'history',
  routes
})
export default router
