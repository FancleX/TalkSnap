import axios from 'axios';
import MsgIndicator from './msgIndicator';
import Notification from "@/utils/notification";
import store from "@/store";

const FileProcessor = {

  upload: async(file, select) => {
    let type;
    if (select === '0') {
      type = 'background';
    } else {
      type = 'profile';
    }
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Type': type
      }
    }
    // create form data object
    const param = new FormData();
    param.append('file', file.raw);
    const result = await axios.post('/user/picture/imgUpdate', param, config)
      .then(res => {
        if (res.data.code == 200) {
          MsgIndicator.success('Upload completed');
          return res.data.data;
        }
        MsgIndicator.error(res.data.message);
        return null;
      })

    if (result) {
      // store locally
      // alter the profile
      if (select === '0') {
        store.dispatch('setMyProfileBg', result);
      } else {
        store.dispatch('setMyProfileAvatar', result);
      }
    }
  },

}

export default FileProcessor;