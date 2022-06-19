<template>
  <el-card class="box-card" style="margin: 100px 0 0 30px;">
    <template #header>
      <div class="card-header">
        <span>Wellcome to TalkSnap</span>
        <el-button class="button" button @click="toggle()">Get Started</el-button>
      </div>
    </template>
    <div v-for="o in 4" :key="o" class="text item">&#9770; {{ ads[o-1] }}</div>
  </el-card>
</template>

<script>

export default {
  name: "Wellcome",
  data() {
    return {
      ads: ["Have fun with your friends and family.", "Share moments in your community.", "Facetime and voice.", "Get start now!"],
    }
  },
  methods: {
    toggle() {
      this.$router.push("/login");
    }
  },

}
</script>


<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
  font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: bold;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 480px;
}
</style>
