<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item label="企业名称">
              <a-input placeholder="请输入企业名称" v-model="queryParam.enterpriseName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系人">
             <a-input placeholder="请输入联系人" v-model="queryParam.topContactsName"  />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系电话">
             <a-input placeholder="请输入联系电话" v-model="queryParam.topContactsPhone" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="审核状态">
             <j-dict-select-tag v-model="queryParam.auditStatus" dictCode="sys_audit_status" placeholder="请选择审核状态" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('企业信息表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text,record">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" :preview="record.id" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="pcaSlot" slot-scope="text">
          <div>{{ getPcaText(text) }}</div>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleAudit(record)" v-if="record.auditStatus == 'dsh'">审核</a>
          <a-divider type="vertical" v-if="record.auditStatus == 'dsh'" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定作废吗?" @confirm="() => handleCancel(record.id)">
                  <a>作废</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <enterprise-qualifications-modal ref="modalForm" @ok="modalFormOk"></enterprise-qualifications-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import EnterpriseQualificationsModal from './modules/EnterpriseQualificationsModal'
  import { loadCategoryData } from '@/api/api'
  import Area from '@/components/_util/Area'
  import { httpAction } from '@/api/manage'
  export default {
    name: 'EnterpriseInfoList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      EnterpriseQualificationsModal
    },
    data () {
      return {
        description: '企业信息表管理页面',
        queryParam: {
          auditStatus: "dsh",
          enterpriseName:"",
          topContactsName: "",
          topContactsPhone: ""
        },
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            fixed:"left",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'企业名称',
            align:"center",
            dataIndex: 'enterpriseName'
          },
          {
            title:'企业类型',
            align:"center",
            dataIndex: 'enterpriseType_dictText'
          },
          {
            title:'统一社会信用代码',
            align:"center",
            dataIndex: 'unifiedSocialCreditCode'
          },
          {
            title:'法人身份证号码',
            align:"center",
            dataIndex: 'legalPersonIdCard'
          },
          {
            title:'营业额',
            align:"center",
            dataIndex: 'turnover'
          },
          {
            title:'企业人数',
            align:"center",
            dataIndex: 'enterprisesNumber'
          },
          {
            title:'经营项目',
            align:"center",
            dataIndex: 'businessItems',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['businessItems'], text) : '')
          },
          {
            title:'省-市-区、县',
            align:"center",
            dataIndex: 'province',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          // {
          //   title:'市',
          //   align:"center",
          //   dataIndex: 'city',
          //   scopedSlots: {customRender: 'pcaSlot'}
          // },
          // {
          //   title:'县、区',
          //   align:"center",
          //   dataIndex: 'county',
          //   scopedSlots: {customRender: 'pcaSlot'}
          // },
          // {
          //   title:'详细地址',
          //   align:"center",
          //   dataIndex: 'address'
          // },
          {
            title:'法人姓名',
            align:"center",
            dataIndex: 'legalPersonName'
          },
          {
            title:'法人手机号',
            align:"center",
            dataIndex: 'legalPersonPhone'
          },
          {
            title:'常用联系人姓名',
            align:"center",
            dataIndex: 'topContactsName'
          },
          {
            title:'常用联系人手机号',
            align:"center",
            dataIndex: 'topContactsPhone'
          },
          // {
          //   title:'营业执照',
          //   align:"center",
          //   dataIndex: 'businessLicenseUrl',
          //   scopedSlots: {customRender: 'imgSlot'}
          // },
          // {
          //   title:'银行开户许可证',
          //   align:"center",
          //   dataIndex: 'bankAccountOpeningPermit',
          //   scopedSlots: {customRender: 'imgSlot'}
          // },
          // {
          //   title:'法人身份证正面',
          //   align:"center",
          //   dataIndex: 'idCardPositive',
          //   scopedSlots: {customRender: 'imgSlot'}
          // },
          // {
          //   title:'法人身份证反面',
          //   align:"center",
          //   dataIndex: 'idCardBack',
          //   scopedSlots: {customRender: 'imgSlot'}
          // },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'auditStatus_dictText'
          },
          {
            title:'驳回原因',
            align:"center",
            dataIndex: 'reason'
          },
          {
            title:'注册资金',
            align:"center",
            dataIndex: 'registeredCapital'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/enterprise/enterpriseInfo/list",
          delete: "/enterprise/enterpriseInfo/delete",
          cancel: "/enterprise/enterpriseInfo/cancel",
          deleteBatch: "/enterprise/enterpriseInfo/deleteBatch",
          exportXlsUrl: "/enterprise/enterpriseInfo/exportXls",
          importExcelUrl: "enterprise/enterpriseInfo/importExcel",
          
        },
        dictOptions:{},
        pcaData:'',
        superFieldList:[],
      }
    },
    created() {
      this.pcaData = new Area()
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      handleCancel(id) {
        httpAction(this.url.cancel,{id: id},"put").then((res)=>{
          if(res.success){
            this.$message.success(res.message);
            this.getList();
          }else{
            this.$message.warning(res.message);
          }
        })
      },
      getList(){
         httpAction(this.url.list,{pageNo:1,pageSize:10},"get").then((res)=>{
          if(res.success){
            this.$message.success(res.message);
            this.getSuperFieldList();
          }else{
            this.$message.warning(res.message);
          }
        })
      },
      getPcaText(code){
        return this.pcaData.getText(code);
      },
      initDictConfig(){
        loadCategoryData({code:''}).then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'businessItems', res.result)
          }
        })
      }, 
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'enterpriseName',text:'企业名称',dictCode:''})
        fieldList.push({type:'string',value:'enterpriseType',text:'企业类型',dictCode:'sys_enterprise_type'})
        fieldList.push({type:'string',value:'unifiedSocialCreditCode',text:'统一社会信用代码',dictCode:''})
        fieldList.push({type:'string',value:'legalPersonIdCard',text:'法人身份证号码',dictCode:''})
        fieldList.push({type:'string',value:'turnover',text:'营业额',dictCode:''})
        fieldList.push({type:'string',value:'enterprisesNumber',text:'企业人数',dictCode:''})
        fieldList.push({type:'string',value:'businessItems',text:'经营项目',dictCode:''})
        fieldList.push({type:'pca',value:'province',text:'省-市-区、县'})
        fieldList.push({type:'string',value:'address',text:'详细地址',dictCode:''})
        fieldList.push({type:'string',value:'legalPersonName',text:'法人姓名',dictCode:''})
        fieldList.push({type:'string',value:'legalPersonPhone',text:'法人手机号',dictCode:''})
        fieldList.push({type:'string',value:'topContactsName',text:'常用联系人姓名',dictCode:''})
        fieldList.push({type:'string',value:'topContactsPhone',text:'常用联系人手机号',dictCode:''})
        fieldList.push({type:'string',value:'businessLicenseUrl',text:'营业执照',dictCode:''})
        fieldList.push({type:'string',value:'bankAccountOpeningPermit',text:'银行开户许可证',dictCode:''})
        fieldList.push({type:'string',value:'idCardPositive',text:'法人身份证正面',dictCode:''})
        fieldList.push({type:'string',value:'idCardBack',text:'法人身份证反面',dictCode:''})
        fieldList.push({type:'string',value:'auditStatus',text:'审核状态',dictCode:'sys_audit_status'})
        fieldList.push({type:'string',value:'reason',text:'驳回原因',dictCode:''})
        fieldList.push({type:'string',value:'registeredCapital',text:'注册资金',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style lang="less" scoped>
  @import '~@assets/less/common.less';
</style>