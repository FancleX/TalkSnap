<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="300px" class="sidenav">
        <div style="width: 100%">
          <el-scrollbar class="aside">
            <ProfileNav />
          </el-scrollbar>
        </div>
      </el-aside>
      <el-main>
        <el-row style="height: 50px"></el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="7">
            <router-view class="grid-content" />
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import ProfileNav from "@/components/ProfileNav.vue";

export default {
  name: "ProfileView",
  components: {
    ProfileNav,
  },
};
</script>

<style scoped>
.sidenav {
  margin: 100px 0 0 50px;
}

.aside {
  border: 2px solid rgba(196, 189, 189, 0.5);
  border-radius: 10px;
}

.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
