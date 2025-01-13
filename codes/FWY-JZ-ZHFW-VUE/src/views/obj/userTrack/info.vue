<template>
  <a-modal
    title="学习信息"
    :visible="dialogVisible"
    width="60%"
    :footer="null"
    @cancel="colseDialog()"
    v-if="dialogVisible"
  >
    <a-row :gutter="24">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-descriptions title="基本信息" :column="{ xs: 4, sm: 4, md: 4 }">
              <a-descriptions-item label="姓名" :span="1">
                {{ userForm.nickName }}
              </a-descriptions-item>
              <a-descriptions-item label="手机号" :span="1">
                {{ userForm.phonenumber }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
        </a-row>
      </a-card>
    </a-row>
    <a-row :gutter="24" style="margin-top: -30px">
      <a-card :bordered="false">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="课程记录">
            <a-table
              :loading="loading"
              :size="tableSize"
              rowKey="courseId"
              :columns="courseColumns"
              :data-source="courseList"
              :pagination="false"
            >
              <span slot="coverImg" slot-scope="text, record">
                <img :src="getImgView(record.coverImg)" preview=""  height="25px" alt=""  style="max-width:80px;font-size: 12px;font-style: italic;" class="ccie-img"  />
              </span>
              <span slot="expandedRowRender" slot-scope="text">
                <chapter-List ref="chapterList" :courseId="text.courseId" :userId="queryParam1.userId" />
              </span>
            </a-table>
            <a-pagination
              class="ant-table-pagination"
              show-size-changer
              show-quick-jumper
              :current="queryParam.pageNum"
              :total="total"
              :page-size="queryParam.pageSize"
              :showTotal="(total) => `共 ${total} 条`"
              @showSizeChange="onShowSizeChange"
              @change="changeSize"
            />
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-row>
  </a-modal>
</template>
<script>
import { getUserCourse } from '@/api/business/course'
import { listParentName } from '@/api/obj/type'
import chapterList from './modules/courseChapter'
import { ajaxGetDictItems } from '@/api/api'
import {   getFileAccessHttpUrl } from '@/api/manage'
export default {
  components: {
    chapterList,
  },
  data() {
    return {
      dialogVisible: false,
      loading: false,
      selectedRowKeys: [],
      selectedRows: [],
      quanList: [],
      //作品类型
      productTypeOptions: [],
      single: false,
      userForm: {},
      courseList: [],
      studyList: [],
      productList: [],
      total: 0,
      total1: 0,
      total2: 0,
      queryParam: {
        userId: null,
        title: null,
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'createTime',
        isAsc: 'desc',
      },
      queryParam1: {
        userId: null,
        title: null,
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'createTime',
        isAsc: 'desc',
      },
      form: {
        parentId: '',
      },
      deptlist: [],
      courseColumns: [
        {
          title: '课程封面',
          dataIndex: 'coverImg',
          scopedSlots: { customRender: 'coverImg' },
          ellipsis: true,
          align: 'center',
        },
        {
          title: '课程编号',
          dataIndex: 'code',
          ellipsis: true,
          align: 'center',
        },
        {
          title: '课程名称',
          dataIndex: 'title',
          ellipsis: true,
          align: 'center',
        },
        {
          title: '专业类型',
          dataIndex: 'type',
          ellipsis: true,
          align: 'center',
        },
        {
          title: '讲师',
          dataIndex: 'teacherName',
          ellipsis: true,
          align: 'center',
        },
      ],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /* 图片预览 */
    getImgView(text) {
      if (text && text.indexOf(',') > 0) {
        text = text.substring(0, text.indexOf(','))
      }
      return getFileAccessHttpUrl(text)
    },
    /** 查询行业名称列表 */
    getList() {
      listParentName().then((response) => {
        this.deptlist = response.data
      })
    },
    resetQuery() {},
    handleQuery() {},
    show(user) {
      this.userForm = user
      this.dialogVisible = true
      this.queryParam.userId = user.userId
      this.queryParam.courseId = user.courseId
      this.queryParam1.userId = user.userId
      this.queryParam1.courseId = user.courseId
      getUserCourse(this.queryParam).then((response) => {
        this.courseList = response.rows
        this.courseList.forEach((item) => {
          this.deptlist.forEach((item1) => {
            if (item1.id == item.type) {
              item.type = item1.title
            }
          })
        })
        this.total = response.total
        this.loading = false
      })
    },
    handleChange(e) {
      this.queryParam.type = this.productTypeOptions[e].dictValue
    },
    //类型字典转译
    typeFormat(type) {
      return this.selectDictLabel(this.productTypeOptions, type)
    },
    onShowSizeChange(current, pageSize) {
      this.queryParam.pageSize = pageSize
      this.getCourseList(this.queryParam)
    },
    changeSize(current, pageSize) {
      this.queryParam.pageNum = current
      this.queryParam.pageSize = pageSize
      this.getCourseList(this.queryParam)
    },
    onShowSizeChange1(current, pageSize) {
      this.queryParam1.pageSize = pageSize
      this.getStudyList(this.queryParam1)
    },
    changeSize1(current, pageSize) {
      this.queryParam1.pageNum = current
      this.queryParam1.pageSize = pageSize
      this.getStudyList(this.queryParam1)
    },
    onShowSizeChange2(current, pageSize) {
      this.queryParam2.pageSize = pageSize
      this.getProductionList(this.queryParam2)
    },
    changeSize2(current, pageSize) {
      this.queryParam2.pageNum = current
      this.queryParam2.pageSize = pageSize
      this.getProductionList(this.queryParam2)
    },
    colseDialog() {
      this.dialogVisible = false
      this.courseForm = {}
      this.chapterCourseList = []
    },
    getCourseList() {
      this.loading = true
      getUserCourse(this.queryParam).then((response) => {
        this.courseList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getStudyList() {
      this.loading = true
      getUserStudy(this.queryParam1).then((response) => {
        this.studyList = response.rows
        this.total1 = response.total
        this.loading = false
      })
    },
    getProductionList() {
      this.loading = true
      listProduction(this.queryParam2).then((response) => {
        this.productList = response.rows
        this.total2 = response.total
        this.loading = false
      })
    },
  },
}
</script>
<style scoped>
.flow-item {
  width: 278px;
  height: 39px;
  margin: 0 auto;
  text-align: center;
}

.title {
  position: relative;
  padding: 5px 0 5px 8px;
  font-family: AdobeHeitiStd-Regular;
  font-size: 20px;
  white-space: nowrap;
  overflow: visible;
  color: #ffffff;
  background-color: #fbba46;
}

.title::before {
  z-index: 1;
  content: ' ';
  display: block;
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 20px 0 20px 10px;
  border-color: transparent transparent transparent #fff;
  position: absolute;
  left: 0;
  top: 0;
}

.title::after {
  content: ' ';
  display: block;
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 20px 10px 20px 0;
  border-color: transparent #fff transparent transparent;
  position: absolute;
  left: auto;
  top: 0;
  right: 0;
  z-index: 2;
}

.xian-list {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
}

.quan {
  width: 278px;
  height: 135px;
  background-image: linear-gradient(180deg, #ff8955 0%, #f93635 100%);
  box-shadow: 0px 3px 8px 0px rgba(255, 63, 38, 0.59);
  /* background: url(../../../assets/quan.png) no-repeat; */
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  padding: 20px 0 20px 17px;
}

.quan-left {
  width: 65%;
}

.quan-right {
  width: 32%;
  text-align: center;
}

.quan-left,
.quan-right {
  font-family: SourceHanSansCN-Regular;
  color: #fff;
}

.quan-left h2 {
  font-size: 20px;
  color: #fff;
  margin-bottom: 0;
}

.quan-left p {
  font-size: 16px;
  margin-bottom: 0;
}

.quan-left time,
.quan-right time {
  font-size: 12px;
  color: #fee9df;
}

.line {
  width: 1px;
  height: 87px;
  background-color: #feb092;
}

.quan-right h3 {
  font-size: 13px;
  color: #fff;
}
</style>