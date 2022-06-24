<template>
  <div class="common-layout">
    <el-container>
      <el-header><Navbar /></el-header>
      <el-main class="main">
        <router-view :key="$route.fullPath" />
      </el-main>
      <el-footer><Footer /></el-footer>
    </el-container>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";
import ProfileFetcher from "./service/user/profile";

export default {
  name: "HomeView",
  components: {
    Navbar,
    Footer,
  },
  created() {
    // has token don't have profile
    if (localStorage.token) {
      this.$store.commit("setToken", localStorage.token);
      if (sessionStorage.isLogin) {
        this.$store.commit("login");
        ProfileFetcher.fetchMyProfile();
      }
    }

    window.addEventListener("beforeunload", () => {
      localStorage.setItem("token", this.$store.state.getters.getAuth);
    });
  },
};
</script>

<style>
.main {
  min-height: 82vh;
  margin: 1rem 0 1rem 0;
  width: 100%;
}
</style>
