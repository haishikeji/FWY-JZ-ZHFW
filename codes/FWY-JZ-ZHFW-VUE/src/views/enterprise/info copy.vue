<template>
  <a-card :bordered="false" style="padding-bottom: 60px">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enterpriseName">
              <a-input v-model="model.enterpriseName" placeholder="请输入企业名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="企业类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enterpriseType">
              <j-dict-select-tag type="list" v-model="model.enterpriseType" dictCode="sys_enterprise_type"
                placeholder="请选择企业类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="统一社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol"
              prop="unifiedSocialCreditCode">
              <a-input v-model="model.unifiedSocialCreditCode" placeholder="请输入统一社会信用代码"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="法人身份证号码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="legalPersonIdCard">
              <a-input v-model="model.legalPersonIdCard" placeholder="请输入法人身份证号码"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="营业额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="turnover">
              <a-input-number v-model="model.turnover" placeholder="请输入营业额"></a-input-number> 元
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="企业人数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enterprisesNumber">
              <a-input v-model="model.enterprisesNumber" placeholder="请输入企业人数"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="经营项目" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="businessItems">
              <j-category-select :multiple="true" v-model="model.businessItems" pcode="A01" placeholder="请选择经营项目" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="省-市-区、县" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="province">
              <j-area-linkage type="cascader" v-model="model.province" placeholder="请输入省市区" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="address">
              <a-input v-model="model.address" placeholder="请输入详细地址"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="法人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="legalPersonName">
              <a-input v-model="model.legalPersonName" placeholder="请输入法人姓名"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="法人手机号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="legalPersonPhone">
              <a-input v-model="model.legalPersonPhone" placeholder="请输入法人手机号"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="常用联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="topContactsName">
              <a-input v-model="model.topContactsName" placeholder="请输入常用联系人姓名"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="常用联系人手机号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="topContactsPhone">
              <a-input v-model="model.topContactsPhone" placeholder="请输入常用联系人手机号"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="registeredCapital">
              <a-input style="width:90%" v-model="model.registeredCapital" placeholder="请输入注册资金"></a-input> 万元
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="企业logo" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="logo">
              <j-image-upload v-model="model.logo" v-if="!formDisabled"></j-image-upload>
              <span v-else>暂无请上传</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="企业证照:" prop="businessLicenseUrl" :label-col="{ span: 4 }"
              :wrapper-col="{ span: 18 }">
              <a-row>
                <a-col :span="6">
                  <j-image-upload v-model="model.businessLicenseUrl"></j-image-upload>
                  <p class="upload-txt" style="">营业执照</p>
                </a-col>
                <a-col :span="6">
                  <j-image-upload v-model="model.bankAccountOpeningPermit"></j-image-upload>
                  <p class="upload-txt">银行开户许可证</p>
                </a-col>
                <a-col :span="6">
                  <j-image-upload v-model="model.idCardPositive"></j-image-upload>
                  <p class="upload-txt">身份证正面（法人）</p>
                </a-col>
                <a-col :span="6">
                  <j-image-upload v-model="model.idCardBack"></j-image-upload>
                  <p class="upload-txt">身份证背面（法人）</p>
                </a-col>
              </a-row>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
    <a-row>
      <a-col :span="6" :offset="10">
        <a-button size="large" type="primary" htmlType="submit" class="login-button" @click="updateForm">修 改</a-button>
        <a-button size="large" type="primary" style="margin-left: 20px" htmlType="submit" class="login-button"
          @click="submitForm">保 存</a-button>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>

import { httpAction, getAction } from '@/api/manage'

export default {
  name: 'info',
  components: {
  },
  props: {
  },
  computed: {
    userInfo() {
      return this.$store.getters.userInfo
    }
  },
  data() {
    return {
      model: {},
      formDisabled: true,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      url: {
        add: "/enterprise/enterpriseInfo/add",
        audit: "/enterprise/enterpriseInfo/audit",
        edit: "/enterprise/enterpriseInfo/edit",
        queryById: "/enterprise/enterpriseInfo/queryById"
      },
      validatorRules: {}
    }
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
    updateForm() {
      this.formDisabled = false;
    },
    getUserInfo() {
      getAction(this.url.queryById, { id: this.userInfo.enterpriseId }).then((res) => {
        if (res.success) {
          this.model = res.result;
        }
      })
    },
    submitForm() {
      const that = this;
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true;
          that.formDisabled = true;
          httpAction(that.url.edit, that.model, "put").then((res) => {
            if (res.success) {
              that.$message.success(res.message);
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
          })
        }
      })
    }
  }
}

</script>