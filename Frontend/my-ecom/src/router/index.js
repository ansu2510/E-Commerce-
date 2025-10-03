import { createRouter, createWebHistory } from "vue-router";
import Login from "../Pages/Login.vue";
import Register from "../Pages/Register.vue";
import Dashboard from "../Pages/Dashboard.vue";


const routes = [
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  { path: "/", redirect: "/login" }, // default redirect
  { path: "/dashboard", component: Dashboard , meta: { requiresAuth: true }}, 
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});



router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  if (to.meta.requiresAuth && !token) {
    // If route requires auth and no token, redirect to login
    next("/");
  } else {
    // Otherwise, allow access
    next();
  }
});

// ✅ Important: Default export
export default router;
