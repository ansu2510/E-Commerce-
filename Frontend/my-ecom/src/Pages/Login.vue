<template>
  <div class="min-h-screen flex">
    <!-- Left side (banner / brand) -->
    <div
      class="hidden md:flex flex-1 bg-gradient-to-br from-blue-600 to-indigo-700 text-white flex-col justify-center items-center p-10"
    >
      <h1 class="text-4xl font-bold">MyEcom</h1>
      <p class="mt-4 text-lg text-gray-100">
        Your favorite shopping destination.
      </p>
      <img
        src="https://cdn-icons-png.flaticon.com/512/3081/3081559.png"
        class="w-72 mt-8"
        alt="Shopping illustration"
      />
    </div>

    <!-- Right side (form) -->
    <div class="flex-1 flex justify-center items-center bg-gray-50">
      <div class="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
        <h2 class="text-2xl font-bold text-gray-800 text-center">
          Login to your account
        </h2>
        <p class="text-center text-gray-500 mb-6">
          Welcome back! Please enter your details.
        </p>

        <!-- Form -->
        <form @submit.prevent="login" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700">Email</label>
            <input
              v-model="email"
              type="email"
              placeholder="you@example.com"
              class="mt-1 w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700"
              >Password</label
            >
            <input
              v-model="password"
              type="password"
              placeholder="••••••••"
              class="mt-1 w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition"
          >
            Login
          </button>
        </form>

        <!-- Extras -->
        <div
          class="flex justify-between items-center mt-4 text-sm text-gray-500"
        >
          <a href="#" class="hover:underline">Forgot password?</a>
          <a href="/register" class="hover:underline">New here? Register</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import api from "../services/api.js";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");
const password = ref("");

const login = async () => {
  try {
    const response = await api.post("http://localhost:8081/users/login", {
      email: email.value,
      password: password.value,
    });
    localStorage.setItem("token", response.data.token);
    alert("Login successful!");
    router.push("/dashboard");
  } catch (err) {
    alert(err.response?.data || "Login failed");
  }
};
</script>
