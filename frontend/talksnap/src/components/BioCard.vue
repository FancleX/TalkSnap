<template>
  <el-row>
    <el-col :span="10">
      <el-card :body-style="{ padding: '0px' }" style="width: 400px" v-if="dataReady">
        <img
          :src="bgImg"
          class="image"
        />
        <el-avatar :size="50" :src="circleUrl" style="margin: 10px" />
        <div style="padding: 14px">
          <ul>
            <li>
              <span>{{ "@ " + userInfo.nickname }}</span>
            </li>
            <li>
              <span>{{ "Email: " + userInfo.email }}</span>
            </li>
            <li style="white-space: break-spaces">
              <span v-if="userInfo.bio">{{ "Bio: " + userInfo.bio }}</span>
              <span v-else>{{ "Bio: Hey there! I am using TalkSnap." }}</span>
            </li>
            <li>
              <span>{{ "Member since: " + userInfo.created_time.substring(0, 10) }}</span>
            </li>
          </ul>
          <div class="bottom">
            <el-button circle class="button" :type="subscribeBtnType" :title="subscribeTitle" @click="subscribe"><el-icon><Star /></el-icon></el-button>
          </div>
        </div>
      </el-card>
      <ProfileSk v-else />
    </el-col>
  </el-row>
</template>

<script>
import { ProfileEditor, ProfileFetcher } from "../service/user/profile";
import ProfileSk from "@/skeletons/ProfileSk.vue";
import { computed } from "@vue/runtime-core";
import { Star } from '@element-plus/icons-vue';

export default {
  name: "BioCard",
  components: { ProfileSk },
  data() {
    return {
      dataReady: false,
      circleUrl: computed(() => {
        return  this.userInfo.profile_img
          ? 'data:image/jpeg;base64,' + this.userInfo.profile_img
          : "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";
      }),
      bgImg: computed(() => {
        return this.userInfo.bg_img
          ? 'data:image/jpeg;base64,' + this.userInfo.bg_img
          : "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png";
      }),
      userInfo: {
        nickname: String,
        profile_img: String,
        email: String,
        bio: String,
        bg_img: String,
        created_time: String
      },
      params: {},
      subscribeBtnType: computed(() => { return ProfileEditor.analyzer(this.params.id, this.$store.getters.getMyProfile.subscriptions_values) ? 'danger' : 'warning' }),
      subscribeTitle: computed(() => { return this.subscribeBtnType === 'danger' ? 'Unsubcribe' : 'Subscribe' }),
    };
  },
  async mounted() {
    const params = decodeURIComponent(this.$route.params.user);
    // get id
    this.params = JSON.parse(params);
    // fetch intro of the user
    // {nickname: xxx, profile_img: xxx, email: xxx, bio: xxx, bg_img: xxx, created_time: xxx}
    this.userInfo = await ProfileFetcher.fetchUserProfile(this.params.id);
     
    this.dataReady = true;
  },
  methods: {
    async subscribe() {
        const user = this.params;
        await ProfileEditor.subscribe(user);
    }
  }
};
</script>

<style scoped>
.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  height: 200px;
  display: block;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
   padding: 5px;
}
</style>
