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
    // display content
    text: {
      type: String,
      default: ''
    },
    // tooltip displacement
    placement: {
      type: String,
      default: 'top'
    }
  },
  data() {
    return {
      tooltipFlag: false // dispay tooltip
    }
  },
  methods: {
    handleTooltipIn (event) {
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow:ellipsis;
}
</style>

