<template>
  <div class="apply-container">
    <!-- 更改姓名 -->
    <van-form ref="infoForm" style="z-index: 10">
      <van-field label="当前手机号：" v-model="ownerTel" placeholder="未设置手机号" readonly :border="true"> </van-field>
      <van-field
        maxlength="11"
        name="newName"
        :rules="[{ required: true }]"
        v-model="phone"
        placeholder="请输入新的手机号"
        :border="true"
      >
      </van-field>
      <van-field v-model="code" :rules="[{ required: true }]" center clearable placeholder="请输入验证码">
        <template #button>
          <van-button size="small" class="codeBtn" :disabled="isLock" @click="getCode">{{ innerText }}</van-button>
        </template>
      </van-field>
    </van-form>
    <div class="submitBtn">
      <van-button round type="info" class="submit" @click="addSubmit">确定</van-button>
    </div>
  </div>
</template>

<script>
import { editMsg, queryByIdOwn, loginSendSms, smsJiaoYan } from '@/api/user';
import { Notify } from 'vant';
export default {
  name: '',
  data() {
    return {
      phone: undefined,
      code: undefined,
      ownerTel: undefined,
      innerText: "获取验证码",
      isLock: false,
      timer: null,
      form: {},
    };
  },
  mounted() {
    queryByIdOwn({ id: JSON.parse(localStorage.getItem('userInfo')).id }).then((res) => {
      this.ownerTel = res.result.ownerTel;
      this.form = res.result;
    });
  },
  methods: {
    getCode() {
      if (!this.phone) {
        this.$notify('请先输入手机号');
        return;
      }
      if (/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(this.phone)) {
        loginSendSms({ phone: this.phone }).then((response) => {
          if(response.code == 200){
            Notify({ type: 'success', message: response.message });
            this.isLock = true;
            let num = 60;
            this.timer = setInterval(() => {
              num--;
              this.innerText = `${num}s后重新发送`;
              if (num < 0) {
                clearInterval(this.timer);
                this.innerText = '获取验证码';
                this.isLock = false;
              }
            }, 1000);
          }else {
            Notify({ type: 'danger', message: response.message });
          }
        });
      }
    },
    addSubmit() {
      let that = this;
      this.$refs.infoForm
        .validate()
        .then(() => {
          that.form.ownerTel = that.phone;
          smsJiaoYan({
            phone: this.form.ownerTel,
            captcha: this.code
          }).then(resp => {
            if(resp.success){
              editMsg(that.form).then((res) => {
                that.$toast.clear();
                if (res.code == 200) {
                  that.$toast('修改成功');
                  that.$router.back();
                }else {
                  Notify({ type: 'danger', message: resp.message });
                }
              });
            }else {
              Notify({ type: 'danger', message: resp.message });
            }
          })
        })
        .catch((err) => {});
    },
  },
};
</script>

<style lang="scss" scoped>
.apply-container {
  width: 100%;
  height: calc(100vh - 50px);
  margin-top: 50px;
  background: #f9f9f9;
  border-top: 10px solid #f9f9f9;
}
.codeBtn {
  color: #488cf8;
  background: #fff;
  border: none;
}
.submitBtn {
  width: 100%;
  height: 58px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  left: 0;
  .submit {
    width: 90%;
    margin: 0 auto;
    font-size: 16px;
    font-family: Source Han Sans CN;
    font-weight: bold;
    background: #488cf8;
  }
}
</style>