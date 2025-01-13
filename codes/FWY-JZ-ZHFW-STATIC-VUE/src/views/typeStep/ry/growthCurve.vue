<template>
  <div class="app-box data-box">
    <!-- 标题 -->
    <hometitle ref="hometitle" :titleData="titleData" />
    <div class="app-content">
      <div class="title-header">
        <div>
          <img src="@/assets/images/qy/tit_logo.png" alt="" class="left-img" />
          人员增长曲线
          <img src="@/assets/images/qy/left.png" alt="" class="left-l" />
        </div>
        <img src="@/assets/images/qy/right.png" alt="" class="right-l" />
      </div>
      <img src="@/assets/images/qy/line.png" alt="" class="line-img" />
      <el-form ref="queryForm" :model="queryParams">
        <el-row :gutter="20">
          <el-col :span="3">
            <div class="type-box">
              <span
                v-for="(item, ind) in typeList"
                :key="ind"
                :class="{ bg: activeInd == item.type }"
                @click="tabTitle(item.type)"
                >{{ item.name }}</span
              >
            </div>
          </el-col>
          <el-col :span="6">
            <div class="date-box">
              <span>选择时间</span>
              <el-date-picker
                v-model="times"
                type="daterange"
                class="sel-w"
                clearable
                value-format="yyyy-MM-dd"
                range-separator="——"
                start-placeholder="请选择"
                end-placeholder="请选择"
              />
              <i class="el-icon-date data_icon" />
            </div>
          </el-col>
          <el-col :span="3">
            <el-button size="small" class="view" @click="getList"
              >查询</el-button
            >
          </el-col>
        </el-row>
      </el-form>
      <el-table
        v-loading="loading"
        :data="dataList"
        height="calc(100vh - 260px)"
        class="big-table"
        :header-cell-style="{ background: '#07327F' }"
      >
        <el-table-column label="id" align="center" prop="id" />
        <el-table-column label="公司企业" align="center" prop="enterpriseId_dictText" />
        <el-table-column
          label="人员姓名"
          align="center"
          prop="staffName"
          width="100px"
        />
        <el-table-column
          label="人员状态"
          align="center"
          prop="status_dictText"
          width="120px"
        />
        <el-table-column label="手机号" align="center" prop="phone" />
        <el-table-column label="人员编号" align="center" prop="persoPersonnelNo" />
        <el-table-column label="性别" align="center" prop="sex_dictText" />
        <el-table-column label="籍贯" align="center" prop="registeredResidence" />
        <el-table-column label="出生年月" align="center" prop="birth" />
        <!-- <el-table-column label="经验年限" align="center" prop="experienceAgeLimit_dictText" /> -->
        <el-table-column label="注册时间" align="center" prop="createTime" />
        <el-table-column label="审核时间" align="center" prop="auditTime" />
        <el-table-column label="操作" align="center" width="200px">
          <template slot-scope="scope">
            <button type="text" @click="goDetails(scope.row)">查看详情</button>
          </template>
          
        </el-table-column>
        <div slot="empty">
          <img src="@/assets/images/empty_icon.png" class="empty-icon" alt="" />
        </div>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        background
        layout="prev, pager, next"
        :page.sync="pageNum"
        :limit.sync="pageSize"
        style="background: transparent"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import { staffInfoList } from "@/api/index.js"
import hometitle from "@/views/common/index_title";
export default {
  name: "qyIncrease",
  components: { hometitle },
  data() {
    return {
      times:[],
      titleData: {
        isTwo:1,
        content: '首页>>人员增长曲线'
      },
      queryParams: {},
      dataList: [],
      loading: false,
      total: 0,
      activeInd:1,
      pageNum:1,
      pageSize:10,
      typeList: [
        {
          name: "本月",
          count: 0,
          type: 1,
        },
        {
          name: "本年",
          count: 0,
          type: 2,
        },
        {
          name: "总计",
          count: 0,
          type: 0,
        },
      ],
    };
  },
  created(){
    this.getList()
  },
  methods: {
    goDetails(row){
      this.$router.push({
        path:'/ryDetails',
        query:{
          row:row
        }
      })
    },
    tabTitle(type){
      this.activeInd = type
      this.getList()
    },
    getList(){
      this.loading = true
      staffInfoList({
        pageNum:this.pageNum,
        pageSize:this.pageSize,
        dateType:this.activeInd == 1?1:this.activeInd == 2?2:'',
        startCreateTime:this.times[0],
        endCreateTime:this.times[1],
      }).then(res=>{
        this.loading = false
        this.dataList = res.result.records
        this.total = res.result.total
      })
    }
  },
};
</script>
<style lang="scss">
.data-box {
  background: #02122d;
  .el-table{
    background:rgba(11, 24, 49)!important ;
  }
  .el-table__row:hover{
    background: #07327F;
  }
  .el-table__empty-block{
    background-color: rgba(11, 24, 49);
  }
  .el-table th.el-table__cell > .cell{
    color:#fff
  }
  .el-loading-mask {
    background-color: rgba(11, 24, 49, 0.9);
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
    background-color: transparent;
    color: #27fffc;
  }
  .inputBorder {
    width: 100%;
    .el-input__inner {
      width: 100%;
      border-color: #2099c5;
      background-color: rgba(31, 158, 204, 0.2);
    }
  }
  .organ-option {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    .dept-opt {
      display: flex;
      align-items: center;
      font-size: 14px;
      cursor: pointer;
      &.scht {
        margin-right: 10px;
      }
      .btn-icon {
        width: 15px;
        height: auto;
        margin-right: 5px;
      }
    }
  }
  .hasBorder {
    width: 100%;
    height: 32px;
    display: flex;
    align-items: center;
    background-color: rgba(31, 158, 204, 0.2);
    border-radius: 4px;
    border: 1px solid #2099c5;
    .sp {
      flex-shrink: 0;
      display: inline-block;
      margin-left: 10px;
      font-size: 14px;
      color: #0ab3e4;
    }
    .el-input__inner {
      border: none;
      height: 32px;
      line-height: 32px;
      color: #fff;
      background-color: transparent;
    }
    .sel-w {
      flex: 1;
      flex-shrink: 0;
    }
  }
  .date-box {
    height: 32px;
    border: 1px solid #2099c5;
    position: relative;
    width: fit-content;
    display: flex;
    align-items: center;
    .el-date-editor {
      height: 28px;
      width: 12%;
      color: #fff;
    }
    .el-range-editor--medium .el-range-separator {
      width: 12%;
      line-height: 21px;
      color: #e2ebf1;
    }
    // 穿刺写法
    .el-icon-date {
      display: none;
    }
    span {
      display: inline-block;
      margin: 0 10px 0 20px;
      font-size: 14px;
      color: #0ab3e4;
    }
    .data_icon {
      position: absolute;
      top: 50%;
      right: 17px;
      z-index: 9;
      color: #1f9ecc;
      font-size: 14px;
      transform: translateY(-50%);
    }
    .el-range-input,
    .el-input__inner {
      color: #fff;
      border: none;
      background: transparent;
    }
    .sel-w {
      flex: 1;
      flex-shrink: 0;
    }
  }
}
</style>
<style lang="scss" scoped>
.app-content {
  width: 100%;
  padding: 0 55px;
  height: calc(100vh - 70px);
  background: #02122d;
}
.title-header {
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 18px;
  font-family: Source Han Sans CN;
  font-weight: 800;
  color: #ffffff;
  text-shadow: 0px 2px 10px rgba(50, 239, 245, 0.81);
  >div {
    display: flex;
    align-items: center;
  }
  .left-img {
    width: 47px;
    height: auto;
    display: inline-block;
    margin-right: 5px;
  }
  .left-l {
    width: 132px;
    height: 7px;
    margin-left: 20px;
  }
  .right-l {
    width: 132px;
    height: 7px;
  }
}
.line-img {
  width: 100%;
  height: 1px;
  margin-bottom: 15px;
}
.empty-icon {
  width: 300px;
}
.el-table {
  margin-top: 20px;
}
.type-box {
  width: 100%;
  height: 32px;
  display: flex;
  align-items: center;
  span {
    width: calc(100% / 3);
    font-size: 14px;
    height: 32px;
    line-height: 32px;
    text-align: center;
    display: inline-block;
    color: #03b2e7;
    border: 1px solid #03b2e7;
    cursor: pointer;
    &.bg {
      background: rgba(3,178,231,.2);
      color: #fff;
    }
  }
}
.view {
  background: #0ab3e4;
  border: 1px solid #2099c5;
  color: #fff;
}
.hasBorder {
  width: 100%;
  border: 1px solid #2099c5;
  color: #0ab3e4;
  background-color: rgba(31, 158, 204, 0.2);
}
</style>