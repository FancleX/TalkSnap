<template>
  <div class="common-layout">
    <el-container>
      <el-header><Navbar /></el-header>
      <el-main class="main">
        <router-view
          :key="$route.fullPath"
        />
      </el-main>
      <el-footer><Footer /></el-footer>
    </el-container>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";

export default {
  name: "HomeView",
  components: {
    Navbar,
    Footer,
  },
  created() {
    if (localStorage.getItem("token")) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state.token,
          localStorage.getItem("token")
        )
      );
    }
    window.addEventListener("beforeunload", () => {
      localStorage.setItem("token", this.$store.state.token);
    });
  }
};
</script>

<style>
.main {
  min-height: 82vh;
  margin: 1rem 0 1rem 0;
  width: 100%;
}
</style>
