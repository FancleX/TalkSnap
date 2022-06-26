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
      >
        <img class="image" :src="bgImg" title="Edit" />
      </el-upload>
    </el-form-element>

    <el-form-element>
      <el-upload
        action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
        :show-file-list="false"
        :on-success="handleImgSuccess"
        :before-upload="beforeImgUpload"
        style="width: auto; margin-left: 20px"
        @click="handleSelect('1')"
        ref="upload"
      >
        <el-avatar
          :size="50"
          class="avatar"
          :src="circleUrl"
          style="margin: 10px"
          title="Edit"
        />
      </el-upload>
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
  name: "Settings",
  components: { DialogBox },
  data() {
    return {
      select: "",
      content: "",
      dialogFormVisible: false,
      myInfo: {
        nickname: String,
        profile_img: String,
        email: String,
        bio: String,
        bg_img: String,
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
  },
  components: { DialogBox },
};
</script>

<style scoped>
.settingform {
  max-width: 500px;
  padding: 10px;
  border: 1px outset rgba(57, 197, 187, 0.5);
  border-radius: 14px;
  box-shadow: 2px 5px 13px 0px #0b325e;
}

.settingform :deep() .el-upload {
  width: 100%;
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