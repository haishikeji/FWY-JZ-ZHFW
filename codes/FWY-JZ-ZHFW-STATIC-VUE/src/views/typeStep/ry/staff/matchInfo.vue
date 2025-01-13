<template>
  <!-- 比赛信息 -->
  <div class="match-info" style="padding-left:50px">


    <div class="content">
      <div class="left">
        <div class="tab plat" :class="{
          'active': active == 'plat'
        }" @click="active = 'plat'">
          <!-- <div class="title">平台比赛</div>
          <div class="desc">平台报名比赛记录</div> -->
        </div>
        <div class="tab" :class="{
          'active': active == 'people'
        }" @click="active = 'people'">
          <!-- <div class="title">个人比赛</div>
          <div class="desc">个人报名比赛记录</div> -->
        </div>
      </div>

      <div class="right">
        <div v-show="active == 'plat'"  style="height:100%">
          <div v-if="plat.length == 0" class="empty">

            <div>
              <img src="@/assets/staff/empty.png" alt="">

            </div>
          </div>
        </div>
        <div v-show="active == 'people'"  style="height:100%">
          <el-row class="list" type="flex" justify="space-between" v-for="p in people" :key="p.id">
            <el-col :span='14' class="people_desc">
              <img :src="p.certificate || '@/assets/staff/train.png'" alt="">
              <!-- <img :src="getImgView(p.certificate) || '@/assets/staff/train.png'" alt="" :preview="'相关照片'"
                preview-text="相关照片"> -->
              <div class="text">
                <div class="name">{{ p.matchName }}</div>
                <div class="time">比赛时间：{{ p.matchTime || '无' }}</div>
                <div class="tag">
                  <div class="ks" v-if="p.matchLevel">{{ dist[p.matchLevel] }}</div>

                  <!-- <j-category-select v-if='p.projectType' :disabled="true" :multiple="true" v-model="p.projectType"
                    pcode="A01" /> -->
                </div>
              </div>
            </el-col>
            <el-col :span='5' class="middle">
              <div class="td"> <img src="@/assets/staff/vrz.png" alt=""><span>{{ p.matchResults }}</span> </div>
            </el-col>
            <!-- <el-col :span='5' class="middle">
              <div class="ck">
                <img src="@/assets/staff/look.png" alt="">
                <span>查看</span>
              </div>
            </el-col> -->
          </el-row>
          <div v-if="people.length == 0" class="empty">
            <div>
              <img src="@/assets/staff/empty.png" alt="">

            </div>
          </div>
        </div>


      </div>
    </div>


  </div>
</template>

<script>
import {assMatchStaffList,getDictItems} from "@/api/index.js"
// import { httpAction, getAction } from '@/api/manage'
// import { getFileAccessHttpUrl } from '@/api/manage'
export default {
  name: 'matchInfo',
  props: ['Infodata'],
  data() {
    return {
      form: {},
      active: 'people',
      people: [ ],
      plat: [],
      matchLevelColumn: [],
      dist: {
        // gjj: '国家级',
        // sj: '省级',
        // shij: '市级',
        // other: '其他',
      }
    }
  },
  mounted() {
    this.getLevel()
    this.getData()
        // getAction('/sys/dict/getDictItems/jz_match_level').then(res => {
              
        //       var arr = res.result
            
        //       let leaveTypeDist={};
        //     arr.forEach(ele=>{
               
        //          leaveTypeDist[ele.value]=ele.text 
        //       }) 
        //       this.dist=leaveTypeDist
         
              
        // })

    // console.log('this.matchLevelColumn');
    // console.log(this.matchLevelColumn);

    // getAction('ass/assMatchStaff/list', { pageNo: 1, pageSize: 3, matchType: 1, staffId: this.Infodata }).then(res => {

    //   this.people = res.result.records.map(ele => {
    //     let arr = ele.projectType.split(',')
    //     if (ele.projectType && arr.length > 2) {
    //       ele.projectType = arr[0] + ',' + arr[1]
    //     }
    //     return ele
    //   });


    // })
  },
  watch: {
    Infodata: {
      deep: true,
      handler(val) {
        if (val) {
         
          // getAction('ass/assMatchStaff/list', { pageNo: 1, pageSize: 3, matchType: 1, staffId: val }).then(res => {

          //   this.people = res.result.records.map(ele => {
          //     let arr = ele.projectType.split(',')
          //     if (ele.projectType && arr.length > 2) {
          //       ele.projectType = arr[0] + ',' + arr[1]
          //     }
          //     return ele
          //   });

          //   // console.log('this.people');
          //   // console.log(this.people);
          // })
        }
      },
    },
  },
  methods: {
    getLevel(){
      getDictItems().then(res=>{
        var arr = res.result
        let leaveTypeDist={};
        arr.forEach(ele=>{
          leaveTypeDist[ele.value]=ele.text 
        }) 
        this.dist=leaveTypeDist
      })
    },
    getData(){
      assMatchStaffList({
        matchType: 1, 
        staffId: this.Infodata
      }).then(res=>{
        this.people = res.result.records.map(ele => {
          let arr = ele.projectType.split(',')
          if (ele.projectType && arr.length > 2) {
            ele.projectType = arr[0] + ',' + arr[1]
          }
          return ele
        });
      })
    },
    getImgView(text) {
      if (text && text.indexOf(",") > 0) {
        text = text.substring(0, text.indexOf(","))
      }
      return getFileAccessHttpUrl(text)
    },


  }
}
</script>

<style lang="scss" scoped>
.match-info {

  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .getMore {
      width: 109px;
      height: 29px;
      background: #128BED;
      border-radius: 5px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 16px;
      color: #fff;
    }
  }

  .content {
    display: flex;
    justify-content: space-between;
    min-height: 422px;

    .left {
      width: 200px;

      .tab {
        width: 200px;
        height: 90px;
        padding: 16px 11px;
        background: url('~@/assets/staff/person_game.png') no-repeat;
        background-size: 100%;
        cursor: pointer;

        .title {

          font-size: 16px;
          font-family: Source Han Sans CN;
          font-weight: bold;

          color: #0098FF;
        }

        .desc {

          color: #0098FF;
          font-size: 10px;
          font-family: Source Han Sans CN;
          font-weight: 400;

        }
      }

      .plat {
        div {
          color: #588AF9 !important;
        }

        background: url('~@/assets/staff/plat_game.png') no-repeat;
        background-size: 100%;
        margin-bottom: 16px;
      }

      .active {
        position: relative;

        &::after {
          display: block;
          content: '';
          width: 4px;
          height: 71px;
          background: #588AF9;
          position: absolute;
          top: 6px;
          right: 0;
        }
      }
    }

    .right {
      flex: 1;
      height: 422px;
      margin-left: 20px;
      .list {
        width:100%;
        // height: 112px;
        background: rgba(21,73,146,.4);
        border-radius: 8px;
        padding: 16px;
        margin-bottom: 16px;

        .people_desc {
          display: flex;
          align-items: center;

          img {
            width: 111px;
            height: 80px;
            background: #FFFFFF;
            border: 1px solid #E6E6E6;
            border-radius: 8px;

          }

          .text {
            margin-left: 18px;
          }

          .name {
            font-size: 16px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #fff;
          }

          .time {
            font-size: 13px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #fff;
            margin: 10px 0;
          }

          .tag {
            display: flex;
            align-items: center;


            .ks {
              white-space: nowrap;
              padding: 5px 9px;
              background:rgba(36,78,162,0.4);
              border: 1px solid #005BFF;
              border-radius: 5px;

              font-size: 13px;
              color: #24E8AA;
            }
          }
        }

        .middle {
          display: flex;
          justify-content: center;
          align-items: center;

          .td {
            white-space: nowrap;
            padding: 8px 10px;
            font-size: 13px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #FAFAFA;

            background: #128BED;
            border-radius: 5px;

            span {
              margin-left: 6px;
            }

            img {
              width: 13px;
              height: 13px;
            }


          }

          .ck {
            white-space: nowrap;
            font-size: 13px;
            color: #0C40C7;

            span {
              margin-left: 6px;
            }
          }
        }
      }

      .empty {
        display: flex;
        justify-content: center;
        align-items: center;
             height: 100%;
          img {
            width: 200px;
       
      
 
            border-radius: 8px;
          }
      }
    }

  }
}




h1 {
  width: 50%;
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

<style lang="scss">
.match-info {


  .ant-select-disabled {
    .ant-select-selection {
      background: none !important;
      border: none !important;
      cursor: pointer !important;

      .ant-select-selection__choice {
        padding: 5px 9px;
        background: #fff;
        border: 1px solid #FFC05C;
        border-radius: 5px;
        min-height: 31.5px;
        font-size: 13px;
        font-family: Source Han Sans CN;
        font-weight: 500;
        color: #FFC05C;
        text-align: center;
        white-space: nowrap;
        margin-top: 0;

      }
    }

    .ant-select-selection__rendered {
      height: 31.5px;
    }
  }
}
</style>