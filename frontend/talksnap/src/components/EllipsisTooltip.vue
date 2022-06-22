<template>
  <el-tooltip
    ref="tlp"
    :content="text"
    effect="dark"
    :disabled="!tooltipFlag"
    :placement="placement"
    class="tooltip"
  >
    <div
        class="tooltip-wrap"
        @mouseenter="handleTooltipIn($event)"
        @mouseleave="handleTooltipOut($event)"
    >
    {{ text }}</div>
  </el-tooltip>
</template>
<script>
export default {
  name: 'EllipsisTooltip',
  props: {
    // 字符内容
    text: {
      type: String,
      default: ''
    },
    // tooltip显示位置
    placement: {
      type: String,
      default: 'top'
    }
  },
  data() {
    return {
      tooltipFlag: false // 是否显示tooltip
    }
  },
  methods: {
    handleTooltipIn (event) {
      // scrollWidth: 对象的实际内容的宽度，不包边线宽度，会随对象中内容超过可视区后而变大。
      // offsetWidth对象整体的实际宽度，包滚动条等边线，会随对象显示大小的变化而改变。
      this.tooltipFlag = event.target.scrollWidth > event.target.offsetWidth;
    },
    handleTooltipOut(event) {
      this.tooltipFlag = false;
    }
  },
};
</script>
<style>
.tooltip-wrap{
  width: 100%;
  /*文本不换行*/
  white-space: nowrap;
  overflow: hidden;
  /*文字超出用省略号*/
  text-overflow:ellipsis;
}
</style>

