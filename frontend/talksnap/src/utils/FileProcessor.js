import axios from 'axios';
import MsgIndicator from './msgIndicator';
import Notification from "@/utils/notification";
import store from "@/store";

const FileProcessor = {

  async upload(file, select) {
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
      .catch(err => {
        Notification.alert(err)
      });

    if (result) {
      // store locally
      // alter the profile
      let profile = store.getters.getMyProfile;
      if (select === '0') {
        profile.bg_img = result;
        store.commit('setMyProfile', profile)
      } else {
        profile.profile_img = result;
        store.commit('setMyProfile', profile);
      }
    }
  },

}

export default FileProcessor;