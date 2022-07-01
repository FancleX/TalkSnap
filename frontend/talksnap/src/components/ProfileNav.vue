<template>
  <el-row class="tac" style="max-height: 500px" v-if="isDataReady">
    <el-col>
      <el-menu
        active-text-color="#39C5BB"
        background-color="rgba(193, 232, 246, 0.1)"
        text-color="black"
        @open="handleOpen"
        @close="handleClose"
      >
        <h5 class="mb-2" style="text-align: center; color: black; padding-top: 10px">
          DashBoard
        </h5>
        <el-sub-menu index="1">
          <template #title>
            <el-icon><ChatDotRound /></el-icon>
            <span>Chats</span>
          </template>
          <el-menu-item-group title="Group One">
            <el-menu-item index="1-1">item one</el-menu-item>
            <el-menu-item index="1-2">item one</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Group Two">
            <el-menu-item index="1-3">item three</el-menu-item>
          </el-menu-item-group>
          <el-sub-menu index="1-4">
            <template #title>item four</template>
            <el-menu-item index="1-4-1">item one</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Notebook /></el-icon>
            <span>Contacts</span>
          </template>
          <el-menu-item-group :title="myInfo.subscriptions_keys[index]" v-for="item, index in myInfo.subscriptions_values" :key="item">
            <el-menu-item :index="contactsIndex(index)" v-for="data in item" :key="data">{{ data.friendName }}</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="3">
          <el-icon><Connection /></el-icon>
          <span>Channels</span>
        </el-menu-item>
        <el-menu-item index="4">
          <el-icon><Orange /></el-icon>
          <span>Stories</span>
        </el-menu-item>
        <el-menu-item index="5">
          <el-icon><setting /></el-icon>
          <span>Settings</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
  <ProfileStructure v-else />
</template>

<script>
import ProfileStructure from '@/skeletons/ProfileStructure.vue';

export default {
  name: "ProfileNav",
  components: { ProfileStructure },
  data() {
    return {
      myInfo: {},
      isDataReady: true
    };
  },
  mounted() {
    this.myInfo = this.$store.getters.getMyProfile;
    if (!this.myInfo) {
      this.isDataReady = false;
    } else {
      this.isDataReady = true;
    }
  },
  watch: {
    "$store.state.userProfile"() {
      if (!this.$store.getters.getMyProfile) {
        this.isDataReady = false;
      } else {
        this.myInfo = this.$store.getters.getMyProfile;
        this.isDataReady = true;
      }
    },
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
      if (key === "5") {
        this.$router.push({ name: "settings" });
      }
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    contactsIndex(index) {
      return "2-" + index;
    }
  },
};
</script>

<style scoped></style>
