<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="投诉人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sponsor">
              <span> {{ model.sponsor }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="phone">
              <span> {{ model.phone }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.complaintType==1">
            <a-form-model-item label="家政人员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="housekeeper" >
              <span> {{ model.housekeeper }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="所属公司"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="enterpriseId_dictText"
            >
              <span> {{ model.enterpriseId_dictText }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="身份证号"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="idCard"
            >
              <span> {{ model.idCard }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="服务项目" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="serviceCategory">
              <span> {{ model.serviceCategory }}</span>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="事情经过" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="eventProcess">
              <a-textarea v-model="model.eventProcess" :rows="4" readonly />
            </a-form-model-item>
          </a-col>
          

             <a-col :span="24" v-if="model.evidence">
            <a-form-model-item label="证据" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple v-model="model.evidence"></j-image-upload>
            </a-form-model-item>
          </a-col>

          <a-col :span="24">
            <a-form-model-item label="投诉状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status_dictText">
              <a-tag color="red" v-if="model.status == 0">
                {{ model.status_dictText }}
              </a-tag>
              <a-tag color="green" v-if="model.status == 1">
                {{ model.status_dictText }}
              </a-tag>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="是否解决"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="status_dictText"
              v-if="model.status == 0 &&!formDisabled"
            >
              <a-radio-group  name="radioGroup" v-model="model.newstatus">
                <a-radio :value="0">待确认</a-radio>
                <a-radio :value="1">已确认</a-radio>
              </a-radio-group>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'pcOverseeForm',
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        add: '/enterprise/enterpriseComplaint/add',
        edit: '/enterprise/enterpriseComplaint/edit',
        queryById: '/enterprise/enterpriseComplaint/queryById',
      },
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {

            this.model.status =this.model.newstatus
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>
<style lang="less" scoped>
::v-deep .ant-upload-select {
    display: none;
}
</style>