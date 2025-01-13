<template>
  <!-- 荣誉表彰 -->
  <div class="honor-info">
    <h1>荣誉表彰</h1>
    <div>
      <a-spin :spinning="spinning">
        <a-carousel dot-position="bottom" dots-class="slick-dots" v-show="num" :after-change="onChange">
          <div v-for="i in num" :key="i">
            <div class="ccie-box">
              <div v-for="ic in list" :key="ic.id" class="ccie-item">
                <img :src="getImgView(ic.honorPhoto)" alt="" class="ccie-img" />
                <p class="ccie-text">
                  <img src="@/assets/qydetail/fcicon.png" alt="" class="text-icon" />
                  <span class="tit">{{ ic.honorName }}</span>
                </p>
                <div class="ccie-time">
                  <div class="ks">
                    {{ dist[ic.honorLevel] }}
                  </div>
                  <div>获得时间：{{ ic.honorDate }}</div>
                </div>
              </div>
            </div>
          </div>
        </a-carousel>
      </a-spin>
      <div v-show="!num" class="empty">
        <div>
          <img src="@/assets/staff/empty.png" alt="" />
          <div class="text">暂无</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { httpAction, getAction, getFileAccessHttpUrl } from '@/api/manage'
export default {
  name: 'honorInfo',
  props: ['Infodata'],
  data() {
    return {
      value: '',
      list: [],
      dist: {},
      num: 0,
      queryParam: {
        pageNo: 1,
        pageSize: 3,
      },
      spinning: false,
    }
  },
  watch: {
    Infodata: {
      deep: true,
      handler(val) {
        if (val) {
          this.queryParam.staffId = val
          this.getList()
        }
      },
    },
  },

  created() {
    getAction('/sys/dict/getDictItems/jz_match_level').then((res) => {
      var arr = res.result
      let leaveTypeDist = {}
      arr.forEach((ele) => {
        leaveTypeDist[ele.value] = ele.text
      })
      this.dist = leaveTypeDist
    })
    this.queryParam.staffId = this.Infodata
    this.getList()
  },
  methods: {
    getList() {
      getAction('/enterprise/enterpriseStaffHonor/list', this.queryParam).then((res) => {
        this.num = res.result.pages
        this.list = res.result.records
        this.spinning = false
        if (this.list.length > 0) {
          this.list = this.list.map((ele) => {
            ele.honorPhoto = this.getImgView(ele.honorPhoto)
            return ele
          })
        }
      })
    } /* 图片预览 */,
    getImgView(text) {
      if (text && text.indexOf(',') > 0) {
        text = text.substring(0, text.indexOf(','))
      }
      return getFileAccessHttpUrl(text)
    },
    onChange(a, b, c) {
      console.log(a, b, c)
      this.spinning = true
      this.queryParam.pageNo = Number(a) + 1
      this.getList()
    },
  },
}
</script>
<style lang="less">
.honor-info {
  .ant-carousel .slick-slide {
    height: 380px;
    overflow: hidden;
  }

  .ant-carousel .slick-dots-bottom {
    bottom: 0;
    height: 5px;
    border-radius: 30px;
  }

  .ant-carousel .slick-dots li button {
    background: #cccccc;
    height: 5px;
    border-radius: 30px;
  }

  .ant-carousel .slick-dots li {
    vertical-align: middle;
  }

  .ant-carousel .slick-dots li.slick-active button {
    background: #0c40c7;
    height: 5px;
    border-radius: 30px;
  }
}
</style>
<style lang="less" scoped>
.honor-info {
  margin-top: 50px;
  border-top: 1px solid #ebebeb;

  .empty {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;

    img {
      width: 30%;
    }

    .text {
      color: #46bfbf;
      font-size: 20px;
      margin-top: -25px;
    }
  }
}

.ccie-box {
  display: flex;
  background: #fafafa;
  box-shadow: 0px 0px 16px 0px rgba(12, 64, 199, 0.11);

  .ccie-item {
    width: calc(100% / 3);
    background: #fafafa;
    border: 6px solid #ffffff;
    border-radius: 8px;
    padding: 10px;
    box-sizing: border-box;

    &:nth-child(2) {
      margin: 0 8px;
    }

    .ccie-img {
      width: 100%;
      height: 215px;
      display: block;
      margin-bottom: 10px;
      border-radius: 5px;
    }

    .text-icon {
      width: 15px;
      vertical-align: middle;
      display: inline-block;
      margin-right: 15px;
    }

    .ccie-text {
      color: #000000;
      line-height: 26px;
    }

    .ccie-time {
      color: #666666;
      font-size: 12px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .ks {
        white-space: nowrap;
        padding: 5px 9px;
        background: #ffffff;
        border: 1px solid #6adea0;
        border-radius: 5px;

        font-size: 13px;
        color: #6adea0;
      }
    }
  }
}

.upload-btns {
  height: 25px;
  line-height: 25px;
  display: inline-block;
  padding: 0px 14px;
  margin-bottom: 15px;
  color: #fff;
  font-size: 13px;
  cursor: pointer;
  background: #128bed;
  box-shadow: 0px 6px 16px 0px rgb(18 139 237 / 65%);
  border-radius: 0px 16px 16px 16px;
}

h1 {
  width: 100%;
  height: 70px;
  line-height: 70px;
  font-size: 15px;
  color: #000000;
  font-weight: 600;
  position: relative;
  margin-bottom: 0;

  &::before {
    content: '';
    position: absolute;
    left: 16px;
    top: 0;
    display: inline-block;
    height: 6px;
    width: 30px;
    border-radius: 0px 0px 2px 2px;
    background: #128bed;
  }
}
</style>