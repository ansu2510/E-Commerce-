<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div
      class="bg-white shadow-xl rounded-2xl flex max-w-4xl w-full overflow-hidden"
    >
      <!-- Left Side: Banner / Image -->
      <div
        class="hidden lg:flex w-1/2 bg-gradient-to-br from-blue-500 to-indigo-600 justify-center items-center"
      >
        <img
          src="https://cdn-icons-png.flaticon.com/512/3081/3081559.png"
          alt="E-commerce banner"
          class="w-3/4 h-auto rounded-lg"
        />
      </div>

      <!-- Right Side: Registration Form -->
      <div class="w-full lg:w-1/2 p-8 sm:p-12">
        <h2 class="text-3xl font-bold text-gray-800 mb-6">
          Create Your Account
        </h2>
        <p class="text-gray-500 mb-6">
          Join our community and start shopping today!
        </p>

        <form @submit.prevent="register" class="space-y-4">
          <!-- Username -->
          <div class="relative">
            <input
              v-model="username"
              type="text"
              placeholder="Username"
              class="w-full p-3 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
            />
            <span class="absolute left-3 top-3 text-gray-400">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M5.121 17.804A9 9 0 1119.88 6.196 9 9 0 015.12 17.804z"
                />
              </svg>
            </span>
          </div>

          <!-- Email -->
          <div class="relative">
            <input
              v-model="email"
              type="email"
              placeholder="Email"
              class="w-full p-3 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
            />
            <span class="absolute left-3 top-3 text-gray-400">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M16 12H8m0 0l-4 4m4-4l-4-4m8 8h8m-8 0l4-4m-4 4l4 4"
                />
              </svg>
            </span>
          </div>

          <!-- Password -->
          <div class="relative">
            <input
              v-model="password"
              type="password"
              placeholder="Password"
              class="w-full p-3 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
            />
            <span class="absolute left-3 top-3 text-gray-400">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 11c0-2.21 1.79-4 4-4s4 1.79 4 4v2H8v-2c0-2.21 1.79-4 4-4z"
                />
              </svg>
            </span>
          </div>

          <!-- Register Button -->
          <button
            type="submit"
            class="w-full bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition duration-300"
          >
            Register
          </button>
        </form>

        <p class="text-gray-500 mt-4 text-center">
          Already have an account?
          <a href="/login" class="text-blue-500 hover:underline">Login here</a>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import api from "../services/api.js";
import { useRouter } from "vue-router";

const router = useRouter();

const username = ref("");
const email = ref("");
const password = ref("");

const register = async () => {
  try {
    const response = await api.post(
      "http://localhost:8080/user-service/users/signup",
      {
        name: username.value,
        email: email.value,
        password: password.value,
        role: "CUSTOMER",
      }
    );

    console.log("Full response:", response.data);

    if (response.data && response.data.token) {
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role);
      alert("Registration successful!");
      router.push("/dashboard");
    } else {
      alert("Registration successful, but no token received!");
      router.push("/dashboard");
    }
  } catch (err) {
    console.error("Full error object:", err);

    if (err.response) {
      // Server responded with error status
      console.error("Error response data:", err.response.data);
      console.error("Error response status:", err.response.status);
      alert(err.response.data.message || "Registration failed!");
    } else if (err.request) {
      // Request was made but no response received
      console.error("No response received:", err.request);
      alert("No response from server. Check if backend is running.");
    } else {
      // Something else happened
      console.error("Error message:", err.message);
      alert("Something went wrong: " + err.message);
    }
  }
};
</script>
