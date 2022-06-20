<template>
  <div class="signupForm">
    <el-form
      :model="signupForm"
      status-icon
      :rules="rules"
      ref="signupForm"
      label-width="100px"
      style="width: 100%; heigth: 100%; magin: auto"
    >
      <el-header
        style="
          font-size: 2rem;
          padding: 40px 20px 10px 20px;
          text-align: center;
          margin-top: 1rem;
        "
        >Signup</el-header
      >
      <el-form-item label="nickname" prop="nickname" class="item" style="margin-top: 4rem;">
        <el-input type="text" v-model="signupForm.nickname" autocomplete></el-input>
      </el-form-item>
      <el-form-item label="email" prop="email" class="item">
        <el-input type="email" v-model="signupForm.email" autocomplete></el-input>
      </el-form-item>
      <el-form-item label="password" prop="password" class="item">
        <el-input type="password" v-model="signupForm.password" autocomplete></el-input>
      </el-form-item>
      <el-form-item label="confirm password" prop="duplicatedPassword" class="item">
        <el-input type="password" v-model="signupForm.duplicatedPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item class="item">
        <el-button type="primary" style="margin-left: 90px;" @click="submitForm('signupForm')">Signup</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import MsgIndicator from "@/utils/msgIndicator";
import LoginProcess from "@/service/user/login";

export default {
  name: "Signup",
  data() {
    return {
      signupForm: {
        nickname: "",
        email: "",
        password: "",
        duplicatedPassword: ""
      },
      rules: {
        nickname: [
            { required: true, message: "Nickname cannot be empty", trigger: "blur" },
            {
            min: 3,
            max: 10,
            message: "Nickname should be 6 to 20 charaters",
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
        password: [
          { required: true, message: "Password cannot be empty", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "Password should be 6 to 20 charaters",
            trigger: "blur",
          },
        ],
        duplicatedPassword: [
          { required: true, validator: this.validatePassword, trigger: "change" },
        ],
      },
    };
  },
  methods: {
    validatePassword(rule, value, callback) {
      if (value !== this.signupForm.password) {
        callback(new Error("Password is not consistent"))
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          LoginProcess.signup(this.signupForm);
        } else {
          MsgIndicator.error("Something wrong with your information, please try again");
        }
      });
    },
  },
};
</script>

<style scoped>
.signupForm {
  margin: 150px 0 0 70px;
  width: 30rem;
  height: 30rem;
  border: 2px solid rgb(39 37 37 / 50%);
  border-radius: 5px;
  box-shadow: 5px 5px 5px 0.1rem rgb(39 37 37 / 50%);
}


.item {
  padding: 10px 15px 0px 10px;
}
</style>
