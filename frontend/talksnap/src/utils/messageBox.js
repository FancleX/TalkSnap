import { ProfileEditor } from '@/service/user/profile'
import { ElMessage, ElMessageBox } from 'element-plus'
import MsgIndicator from '@/utils/msgIndicator'

const MessageBox = {

  editNickname() {
    ElMessageBox.prompt('Please input your new nickname', 'Tip', {
      confirmButtonText: 'Edit',
      cancelButtonText: 'Cancel',
      draggable: true,
      inputValidator: (value) => {
        if (!value) {
          return "Nickname cannot be empty";
        }
        if (value.length < 3 || value.length > 10) {
          return "Nickname should be 3 to 10 characters";
        }
      },
      beforeClose: (action, instance, done) => {
        if (action === 'confirm') {
          instance.confirmButtonLoading = true
          instance.confirmButtonText = 'Loading...'
          setTimeout(() => {
            done()
            setTimeout(() => {
              instance.confirmButtonLoading = false
            }, 300)
          }, 500)
        } else {
          done()
        }
      },
    })
      .then(async({ value }) => {
        const result = await ProfileEditor.editNickname(value);
        if (result) {
          MsgIndicator.success('Edit completed');
        }
      })
      .catch(() => {
        MsgIndicator.info('Edit canceled');
      })
  },

  editEmail() {
    ElMessageBox.prompt('Please input your new email', 'Tip', {
      confirmButtonText: 'Edit',
      cancelButtonText: 'Cancel',
      draggable: true,
      inputPattern:
        /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
      inputErrorMessage: "Invalid email",
      beforeClose: (action, instance, done) => {
        if (action === 'confirm') {
          instance.confirmButtonLoading = true
          instance.confirmButtonText = 'Loading...'
          setTimeout(() => {
            done()
            setTimeout(() => {
              instance.confirmButtonLoading = false
            }, 300)
          }, 500)
        } else {
          done()
        }
      },
    })
      .then(async({ value }) => {
        const result = await ProfileEditor.editEmail(value);
        if (result) {
          MsgIndicator.success('Edit completed');
        }
      })
      .catch(() => {
        MsgIndicator.info('Edit canceled');
      })
  },

  editPassword() {
    ElMessageBox.prompt('Please input your new email', 'Tip', {
      confirmButtonText: 'Edit',
      cancelButtonText: 'Cancel',
      inputPattern:
        /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
      inputErrorMessage: "Invalid email",
      beforeClose: (action, instance, done) => {
        if (action === 'confirm') {
          instance.confirmButtonLoading = true
          instance.confirmButtonText = 'Loading...'
          setTimeout(() => {
            done()
            setTimeout(() => {
              instance.confirmButtonLoading = false
            }, 300)
          }, 500)
        } else {
          done()
        }
      },
    })
      .then(async({ value }) => {
        const result = await ProfileEditor.editEmail(value);
        if (result) {
          MsgIndicator.success('Edit completed');
        }
      })
      .catch(() => {
        MsgIndicator.info('Edit canceled');
      })
  },


}

export default MessageBox;