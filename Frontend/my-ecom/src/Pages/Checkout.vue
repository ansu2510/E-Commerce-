<template>
  <div class="min-h-screen bg-gray-100 flex flex-col items-center p-6">
    <h1 class="text-3xl font-bold text-blue-600 mb-6">Checkout</h1>

    <div class="bg-white shadow-lg rounded-xl p-6 w-full max-w-lg">
      <h2 class="text-xl font-semibold mb-4">Order Summary</h2>
      <ul class="divide-y divide-gray-200 mb-4">
        <li
          v-for="item in cartItems"
          :key="item.id"
          class="flex justify-between py-2"
        >
          <span>{{ item.name }} (x{{ item.quantity }})</span>
          <span>₹{{ item.price * item.quantity }}</span>
        </li>
      </ul>

      <div class="flex justify-between text-lg font-bold mb-6">
        <span>Total:</span>
        <span>₹{{ formattedTotal }}</span>
      </div>

      <button
        @click="placeOrder"
        class="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition"
      >
        Place Order
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute(); // ✅ this is the current route

// Load cart items (if you stored them in localStorage)
const cartItems = JSON.parse(localStorage.getItem("cart") || "[]");

const total = computed(() => {
  const raw = route.query.total; // ✅ use route, not router
  const s = Array.isArray(raw) ? raw[0] : raw; // handle possible array
  return parseFloat(s ?? "0");
});

const formattedTotal = computed(() => total.value.toFixed(2));

const placeOrder = () => {
  alert("Order placed successfully!");
  localStorage.removeItem("cart");
  router.push("/"); // redirect to home
};
</script>

