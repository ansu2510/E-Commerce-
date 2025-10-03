<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Simple Navbar -->
    <nav class="bg-white shadow p-4 flex justify-between">
      <h1 class="text-xl font-bold text-blue-600">My E-Commerce</h1>
      <div class="space-x-4">
        <router-link to="/login" class="text-gray-700 hover:text-blue-500">Login</router-link>
        <router-link to="/register" class="text-gray-700 hover:text-blue-500">Register</router-link>
      </div>
    </nav>

    <!-- Main Page Content -->
    <main class="p-6">
      <router-view />
    </main>
  </div>
</template>

<script setup>
// No need to import HelloWorld anymore
</script>

<style>
/* You can keep global styles here */
</style>
