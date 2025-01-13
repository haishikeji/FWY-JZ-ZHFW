<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
    weekData:{
      type:Array,
    },
    lastWeekData:{
      type:Array,
    }
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      this.chart.setOption({
        grid: {
          top: 20,
          right: 20,
          bottom: 20,
          left: 34,
        },
        tooltip: {
          show: true,
          formatter: (a) => {
            let str =
              a.componentIndex === 1
                ? a.marker + " 上" + a.name + "：" + a.value / 1000 + "K"
                : a.marker + " 本" + a.name + "：" + a.value / 1000 + "K";
            return str;
          },
        },
        xAxis: {
          type: "category",
          data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        },
        yAxis: {
          type: "value",
          minInterval: 1000,
          min: 0,
          axisLabel: {
            formatter: (a) => {
              a = +a;
              return isFinite(a) && a != 0 ? Math.ceil(+a / 1000) + "K" : "0";
            },
          },
        },
        series: [
          {
            data: this.weekData,
            type: "bar",
            itemStyle: { color: "#1AAAEB" },
          },
          {
            data: this.lastWeekData,
            type: "bar",
            barGap: "0",
            itemStyle: { color: "#DBDFE5" },
          },
        ],
      });
    },
  },
};
</script>

<style lang="scss"></style>
