<template>
  <el-form
    :model="form"
    label-width="120px"
    class="settingform"
    ref="form"
    :rules="rules"
  >
    <el-form-element>
      <el-upload
        action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        :show-file-list="false"
        :on-success="handleImgSuccess"
        :before-upload="beforeImgUpload"
        @click="handleSelect('0')"
      >
        <img class="image" :src="bgImg" title="Edit" />
      </el-upload>
    </el-form-element>

    <el-form-element>
      <el-upload
        action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        :show-file-list="false"
        :on-success="handleImgSuccess"
        :before-upload="beforeImgUpload"
        style="width: auto; margin-left: 20px"
        @click="handleSelect('1')"
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

    <el-form-item label="Nickname" prop="nickname">
      <el-input v-model="form.nickname" :placeholder="myInfo.nickname" clearable />
    </el-form-item>
    <el-form-item label="Email" prop="email">
      <el-input v-model="form.email" :placeholder="myInfo.email" clearable />
    </el-form-item>
    <el-form-item label="Password">
      <el-input placeholder="*********" clearable />
    </el-form-item>
    <el-form-item label="Bio">
      <el-input v-model="form.bio" type="textarea" maxlength="100" show-word-limit />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('form')">Edit</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { computed } from "@vue/runtime-core";
import { Plus } from "@element-plus/icons-vue";
import MsgIndicator from "@/utils/msgIndicator";
import FileProcessor from "@/utils/fileProcessor";
import { ProfileEditor } from "../service/user/profile";

export default {
  name: "Settings",
  data() {
    return {
      select: "",
      form: {
        nickname: "",
        email: "",
        bio: "",
      },
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
      rules: {
        nickname: [
          { required: true, message: "Nickname cannot be empty", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "Nickname should be 3 to 10 charaters",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "Email cannot be empty", trigger: "blur" },
          {
            pattern: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
            message: "Invalid email format",
            trigger: "blur",
          },
        ],
      },
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
    handleImgSuccess(response, uploadFile) {
      FileProcessor.upload(uploadFile, this.select);
    },
    handleSelect(key) {
      this.select = key;
    },
    submitForm(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          await ProfileEditor.editProfile(this.form);
        } else {
          MsgIndicator.error("Something wrong with your information, please try again");
        }
      });
    },
  },
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
  transition: 500ms ease-in-out;
}

.avatar:hover {
  transform: scale(1.1);
  transition: 500ms ease-in-out;
}
</style>
