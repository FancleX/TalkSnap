<template>
  <div class="loginForm">
    <el-form
      :model="loginForm"
      status-icon
      :rules="rules"
      ref="loginForm"
      label-width="100px"
      style="width: 100%; heigth: 100%; magin: auto"
    >
      <el-header style="font-size: 2rem; padding: 40px 20px 10px 20px; text-align: center; margin-top:1rem"
        >Login</el-header
      >
      <el-form-item label="email" prop="email" class="item" style="margin-top: 5rem">
        <el-input type="email" v-model="loginForm.email" autocomplete></el-input>
      </el-form-item>
      <el-form-item label="password" prop="password" class="item">
        <el-input type="password" v-model="loginForm.password" autocomplete></el-input>
      </el-form-item>
      <el-form-item class="item">
        <el-button type="primary" @click="submitForm('loginForm')">Login</el-button>
        <el-button @click="toggleSignup()">No account? Signup here</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import MsgIndicator from "@/utils/msgIndicator";
import LoginProcess from "@/service/user/login";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        email: "",
        password: "",
      },
      rules: {
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
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          LoginProcess.loginWithAccount(this.loginForm);
        } else {
          MsgIndicator.error("Something wrong with your information, please try again");
        }
      });
    },
    toggleSignup() {
      this.$router.push("/signup");
    },
  },
};
</script>

<style scoped>
.loginForm {
  margin: 150px 0 0 70px;
  width: 30rem;
  height: 25rem;
  border: 2px solid rgb(39 37 37 / 50%);
  border-radius: 5px;
  box-shadow: 5px 5px 5px 0.1rem rgb(39 37 37 / 50%);
}

.loginForm /deep/ .el-form-item__label {
  justify-content: flex-start;
  margin-left: 10px;
}

.item {
    padding: 10px 15px 0px 10px;
}
</style>
