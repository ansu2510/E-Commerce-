<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <!-- Header -->
    <header class="bg-blue-600 text-white p-6 shadow-md">
      <div class="max-w-7xl mx-auto flex justify-between items-center">
        <h1 class="text-2xl font-bold">MyShop</h1>
        <!-- <nav class="flex space-x-6">
          <router-link to="/" class="hover:underline">Home</router-link>
          <router-link to="/cart" class="hover:underline">Cart</router-link>
          <router-link to="/checkout" class="hover:underline">Checkout</router-link>
        </nav> -->
      </div>
    </header>

    <!-- Hero Section -->
    <section class="bg-white shadow-md">
      <div class="max-w-7xl mx-auto text-center py-16 px-6">
        <h2 class="text-4xl font-bold text-gray-800 mb-4">
          Welcome to MyShop
        </h2>
        <p class="text-gray-600 mb-6">
          Discover amazing products at unbeatable prices!
        </p>
        <router-link
          to="/login"
          class="bg-blue-600 text-white px-6 py-3 rounded-lg shadow hover:bg-blue-700 transition"
        >
          Shop Now
        </router-link>
      </div>
    </section>

    <!-- Product Grid -->
    <main class="flex-grow max-w-7xl mx-auto py-12 px-6">
      <h2 class="text-2xl font-semibold text-gray-800 mb-8">Featured Products</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
        <div
          v-for="product in products"
          :key="product.id"
          class="bg-white rounded-lg shadow hover:shadow-lg transition p-4 flex flex-col"
        >
          <img
            :src="product.image"
            :alt="product.name"
            class="w-full h-48 object-cover rounded-md mb-4"
          />
          <h3 class="text-lg font-semibold text-gray-700">{{ product.name }}</h3>
          <p class="text-gray-500 mb-2">₹{{ product.price }}</p>
          <button
            @click="addToCart(product)"
            class="mt-auto bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition"
          >
            Add to Cart
          </button>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer class="bg-blue-600 text-white text-center py-4">
      <p>&copy; 2025 MyShop. All rights reserved.</p>
    </footer>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; 


const router = useRouter();

// Example products
const products = ref([
  { id: 1, name: "Product 1", price: 499, image: "https://picsum.photos/300?random=1" },
  { id: 2, name: "Product 2", price: 899, image: "https://picsum.photos/300?random=2" },
  { id: 3, name: "Product 3", price: 1299, image: "https://picsum.photos/300?random=3" },
  { id: 4, name: "Product 4", price: 699, image: "https://picsum.photos/300?random=4" },
]);

const goToLogin = () => {
    router.push("/login");
}



</script>
