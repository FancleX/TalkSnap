<template>
  <el-dialog draggable>
    <template #header="{ titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass" style="align-item: center">
          <el-icon color="#ff9966" :size="30"><Warning /></el-icon>
        </h4>
      </div>
    </template>
    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="Current password" label-width="140px" prop="oldPassword" style="padding: 0px 30px 40px 10px;">
        <el-input type="password" v-model="form.oldPassword" autocomplete="off" />
      </el-form-item>
      <el-form-item label="New password" label-width="140px" prop="newPassword" style="padding: 0px 30px 0px 10px;">
        <el-input type="password" v-model="form.newPassword" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="onClose">Cancel</el-button>
        <el-button type="primary" @click="submitForm('form')">Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { ProfileEditor } from '../service/user/profile';
import MsgIndicator from '@/utils/msgIndicator';

export default {
  name: "DialogBox",
  data() {
    return {
      form: {
        oldPassword: "",
        newPassword: "",
      },
      rules: {
        oldPassword: [
          { required: true, message: "Password cannot be empty", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "Password should be 6 to 20 charaters",
            trigger: "blur",
          },
        ],
        newPassword: [
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
    onClose() {
        MsgIndicator.info('Edit canceled');
        this.$emit('update:modelValue', false);
        this.form = {};
    },
     submitForm(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          const result = await ProfileEditor.editPassword(this.form);
          if (result) {
            this.$emit('update:modelValue', false);
            this.form = {};
          }
        } else {
          MsgIndicator.error("Something wrong with your information, please try again");
        }
      });
    },
  },
};
</script>

<style></style>
