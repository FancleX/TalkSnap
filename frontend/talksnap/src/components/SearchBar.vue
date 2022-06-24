<template>
  <div class="searchbar"> 
    <el-form :model="basicInfo" size="default" label-width="100px">
      <el-form-item prop="startDate" style="width: 100%; margin: auto">
        <el-autocomplete
          prefix-icon="Search"
          v-model.trim="basicInfo.selectValue"
          placeholder="Search user ..."
          :fetch-suggestions="querySearchAsync"
          :trigger-on-focus="false"
          @select="handleSelect"
        >
          <template v-slot:item="{ item }">
            <EllipsisTooltip :text="item.value" />
          </template>
        </el-autocomplete>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import EllipsisTooltip from "@/components/EllipsisTooltip.vue";
import ProfileFetcher from "@/service/user/profile";
import { Search } from "@element-plus/icons-vue";

export default {
  name: "SearchBar",
  components: {
    EllipsisTooltip,
    Search,
  },
  data() {
    return {
      basicInfo: {
        selectValue: "",
      },
    };
  },
  methods: {
    async querySearchAsync(queryString, callback) {
      let list = [];
      if (queryString) {
        // get object list
        const result = await ProfileFetcher.searchUser(queryString);
        list = result;
        for (let i = 0; i < list.length; i++) {
          list[i].value = list[i].nickname;
        }
        setTimeout(() => {
          callback(list);
        }, 300);
      }
    },
    handleSelect(item) {
      // get selected object
      const user = JSON.stringify(item);
      // transfer the user info and display the individual bio
      this.$router.push({ name:'biocard', params: { user: encodeURIComponent(user) } });
    },
  },
};
</script>
<style scoped>
.searchbar :deep() .el-form-item__content {
  margin-left: 0 !important;
}
</style>
