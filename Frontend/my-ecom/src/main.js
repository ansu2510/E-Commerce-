import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // import router
import "./style.css"; // Tailwind styles

const app = createApp(App);
app.use(router);
app.mount("#app");
