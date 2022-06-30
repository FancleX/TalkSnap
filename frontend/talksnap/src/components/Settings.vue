<template>
  <el-form label-width="120px" class="settingform">
    <el-form-element>
      <el-upload
        action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
        :show-file-list="false"
        :on-success="handleImgSuccess"
        :before-upload="beforeImgUpload"
        @click="handleSelect('0')"
        ref="upload"
        :on-progress="handleProgress"
      >
        <img class="image" :src="bgImg" title="Edit" />
      </el-upload>
      <el-progress
        :percentage="progress.percent"
        v-if="progress.onProgressA"
        status="success"
      />
    </el-form-element>

    <el-form-element class="avatar">
      <el-upload
        action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
        :show-file-list="false"
        :on-success="handleImgSuccess"
        :before-upload="beforeImgUpload"
        style="width: auto; margin-left: 20px"
        @click="handleSelect('1')"
        ref="upload"
        :on-progress="handleProgress"
      >
        <el-avatar
          :size="50"
          class="avatar"
          :src="circleUrl"
          style="margin: 10px"
          title="Edit"
        />
      </el-upload>
      <el-progress
        :percentage="progress.percent"
        v-if="progress.onProgressB"
        status="success"
      />
    </el-form-element>

    <el-form-item label="Nickname">
      <el-button text @click="handleSelect('2')" bg>{{ myInfo.nickname }}</el-button>
    </el-form-item>
    <el-form-item label="Email">
      <el-button text @click="handleSelect('3')" bg>{{ myInfo.email }}</el-button>
    </el-form-item>
    <el-form-item label="Password">
      <el-button text @click="dialogFormVisible = true" bg>*********</el-button>
      <DialogBox v-model="dialogFormVisible" />
    </el-form-item>
    <el-form-item label="Bio">
      <el-input
        type="textarea"
        maxlength="150"
        show-word-limit
        @keyup.enter="handleSelect('4')"
        :placeholder="myInfo.bio"
        v-model="content"
      />
    </el-form-item>
    <el-form-item class="btn">
        <el-container style="margin:auto; justify-content: space-evenly;">
          <el-switch
          v-model="logout"
          class="ml-2"
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
          inactive-text="logout"
        />
        <el-popconfirm
          confirm-button-text="Yes"
          cancel-button-text="No"
          icon="Warning"
          icon-color="red"
          title="Are you sure to delete your Account?"
          @confirm="confirmEvent"
        >
          <template #reference>
            <el-button type="danger">Delete Account</el-button>
          </template>
        </el-popconfirm>
        </el-container>
    </el-form-item>
  </el-form>
</template>

<script>
import { computed } from "@vue/runtime-core";
import { Plus } from "@element-plus/icons-vue";
import MsgIndicator from "@/utils/msgIndicator";
import FileProcessor from "@/utils/fileProcessor";
import { ProfileEditor } from "../service/user/profile";
import MessageBox from "@/utils/messageBox";
import DialogBox from "@/components/DialogBox.vue";

export default {
  name: "settings",
  components: { DialogBox },
  data() {
    return {
      select: "",
      content: "",
      dialogFormVisible: false,
      logout: false,
      progress: {
        percent: 0,
        onProgressA: false,
        onProgressB: false,
      },
      myInfo: {
        nickname: "",
        profile_img: "",
        email: "",
        bio: "",
        bg_img: "",
      },
      circleUrl: computed(() => {
        return this.myInfo.profile_img
          ? "data:image/jpeg;base64," + this.myInfo.profile_img
          : "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";
      }),
      bgImg: computed(() => {
        return this.myInfo.bg_img
          ? "data:image/jpeg;base64," + this.myInfo.bg_img
          : "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png";
      }),
      bio: computed(() => {
        return this.myInfo.bio ? this.myInfo.bio : "Hey there! I am using TalkSnap.";
      }),
    };
  },
  mounted() {
    this.myInfo = this.$store.getters.getMyProfile;
  },
  watch: {
    "$store.state.userProfile"() {
      this.myInfo = this.$store.getters.getMyProfile;
    },
    logout() {
      if (!this.$logout) {
        this.$store.dispatch("deleteToken");
        this.$router.go(0);
      }
    },
  },
  methods: {
    beforeImgUpload(rawFile) {
      if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/png") {
        MsgIndicator.error("Picture must be JPG/PNG format!");
        return false;
      } else if (rawFile.size / 1024 > 500) {
        MsgIndicator.error("Picture size can not exceed 500KB!");
        return false;
      }
      return true;
    },
    async handleImgSuccess(response, uploadFile) {
      await FileProcessor.upload(uploadFile, this.select);
      this.$refs.upload.clearFiles();
    },
    handleProgress(event, file, fileList) {
      if (this.select === "0") {
        this.progress.onProgressA = true;
      } else if (this.select === "1") {
        this.progress.onProgressB = true;
      }
      this.progress.percent = parseInt(event.percent);
      if (this.progress.percent >= 100) {
        this.progress.percent = 100;
        setTimeout(() => {
          this.progress.onProgressA = false;
          this.progress.onProgressB = false;
          this.progress.percent = 0;
        }, 1000);
      }
    },
    handleSelect(key) {
      this.select = key;
      if (key === "2") {
        MessageBox.editNickname();
      }
      if (key === "3") {
        MessageBox.editEmail();
      }
      if (key === "4") {
        ProfileEditor.editBio(this.content);
        this.content = "";
      }
    },
    async confirmEvent() {
      await ProfileEditor.deleteAccount();
      this.$store.dispatch("deleteToken");
      this.$router.go(0);
    },
  },
};
</script>

<style scoped>
.settingform {
  max-width: 500px;
  min-width: 300px;
  padding: 10px;
  border: 1px outset rgba(57, 197, 187, 0.5);
  border-radius: 14px;
  box-shadow: 2px 5px 13px 0px #0b325e;
}

.settingform :deep() .el-upload {
  width: 100%;
}

.btn :deep() .el-form-item__content {
  margin-left: 0 !important;
}

.avatar :deep() div {
  margin-left: 0 !important;
}

.image {
  width: 100%;
  height: 200px;
  display: block;
  border-radius: 10px;
}

.image:hover {
  transform: scale(1.03);
  transition: 400ms;
}

.avatar:hover {
  transform: scale(1.1);
  transition: 400ms;
}
</style>
