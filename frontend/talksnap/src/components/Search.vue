<template>
  <div class="searchbar">
    <el-form
        :model="basicInfo"
        size="mini"
        label-width="100px"
    >
          <el-form-item
              prop="startDate"
              style="width:100%; margin:auto;"
          >
            <el-autocomplete
                prefix-icon="Search"
                v-model.trim="basicInfo.selctValue"
                placeholder="Search user ..."
                :fetch-suggestions="querySearchAsync"
                :trigger-on-focus="false"
                @select="handleSelect"
                class="item"
            >
              <template slot-scope="{item}">
                <EllipsisTooltip :text="item.value"/>
              </template>
            </el-autocomplete>
          </el-form-item>
    </el-form>
  </div>
</template>

<script>
import EllipsisTooltip from '@/components/EllipsisTooltip.vue';
import ProfileFetcher from '@/service/user/profile';
import { Search } from "@element-plus/icons-vue";

export default {
  name: 'SearchBar',
  components: {
    EllipsisTooltip,
    Search
  },
  data() {
    return {
      basicInfo: {
        selctValue:''
      }
    };
  },
  methods: {
    querySearchAsync(queryString) {
      let list = [];
      let wtParams = {
        pageNo: 0,
        countPerPage: 5,
        displayName: queryString,
      };
      if (queryString) {
        const result = ProfileFetcher.searchUser(queryString);
        list = result;
        console.log(result)
        for (let i = 0; i < list.length; i++) {
          list[i].value = list[i].nickname;
        }
        let timeout = ''
        clearTimeout(timeout)
        timeout = setTimeout(() => {
          list = [];
        }, 100 * Math.random())
      };
    },
    handleSelect(item) {
      console.log("点击当前项的value值" + item.value);
    }
  },
};
</script>
<style scoped>
.searchbar /deep/ .el-form-item__content {
  margin-left: 0 !important;
}
</style>

