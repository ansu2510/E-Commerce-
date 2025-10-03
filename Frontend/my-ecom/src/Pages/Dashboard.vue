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
            @click="showCartModal = true"
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

    <!-- 🛒 Cart Modal -->
<div
  v-if="showCartModal"
  class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
>
  <div class="bg-white w-96 rounded-lg shadow-lg p-6 relative">
    <!-- Close Button -->
    <button
      class="absolute top-2 right-2 text-gray-600 hover:text-gray-800"
      @click="showCartModal = false"
    >
      ✖
    </button>

    <h2 class="text-2xl font-bold mb-4">Your Cart</h2>

    <div v-if="cart.length > 0" class="space-y-4">
      <div
        v-for="item in cart"
        :key="item.id"
        class="flex justify-between items-center border-b pb-2"
      >
        <div>
          <h3 class="font-semibold">{{ item.name }}</h3>
          <p class="text-sm text-gray-500">
            Qty: {{ item.quantity }} × ${{ item.price }}
          </p>
        </div>
        <p class="font-bold text-blue-600">${{ item.totalPrice }}</p>
      </div>

      <div class="mt-4 flex justify-between font-bold text-lg">
        <span>Total:</span>
        <span>
          ${{ cart.reduce((acc, item) => acc + item.totalPrice, 0) }}
        </span>
      </div>

      <button
        class="w-full mt-4 bg-green-500 text-white py-2 rounded hover:bg-green-600 transition"
      >
        Checkout
      </button>
    </div>

    <p v-else class="text-center text-gray-500">Your cart is empty</p>
  </div>
</div>

  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router"; // Add this import
import api from "../services/api.js";
import { jwtDecode } from "jwt-decode";


const router = useRouter(); // Initialize router
const products = ref([]);
const cart = ref([]);
const username = ref("User"); // Add this since you're using it in template
const showCartModal = ref(false); // <-- for modal open/close


// Fetch all products from backend
const getAllProducts = async () => {
  try {
    const response = await api.get("http://localhost:8082/product/getAll");
    products.value = response.data;
    console.log("Products:", products);
    console.log("ProductsValue:", products.value);
  } catch (error) {
    console.log("Error fetching products:", error);
  }
};


const getUserIdFromToken = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);
    console.log("Decoded token:", decoded);
    return decoded.userId;  // 👈 depends on what backend put inside
  } catch (error) {
    console.error("Error decoding token:", error);
    return null;
  }
};


// Find product by ID
const findProductById = (productId) => {
  return products.value.find(product => product.id === productId);
};



// Add full product to cart
const addToCart = (productId) => {
  try {
    const product = findProductById(productId);
    console.log("here is the added product:" , product);
    
    if (!product) {
      console.error("Product not found:", productId);
      return;
    }

    // Check if product already exists in cart
    const existingItem = cart.value.find(item => item.id === productId);
    
    if (!existingItem) {
      // Add new product with quantity
      const cartItem = {
        ...product,
        quantity: 1,
        totalPrice: product.price
      };
      cart.value.push(cartItem);
      console.log("Product added to cart:", cartItem);
    } else {
      // Increase quantity if already in cart
      existingItem.quantity += 1;
      existingItem.totalPrice = existingItem.price * existingItem.quantity;
      console.log("Quantity increased for:", product.name);
    }
    
    console.log("Updated cart:", cart.value);
  } catch (error) {
    console.error("Error adding to cart:", error);
  }
};



const printcartvalue = () => {
  console.log("Cart contents:", cart.value);
  console.log("Cart length:", cart.value.length);
  console.log("Cart array:", [...cart.value]); // Alternative way to log
}

const logout = () => {
  localStorage.removeItem("token");
  router.push("/");
};

onMounted(() => {
  getAllProducts();
  getUserIdFromToken();
});
</script>
<style>
/* Optional: use Google Material Icons */
@import url("https://fonts.googleapis.com/icon?family=Material+Icons");
</style>
