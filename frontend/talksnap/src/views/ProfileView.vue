<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="aside"><ProfileNav /></el-aside>
      <el-main><router-view /></el-main>
    </el-container>
  </div>
</template>


<script>
import ProfileNav from '@/components/ProfileNav.vue'

export default {
    name: "ProfileView",
    components: {
        ProfileNav
    }
}
</script>

<style scoped>
.aside {
    /* width: auto; */
    margin: 120px 0 0 50px;

    border: 2px solid rgb(39 37 37 / 50%);
    border-radius: 5px;
    box-shadow: 5px 5px 5px 0.1rem rgb(39 37 37 / 50%);
}
</style>