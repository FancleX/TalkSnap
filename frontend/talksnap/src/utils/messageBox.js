import { ElMessage, ElMessageBox } from 'element-plus'

const MessageBox = {

  selectHandle(select) {
    
  },

  fileUpload() {
    ElMessageBox.prompt('Please input your e-mail', 'Tip', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      inputPattern:
        /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
      inputErrorMessage: 'Invalid Email',
    })
      .then(({ value }) => {
        ElMessage({
          type: 'success',
          message: `Your email is:${value}`,
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Input canceled',
        })
      })
  },
}

export default MessageBox;