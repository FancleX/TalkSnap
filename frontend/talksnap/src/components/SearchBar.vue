<template>
  <div>
    <el-icon><Search /></el-icon>
    <el-input
      placeholder="Please Input"
      v-model="searchContent"
      style="margin: 12px 0 12px 0; width: 200px"
    />
    <el-card class="box-card" v-if="isSearch">
      <template #header>
        <div class="card-header">
          <span>Card name</span>
          <el-button class="button" text>Operation button</el-button>
        </div>
      </template>
      <div v-for="o in 4" :key="o" class="text item">{{ "List item " + o }}</div>
    </el-card>
  </div>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import { computed } from "@vue/runtime-core";

export default {
  name: "Searchbar",
  components: {
    Search,
  },
  data() {
    return {
      searchContent: "",
      isSearch: computed(() => this?.searchContent),
    };
  },
  methods: {},
};
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 200px;
}
</style>
