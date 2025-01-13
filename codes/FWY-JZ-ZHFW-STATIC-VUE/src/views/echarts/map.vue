<template>
  <div class="amap-page-container">
    <div id="container"></div>
  </div>
</template>
<script>
import echarts from "echarts";
import "echarts-extension-amap";
import shanxi from "@/utils/map/json/province/shanxi.json";
export default {
  props: ['aData'],
  data() {
    return {
      //地图列表
      markers: [],
      center: [111.95, 37.65],
      zoom: 7,
      dataList: [],
    };
  },
  mounted() {
    this.myEcharts();
  },
  watch: {
    aData(val){
      this.myEcharts();
    }
  },
  methods: {
    myEcharts() {
      var that = this;
      let myChart = echarts.getInstanceByDom(
        document.getElementById("container")
      );
      if (!myChart) {
        myChart = echarts.init(document.getElementById("container"));
      }
      echarts.registerMap("shanxi", shanxi);
      var option = {
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
              return params.name
            }
        },
        geo: {
          map: "shanxi",
          aspectScale: .75, //长宽比
          zoom: 1.2,
          roam: false,
          itemStyle: {
            normal: {
              areaColor: "#3A76C6",
              shadowColor: "#64C6F2",
              shadowOffsetX: 6,
              shadowOffsetY: 14,
            },
            emphasis: {
              areaColor: "#2AB8FF",
              borderWidth: 0,
              color: "green",
              label: {
                show: false,
              },
            },
          },
        },
        series: [
          {
            type: "map",
            roam: true,
            label: {
              normal: {
                show: true,
                textStyle: {
                  color: "#C2C6CC",
                },
              },
              emphasis: {
                textStyle: {
                  color: "#fff",
                },
              },
            },

            itemStyle: {
              normal: {
                borderColor: "#4EA4C8",
                borderWidth: 1.5,
                areaColor: "#3A78CA",
              },
              emphasis: {
                areaColor: "#2AB8FF",
                borderWidth: 0,
                color: "green",
              },
            },
            zoom: 1.2,
            roam: false,
            map: "shanxi", //使用
          },
          {
            type: "scatter",
            coordinateSystem: "geo",
            itemStyle: {
                color: '#f00',
            },
            symbol: function (value, params) {
                return params.data.img;
            },
            symbolSize: [32, 41],
            symbolOffset: [0, -20],
            z: 9999,
            data: that.aData,
          },
        ],
      };
      myChart.setOption(option, true);
    }
  },
};
</script>

<style lang="scss">
.amap-page-container {
  width: 100%;
}
#container {
  width: 100%;
  height: 100%;
}
</style>