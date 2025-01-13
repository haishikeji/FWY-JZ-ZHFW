<template>
  <div id="rankTodo">
    <!-- 企业排行榜 -->
    <div class="top">
      <div class="rate-left">
        <img src="@/assets/rate_bg.png" alt="" class="rateimg" />
        <div class="middle">
          <div class="ranking_title">企业评级认定排行</div>
          <div style="margin-top: 10px; font-size: 18px; font-weight: 500">太原家政服务行业协会综合各项进行考察</div>
        </div>
      </div>
      <div class="right">
        <div>
          <img src="@/assets/home/rank_bg4.png" alt="" />
          <div class="rate-txt">基本评分</div>
        </div>
        <div class="rate-box">
          <img src="@/assets/home/rank_bg1.png" alt="" />
          <div class="rate-txt">企业评分</div>
        </div>
        <div>
          <img src="@/assets/home/rank_bg2.png" alt="" />
          <div class="rate-txt">服务评分</div>
        </div>
      </div>
    </div>
    <a-card class="down" :bordered="false">
      <div class="table-top" style="width: 60%">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper" style="width: 100%">
          <a-form layout="inline" @keyup.enter.native="searchQuery">
            <a-row style="width: 100%" :gutter="16">
              <a-col :span="8">
                <a-form-item label="企业">
                  <a-input placeholder="请输入企业名称" v-model="queryParam.enterpriseName"> </a-input>
                </a-form-item>
              </a-col>
              <a-col :span="4" :offset="1">
                <a-button @click="searchQuery" style="background: #5d7afa" type="primary">搜索</a-button>
              </a-col>
            </a-row>
          </a-form>
        </div>
      </div>
      <!-- table区域-begin -->
      <a-row>
        <a-col :span="24">
          <div class="title">企业排行版</div>
          <a-table
            ref="table"
            size="middle"
            :scroll="{ x: 600, y: 800 }"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{
              selectedRowKeys: selectedRowKeys,
              onChange: onSelectChange,
            }"
            class="j-table-force-nowrap"
            @change="handleTableChange"
          >
            <span slot="rowIndex" slot-scope="text, record, index">
              <span :class="rowIndex(index)" v-if="ipagination.current == 1"> {{ rowIndex(index) }}</span>
              <span v-else> {{ rowIndex(index) }}</span>
            </span>

            <span slot="enterpriseId" slot-scope="text">
              <j-ellipsis v-if="text.length > 13" :value="text" :length="13" />
              <span v-else>{{ text }} </span>
            </span>
            <span slot="action" slot-scope="text, record">
              <a @click="toDetail(record)" class="detail-a"><img src="@/assets/detail.png" alt="" /> 查看详情</a>
            </span>
          </a-table>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getAction } from '@/api/manage'

export default {
  name: 'rankTodo',
  mixins: [JeecgListMixin, mixinDevice],
  data() {
    return {
      dataSource: [],
      queryParam: {
        enterpriseName: '',
      },
      columns: [
        {
          title: '排行',
          dataIndex: '',
          key: 'rowIndex',
          width: 120,
          align: 'center',
          fixed: 'left',
          scopedSlots: {
            customRender: 'rowIndex',
          },
        },
        {
          title: '企业',
          align: 'center',
          dataIndex: 'enterpriseId_dictText',
          key: 'enterpriseId_dictText',
          scopedSlots: {
            customRender: 'enterpriseId',
          },
        },
        {
          title: '法人姓名',
          align: 'center',
          dataIndex: 'legalPersonName',
          key: 'legalPersonName',
        },
        {
          title: '企业评分',
          align: 'center',
          dataIndex: 'totalScore',
        },
        {
          title:'营业额(万元)',
          align:"center",
          dataIndex: 'turnover'
        },
        {
          title:'企业人数',
          align:"center",
          dataIndex: 'enterprisesNumber'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      gradeList: [],
      url: {
        list: '/enterprise/enterpriseGrade/gradeList',
      },
      waitPageNo: 1,
      waitPageSize: 10,
      total: 0,
      empty: true,
      spinning: false,
    }
  },
  created() {
    this.getGradeList(this.waitPageNo, this.waitPageSize)
  },
  methods: {
    toDetail(row) {
      this.$router.push('/rankdeatail?id=' + row.id)
    },
    // 获取评级待办列表
    getGradeList(pageNo, pageSize) {
      this.spinning = true
      var that = this
      let params = {
        status: 0,
        pageNo,
        pageSize,
      }
      getAction(this.url.list, params)
        .then((res) => {
          if (res.success) {
            this.gradeList = this.gradeList.concat(res.result.records)
            if (this.gradeList.length) {
              this.empty = false
            }

            this.total = res.result.total
          } else {
            that.$message.warning(res.message)
          }
          this.spinning = false
        })
        .finally(() => {
          this.spinning = false
        })
    },
    toHandle(row) {
      // 待处理
      this.$router.push('/rankdeatail?id=' + row.id + '&type=1')
    },
    scroll(event) {
      var pos = event.target.scrollTop
      var height = event.target.scrollHeight

      // console.log(scrollTop);
      if (height - pos < 470 && this.total > this.gradeList.length) {
        this.spinning = true
        setTimeout(() => {
          this.waitPageNo = this.waitPageNo + 1
          this.getGradeList(this.waitPageNo, this.waitPageSize)
          this.spinning = false
        }, 600)
      }
    },
    //排行
    rowIndex(i) {
      let ind = (this.ipagination.current - 1) * this.ipagination.pageSize + i
      let val = ind + 1 > 9 ? ind + 1 : 3 >= ind + 1 ? 'TOP0' + (ind + 1) : '0' + (ind + 1)
      return val
    },
  },
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>
<style lang="less" scoped>
.table-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;

  .table-operator {
    margin: 0;

    button {
      margin-bottom: 0;
      width: 90px;
    }

    button:last-of-type {
      margin-right: 0;
    }
  }
}
</style>
<style lang="less" scoped>
#rankTodo {
  

  .table-page-search-wrapper .ant-form-inline .ant-form-item {
    align-items: center;
    margin-bottom: 0;
    margin-right: 10px;
  }

  .table-page-search-wrapper .ant-form-inline .ant-form-item:last-child {
    margin-right: 0px;
  }

  .detail-a {
    color: #666666;
    font-weight: 400;
    font-style: normal;

    img {
      width: 15px;
      margin-right: 2px;
      height: auto;
    }
  }

  .top {
    width: 100%;
    padding: 0 50px 30px 50px;
    height: auto;
    border-radius: 6px;
    margin-bottom: 20px;
    background: url('../../assets/ranking_bg.png') no-repeat;
    background-size: cover;
    display: flex;
    color: #273b98 !important;
    align-items: center;
    justify-content: space-between;

    .rateimg {
      width: 248px;
      height: auto;
      margin-right: 26px;
    }

    .rate-left {
      display: flex;
      align-items: center;
    }

    .middle {
      letter-spacing: 2px;

      .ranking_title {
        font-size: 28px;
        font-weight: bold;
        color: #273b98;
        margin-top: 30px;
      }
    }

    .right {
      display: flex;
      text-align: center;
      font-weight: 500;
      margin-top: 36px;

      .rate-box {
        margin: 0 25px;
      }

      img {
        width: 60px;
        height: auto;
      }

      .rate-txt {
        margin-top: 6px;
        font-size: 16px;
      }
    }
  }

  .down {
    border-radius: 8px;
    .ant-table-thead {
      tr {
        th {
          background: #e5f1fe;
          border-bottom: 0;
        }
      }
    }

    .ant-table-tbody {
      .TOP01 {
        font-style: italic;
        font-weight: 600;
        color: #e27024;
      }
      .TOP02 {
        font-weight: 600;
        color: #f9a954;
      }
      .TOP03 {
        font-weight: 600;
        color: #f9d854;
      }

      td {
        background: rgba(229, 241, 254, 0.3);
      }
    }

    .j-table-force-nowrap {
      border-radius: 15px 15px 0 0;
      background: rgba(229, 241, 254, 0.3);

      .ant-table-header {
        border-radius: 15px 15px 0 0;
      }
    }

    ::-webkit-scrollbar-thumb {
      background: #50a5ff;
    }

    .title {
      font-size: 16px;
      font-weight: bold;
      color: #313740;
      line-height: 60px;
      position: relative;

      &::before {
        content: '';
        width: 4px;
        height: 28px;
        border-radius: 10px;
        background: linear-gradient(0deg, #ffffff00, #ffffff, #c7d1ff, #5d7afa);
        position: absolute;
        top: 50%;
        left: -24px;
        transform: translateY(-25%);
      }
    }

    .title1 {
      font-size: 16px;
      font-weight: bold;
      color: #313740;
      line-height: 60px;
      position: relative;
      padding-left: 20px;

      &::before {
        content: '';
        width: 4px;
        height: 28px;
        border-radius: 10px;
        background: linear-gradient(0deg, #ffffff00, #ffffff, #c7d1ff, #5d7afa);
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-25%);
      }
    }

    .ant-card {
      border: 0;

      .ant-card-head {
        height: 45px;
        line-height: 45px;
        background: #fcecd2;
        border-radius: 15px 15px 0px 0px;

        .ant-card-head-wrapper {
          height: 100%;
          width: 100%;
          border: 0;

          .ant-card-head-title {
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-content: center;
          }
        }
      }

      .ant-card-body {
        width: 100%;
        padding: 15px !important;
        overflow: auto;
        height: 456px;
        background: rgba(252, 236, 210, 0.2);
        border-radius: 0 0 20px 20px;

        .ant-spin-spinning {
          width: 100%;
          height: 100%;
        }
      }

      .ant-card-body-empty {
        height: 168px;
      }

      .list {
        height: auto;
        background: #ffffff;
        box-shadow: 0px 0px 24px 0px rgba(123, 101, 64, 0.06);
        border-radius: 10px;
        margin-bottom: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 25px;

        .left {
          .enterprise {
            font-size: 14px;
            font-weight: 500;
            color: #333333;
          }

          .staff {
            margin-top: 10px;
            font-size: 12px;
            font-weight: 500;
            color: #333333;
          }
        }

        .right {
          .ant-badge {
            cursor: pointer;
            font-size: 12px;
            font-weight: 500;
            color: #f6aa2b;
          }
        }
      }

      ::-webkit-scrollbar-thumb {
        width: 9px;
        height: 102px;
        background: #f6aa2b;
        border-radius: 5px;
      }
    }
  }
}
</style>