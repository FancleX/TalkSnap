import { ElMessage, ElMessageBox } from 'element-plus'

const MsgIndicator = {
  success(msg) {
    ElMessage({
      message: msg,
      type: 'success',
    })
  },
  warning(msg) {
    ElMessage({
      message: msg,
      type: 'warning',
    })
  },
  error(msg) {
    ElMessage.error(msg)
  },
  info(msg) {
    ElMessage({
      message: msg,
      type: 'info',
    })
  }
}

export default MsgIndicator;

