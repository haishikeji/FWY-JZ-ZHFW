<template>
  <!-- 企业消杀 -->
  <div class="disinfect-info">
    <h1>企业消杀</h1>
    <div class="disinfect-records">
      <h4>{{new Date().getFullYear()}}年</h4>
      <div style="height: 380px;overflow: auto;" v-if="enterpriseEaLogList.length>0">
        <div class="constructional-step">
          <div class="item-box" :class="index === enterpriseEaLogList.length-1?'':'pb20'" v-for="(item,index) in enterpriseEaLogList" :key="index + 'step'">
            <div class="vertical-line" v-if="index < enterpriseEaLogList.length-1"></div>
            <div class="dot-box" :class="index==0?'cur':'ago'"><i class="dot"></i></div>
            <!--  :style="index ==0?'color: #FF0000':index==1?'color: #6597FC':'color: #111111'" -->
            <i class="index-box icon-box text-c" :style="index ==0?'color: #FF0000':'color: #111111'">{{ isXS(item.eaTime) }}</i>
            <div class="card-box" :class="index==0?'curbg':'agobg'">
              <div>
                <p class="fs16 title-text fw">消杀人员:
                   <img src="@/assets/images/qy/xs_fzr.png" alt="" class="ml20"> 
                <span class="ml10">{{item.eaUser || '--'}}</span></p>
                <p>消杀方式: <a-tag color="blue" class="ml20">{{formatterType(item.eaType)}}</a-tag> </p>
                <p>消杀时间: <span class="ml20">{{item.eaTime || '--  --  --'}}</span></p>
              </div>
              <div class="imgs-card" v-if="item.eaPhotos">
                <img :src="item.eaPhotos.split(',')[0]" :preview="item.id" height="25px" alt=""/>
                <p class="mask" @click="isShowEa(item)">
                    <span>共{{item.eaPhotos.split(',').length}}张</span>
                </p>
              </div>
              <div class="wxs" v-else>
                 <img src="@/assets/images/qy/wxs.png" alt="" >
                 <span class="ml10">企业未消杀</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="empty">
         <a-empty
          :image-style="{
            height: '200px',
          }"
        >
          <span slot="description"> 暂无消杀记录 ！</span>
        </a-empty>
      </div>
    </div>
    <el-dialog :visible.sync="isShowStore" v-if="isShowStore" width="50%">
       <div class="tops">
        <div>
          <img
            src="@/assets/images/qy/left_logo.png"
            alt=""
            class="left-arrow"
          />企业消杀图片
        </div>
        <img
          src="@/assets/images/close.png"
          alt=""
          class="close"
          @click="isShowStore = false"
        />
      </div>
      <el-carousel height="300px" v-if="eaImgList.length > 0" >
        <el-carousel-item v-for="(item,i) in eaImgList" :key="i">
          <img :src="item" alt="" style="width: 100%;height: 300px" />
        </el-carousel-item>
      </el-carousel>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'disinfect',
  props: ["enterpriseEaLogList"],
  data() {
    return {
      moment,
      eaImgList: [],
      isShowStore: false,
      eglogOptions: [],
      url: {
          list: "/enterprise/enterpriseEquipment/list"
      }
    }
  },
  created(){
    // 消杀方式
    ajaxGetDictItems("enterprise_ealog").then((res) => {
        if (res.success) {
            this.eglogOptions = res.result;
        }
    })
  },
  methods: {
    isShowEa(item){
      this.isShowStore = true;
      this.eaImgList = item.eaPhotos.split(',');
    },
    isXS(time){
        // 今日是否消杀
        let today = moment(new Date()).format('YYYY-MM-DD');
        if(time ==  today){
            return "今日"
        }else{
            return moment(time).format('MM-DD')
        }
    },
    formatterType(val){
      if(this.eglogOptions.find(item => item.value == val)){
        return this.eglogOptions.find(item => item.value == val).label;
      }
    }
  }
}
</script>
<style lang="scss">
.el-dialog {
  width: 100%;
  background: url(~@/assets/images/border.png) no-repeat;
  background-size: 100% 100%;
  .el-dialog__header {
    display: none !important;
  }
  .dialog-box {
    width: 92%;
    margin: 0 auto;
  }
  .tops {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 20px;
    color: #fff;
    margin-bottom: 20px;
    .left-arrow {
      width: 45px;
      height: 30px;
      margin-right: 4px;
      display: inline-block;
      vertical-align: middle;
    }
    .close {
      width: 25px;
      height: 25px;
      cursor: pointer;
    }
  }
}
</style>
<style lang="scss" scoped>
.disinfect-info {
  width: 96%;
  margin-left: 50px;
}
::v-deep ::-webkit-scrollbar-thumb {
    background: #006FDE;
    border-radius: 2px;
}
::v-deep ::-webkit-scrollbar-thumb:hover {
    background: #006FDE;
}
.ml20 {
  margin-left: 20px;
}
.ml10 {
  margin-left: 10px;
}
.imgs-card {
    width: 120px;
    height: 120px;
    position: relative;
    img {
        width: 120px;
        height: 120px;
        border-radius: 5px;
    }
    .mask {
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        width: 120px;
        height: 120px;
        border-radius: 5px;
        background: rgba(0,0,0,.5);
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: #fff;
        margin-bottom: 0;
        cursor: pointer;
        span {
            line-height: 30px;
            font-size: 15px;
            letter-spacing: 1px;
        }
    }
}
.disinfect-records {
  margin-top: 25px;
  width: 100%;
  height: 500px;
  border-radius: 5px;
  // background: url("../../../assets/images/qy/disinfect_bg.png") no-repeat;
  background-size: cover;
  padding: 28px 31px;
  box-sizing: border-box;
  h4 {
    font-size: 30px;
    font-weight: 600;
    color: #000000;
  }
}
.constructional-step{
  width: 67%;
  height: 100%;
  padding: 0 20px;
  .item-box{
    position: relative;  //父级定位
    display: flex;  // flex 布局
    align-items:stretch;   // 设置为stretch  直线的高度才会为父级的高度
    .index-box{
      position:absolute;
      top:20px;
      width: 65px;
      font-weight: 600;
      letter-spacing: 1px;
      text-align: right;
    }
    .vertical-line{
      position: absolute;  //设置为absolute 
      left:100px;
      top:28px;
      width: 2px;
      height: 100%;
      border-left: 2px solid #EBEBEB;
    }
    .dot-box{
      position: absolute;  //设置为absolute 
      left:92px;
      top:25px;
      width: 18px;
      height: 18px;
      border-radius: 50%;
      text-align: center;
      .dot {
        width: 10px;
        height: 10px;
        margin-bottom: 2px;
        display: inline-block;
        border-radius: 50%;
      }
      &.cur {
        background: #F7C8CC;
        .dot {
          background: #FF0000;
        }
      }
      &.ago {
        background: #D8E6FE;
        .dot {
          background: #6597FC;
        }
      }
    }
    .card-box{
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 20px 15px;
      border: 6px solid #FFFFFF;
      box-shadow: 0px 0px 38px 0px rgba(12,64,199,0.11);
      border-radius: 16px;
      margin-left: 150px;
      margin-bottom: 30px;
      font-size: 14px;
      color: #2B2B2B;
      position: relative;
      &::after {
        position: absolute;
        content: "";
        left: -6px;
        top: 30%;
        width: 4px;
        height: 22px;
        background: #6597FC;
        border-radius: 0px 2px 2px 0px;
      }
      .wxs {
        img {
          width: 22px;
          vertical-align: text-bottom;
        }
        span {
          font-size: 22px;
          color: #0690FA;
          display: inline-block;
          font-weight: 600;
          margin-right: 18px;
        }
      }
      p {
        line-height: 40px;
        margin-bottom: 0;
      }
      .title-text {
        img {
          width: 12px;
        }
      }
      &.curbg {
        // background: url("../../../assets/images/qy/cur_bg.png") no-repeat;
        background-size: contain;
      }
      &.agobg {
        // background: url("../../../assets/images/qy/ago_bg.png") no-repeat;
        background-size: contain;
      }
    }
  }
}
h1 {
  width: 100%;
  letter-spacing: 1px;
  padding: 0 50px;
  height: 70px;
  line-height: 70px;
  font-size: 15px;
  color: #fff;
  font-weight: 600;
  position: relative;
  margin-bottom: 0;
  &::before {
    content: '';
    width: 100%;
    height: 2px;
    display: inline-block;
    position: absolute;
    left: 0px;
    right: 0;
    top: 0;
    background: url(~@/assets/images/qy/top_line.png) no-repeat;
    background-size: cover;
  }
}
</style>