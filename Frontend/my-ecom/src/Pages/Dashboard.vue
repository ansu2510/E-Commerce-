<template>
  <div class="min-h-screen flex bg-gray-100">
    <!-- Sidebar -->
    <aside class="hidden md:flex flex-col w-64 bg-white shadow-lg">
      <div class="p-6 text-2xl font-bold text-blue-600">MyShop</div>
      <nav class="flex flex-col mt-10 space-y-3">
        <a
          href="#"
          class="px-6 py-3 hover:bg-blue-100 rounded flex items-center"
        >
          <span class="material-icons mr-3">dashboard</span> Dashboard
        </a>
        <a
          href="#"
          class="px-6 py-3 hover:bg-blue-100 rounded flex items-center"
        >
          <span class="material-icons mr-3">shopping_cart</span> Orders
        </a>
        <a
          href="#"
          class="px-6 py-3 hover:bg-blue-100 rounded flex items-center"
        >
          <span class="material-icons mr-3">favorite</span> Wishlist
        </a>
        <a
          href="#"
          class="px-6 py-3 hover:bg-blue-100 rounded flex items-center"
        >
          <span class="material-icons mr-3">person</span> Profile
        </a>
        <a
          href="#"
          class="px-6 py-3 hover:bg-blue-100 rounded flex items-center"
          @click="logout"
        >
          <span class="material-icons mr-3">logout</span> Logout
        </a>
      </nav>
    </aside>

    <!-- Main Content -->
    <div class="flex-1 p-6 md:p-10">
      <!-- Header -->
      <header class="flex justify-between items-center mb-8">
        <h1 class="text-3xl font-bold text-gray-800">
          Welcome, {{ username }}
        </h1>
        <div class="flex items-center space-x-4">
          <p>{{ cart.length }}</p>
          <button
            class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
          >
            Cart
          </button>
        </div>
      </header>

      <!-- Summary Cards -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <div class="bg-white p-6 rounded-lg shadow flex flex-col items-center">
          <span class="material-icons text-4xl text-blue-500 mb-2"
            >shopping_cart</span
          >
          <p class="text-gray-500">Total Orders</p>
          <h2 class="text-2xl font-bold">12</h2>
        </div>
        <div class="bg-white p-6 rounded-lg shadow flex flex-col items-center">
          <span class="material-icons text-4xl text-red-500 mb-2"
            >favorite</span
          >
          <p class="text-gray-500">Wishlist</p>
          <h2 class="text-2xl font-bold">5</h2>
        </div>
        <div class="bg-white p-6 rounded-lg shadow flex flex-col items-center">
          <span class="material-icons text-4xl text-yellow-500 mb-2"
            >credit_card</span
          >
          <p class="text-gray-500">Pending Payments</p>
          <h2 class="text-2xl font-bold">2</h2>
        </div>
        <div class="bg-white p-6 rounded-lg shadow flex flex-col items-center">
          <span class="material-icons text-4xl text-green-500 mb-2"
            >local_shipping</span
          >
          <p class="text-gray-500">Delivered Orders</p>
          <h2 class="text-2xl font-bold">8</h2>
        </div>
      </div>

      <!-- Recommended Products -->
      <section>
        <h2 class="text-2xl font-bold mb-6">Recommended for You</h2>
        <div
          class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6"
        >
          <div
            v-for="product in products"
            :key="product.id"
            class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition"
          >
            <img
              :src="product.path"
              alt="Product Image"
              class="h-40 w-full object-cover rounded mb-4"
            />
            <h3 class="text-lg font-semibold mb-1">{{ product.name }}</h3>
            <p class="text-gray-500 mb-2">{{ product.category }}</p>
            <p class="text-blue-600 font-bold">${{ product.price }}</p>
            <button
              class="w-full mt-3 bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition"
              @click="addToCart(product.id)"
            >
              Add to Cart
            </button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import api from "../services/api.js"; // Make sure this path is correct

// Reactive variable to store products
const products = ref([]);
const cart = ref([]);

// Fetch all products from backend
const getAllProducts = async () => {
  try {
    const response = await api.get("http://localhost:8082/product/getAll");
    products.value = response.data; // Store the data in reactive variable
    console.log(products);
  } catch (error) {
    console.log("Error fetching products:", error);
  }
};

const addToCart = (productId) => {
  if (!cart.value.includes(productId)) {
    cart.value.push(productId);
    console.log("Cart:", cart.value);
  } else {
    console.log("product already added");
  }
};

const logout = () => {
  // Remove the token from localStorage
  localStorage.removeItem("token");

  // Optionally, remove other user info if stored
  // localStorage.removeItem("user"); // if you saved user details

  // Redirect to login page
  router.push("/");
};

// Call the function when component mounts
onMounted(() => {
  getAllProducts();
});
</script>

<style>
/* Optional: use Google Material Icons */
@import url("https://fonts.googleapis.com/icon?family=Material+Icons");
</style>
