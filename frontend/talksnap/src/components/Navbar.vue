<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    :ellipsis="false"
    @select="handleSelect"
  >
    <el-menu-item index="0"
      ><img src="../assets/logo.png" style="width: auto; height: 56px" />
    </el-menu-item>
    <div class="logo">
      <span id="Ta">Ta</span>
      <span id="lk">lk</span>
      <span id="Sn">Sn</span>
      <span id="ap">ap</span>
    </div>
    <div class="flex-grow" />
    <el-menu-item v-if="isLogin" index="2">
    <SearchBar />  
    </el-menu-item>
    <el-menu-item v-else index="1" style="margin-right: 25px">
      <div style="font-size: 1rem">Login/Signup</div>
    </el-menu-item>
  </el-menu>
</template>


<script>
import { computed, ref } from "vue";
import SearchBar from "@/components/SearchBar.vue"

export default {
  name: "Navbar",
  components: {
    SearchBar
  },
  data() {
    return {
      activeIndex: ref("1"),
      isLogin: computed(() => { return this.$store.getters.isLogin})
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      switch (key) {
        case "0":
          this.$router.go(0);
        case "1":
          this.$router.push("/login");    
      };
    },
  },
};
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

.el-menu-demo {
  background: none;
  height: 5rem;
}

.logo {
  margin: auto;
}

.logo:hover span {
  animation: flicker 1s linear forwards;
}
.logo:hover #lk {
  animation-delay: 0.2s;
}
.logo:hover #Sn {
  animation-delay: 0.5s;
}
.logo:hover #ap {
  animation-delay: 0.6s;
}

@keyframes flicker {
  0% {
    color: #333;
  }
  5%,
  15%,
  25%,
  30%,
  100% {
    color: rgb(41, 167, 240);
    text-shadow: 0px 0px 5px var(--color), 0px 0px 10px var(--color),
      0px 0px 20px var(--color), 0px 0px 50px var(--color);
  }
  10%,
  20% {
    color: #333;
    text-shadow: none;
  }
}
</style>
