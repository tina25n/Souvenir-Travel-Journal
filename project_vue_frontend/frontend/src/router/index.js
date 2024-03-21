import { createRouter, createWebHistory } from 'vue-router'
import {Authenticated} from "../../services/LoginServices";
import HomeView from "@/views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/user',
      name: 'user',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/Customer/UserView.vue')
    },
    {
      path: '/exec',
      name: 'exec',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/Administration/ExecView.vue')
    },
    {
      path: '/emp',
      name: 'emp',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/ServiceProvider/EmpView.vue')
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/service-provider/services-table',
      name: 'service-provider-services-table',
      component: () => import('../views/ServiceProvider/ServicesTable.vue')
    },
    {
      path: '/service-provider/reservations-table',
      name: 'service-provider-reservations-table',
      component: () => import('../views/ServiceProvider/ReservationsTable.vue')
    },
    {
      path: '/service-provider/inventory',
      name: 'service-provider-inventory-table',
      component: () => import('../views/ServiceProvider/InventoryTable.vue')
    },
    {
      path: '/service-provider/my-work-schedule-table',
      name: 'service-provider-my-work-schedule-table',
      component: () => import('../views/ServiceProvider/MyWorkScheduleTable.vue')
    },
    {
      path: '/admin/user-table',
      name: 'admin-user-table',
      component: () => import('../views/Administration/DatabaseManagement/UserTable.vue'),
    },
    {
      path: '/admin/branch-tables',
      name: 'admin-branch-tables',
      component: () => import('../views/Administration/DatabaseManagement/BranchTables.vue'),
    },
    {
      path: '/admin/service-provider-tables',
      name: 'admin-service-provider-tables',
      component: () => import('../views/Administration/DatabaseManagement/ServiceProviderTables.vue'),
    },
    {
      path: '/admin/services-table',
      name: 'admin-services-table',
      component: () => import('../views/Administration/DatabaseManagement/ServicesTable.vue'),
    },
    {
      path: '/admin/reservations-table',
      name: 'admin-reservations-table',
      component: () => import('../views/Administration/DatabaseManagement/ReservationsTable.vue'),
    },
    {
      path: '/admin/pets-table',
      name: 'admin-pets-table',
      component: () => import('../views/Administration/DatabaseManagement/PetTable.vue'),
    }
  ]
})


router.beforeEach(function (to, from, next) {
  if ((to.path !== '/login' && to.path !== 'login') && !Authenticated) {
    next({ path: '/login' })
  } else if ((to.path === '/login' || to.path === 'login') && Authenticated) {
    next({ path: '/' })
  } else {
   next()
  }
})


export default router
