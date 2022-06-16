import { ElNotification } from 'element-plus';
import { h } from 'vue';

const Notification = {

    notify(header, msg) {
        ElNotification({
          title: header,
          message: h('i', { style: 'color: teal' }, msg),
        })
    },

    alert(msg) {
        ElNotification({
          title: 'Error',
          message: msg,
          type: 'error',
        })
      }
}

export default Notification;