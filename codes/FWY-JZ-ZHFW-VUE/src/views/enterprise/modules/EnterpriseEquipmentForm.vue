<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="equipmentName">
              <a-input v-model="model.equipmentName" placeholder="请输入设备名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="equipmentType">
              <j-dict-select-tag type="list" placeholder="请选择设备类型" v-model="model.equipmentType"
                dictCode="jz_equipment" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="设备数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="equipmentNum">
              <a-input-number  v-model.number="model.equipmentNum" :precision='0' :min="0" :step="1" placeholder="请输入设备数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item label="企业id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enterpriseId">
              <a-input v-model="model.enterpriseId" placeholder="请输入企业id"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item label="照片" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="photo">
              <j-image-upload  text="请上传"  v-model="model.photo"></j-image-upload>
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
    name: 'EnterpriseEquipmentForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          photo: [{ required: true, message: '请上传', trigger: 'blur' }],
          equipmentType: [{ required: true, message: '请选择设备类型', trigger: 'blur' }],
          equipmentName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
          equipmentNum: [{ required: true, message: '设备数量不能为空', trigger: 'blur' }],
        },
        url: {
          add: "/enterprise/enterpriseEquipment/add",
          edit: "/enterprise/enterpriseEquipment/edit",
          queryById: "/enterprise/enterpriseEquipment/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>