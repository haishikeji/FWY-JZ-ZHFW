<template>
  <div class="coupon-wrap">
    <!-- 订单 -->
    <div class="banner">
        <h1 class="tit">{{orderInfo.orderStatus_dictText}}</h1>
        <p class="time" v-if="orderInfo.orderStatus == 'dfw'">预计到服务时间还到<span> {{getDiffTime(orderInfo.appointmentTime)}}</span></p>
        <p class="time" v-if="orderInfo.orderStatus == 'dfk'">请在<span>24</span>小时之内付款</p>
        <p class="time" v-if="orderInfo.orderStatus == 'ywc'">请给我们的服务进行评价</p>
        <div class="line"></div>
    </div>
    <div style="height: calc(100vh - 270px);overflow-y: auto;">
        <div class="order-detail">
            <div class="order-header">
                <div class="firm-text">
                    <img src="@/assets/images/sevice/firm.png" alt="" class="firm"> {{orderInfo.enterpriseId_dictText}}
                </div>
                <div class="desc-list">
                    <div v-for="(item,i) in businessDesc" :key="i" :column-num="2" class="desc-item">
                        <van-image :src="item.url" slot="icon"  class="van-icon"/> {{item.tit}}
                    </div>
                </div>
            </div>
            <div class="order-main">
                <img :src="appUrl+orderInfo.businessService.headImgUrl.split(',')[0]" alt=""  class="feng" v-if="orderInfo.businessService&&orderInfo.businessService.headImgUrl&&orderInfo.businessService.headImgUrl.indexOf('/')!=-1">
                <img src="@/assets/images/sevice/detail.png" alt=""  class="feng" v-else>
                <div class="detail">
                    <h6>
                        {{orderInfo.serviceName}}
                    </h6>
                    <p class="desc">数量：{{orderInfo.serveNumber}} <span v-if="orderInfo.businessService&&orderInfo.businessService.setMeal">套餐：{{orderInfo.businessService&&orderInfo.businessService.setMeal&&JSON.parse(orderInfo.businessService.setMeal)[0]&&JSON.parse(orderInfo.businessService.setMeal)[0].tcname || '暂无'}}</span></p>
                    <a href="#"><small>¥</small>{{orderInfo.orderPrice}}</a>
                </div>
            </div>
            <div class="person-sevice" @click="artificial">
                <div><img src="@/assets/images/order/service.png" alt="">人工客服</div>
            </div>
        </div>
        <div class="order-brief">
            <div class="order-desc">
                <p>订单号码: <span @click="_copy(orderInfo.orderNo)">{{orderInfo.orderNo}} | 复制</span></p>
                <p>下单时间: <span>{{orderInfo.createTime}}</span></p>
            </div>
            <p class="p1">服务类目: <span>{{orderInfo.businessService&&orderInfo.businessService.categoryId_dictText}}</span></p>
            <p>服务时间: <span>{{orderInfo.appointmentTime}}</span></p>
            <p v-if="orderInfo.orderStatus == 'dpj'">服务历时: <span>4个小时</span></p>
            <div class="p4"><div class="bz"><span style="flex-shrink:0">备注:</span>  <span class="remark">{{orderInfo.remark || "无"}}</span></div> <div class="tel" @click="toTel"><img src="@/assets/images/order/tel.png" alt="">拨打电话</div></div>
            <p class="p3" style="padding-top: 8px;">商品总额: <span>{{orderInfo.orderPrice&&orderInfo.orderPrice.toFixed(2)}}</span></p>
            <!-- <p class="p3">其他费用: <span>+  ¥0.00</span></p> -->
            <div class="p2">
                <div>实付款:<span><small>￥</small>{{orderInfo.orderPrice&&orderInfo.orderPrice.toFixed(2)}}</span></div>
            </div>
        </div>
    </div>
    <div class="submitBtn" v-if="orderInfo.orderStatus == 'djd'||orderInfo.orderStatus == 'dfp'||orderInfo.orderStatus == 'dfk'||orderInfo.orderStatus == 'dfw'||orderInfo.orderStatus == 'ywc'">
        <van-button type="default" round size="normal" class="van-but" @click="delOrder" v-if="orderInfo.orderStatus == 'dfk'">取消订单</van-button>
        <!-- <van-button type="default" round size="normal" class="van-but">订单修改</van-button>  -->
        
        <van-button round size="small" class="van-but" @click.stop="toSale" v-if="orderInfo.orderStatus == 'ywc'||orderInfo.orderStatus == 'djd'||orderInfo.orderStatus == 'dfp'||orderInfo.orderStatus == 'dfw'">投诉售后</van-button>
        <van-button round size="small" class="van-but" @click.stop="toPj" v-if="orderInfo.orderStatus == 'ywc'&&orderInfo.isComment">立即评价</van-button>
        <van-button type="default" round size="normal" class="van-but" @click="payOrderShow" v-if="orderInfo.orderStatus == 'dfk'" color="linear-gradient(90deg, #FE4807, #FF7803)">立即支付</van-button> 
        <van-button type="default" round size="normal" class="van-but" @click="applyRefund" v-if="orderInfo.orderStatus == 'djd'||orderInfo.orderStatus == 'dfp'||orderInfo.orderStatus == 'dfw'||orderInfo.orderStatus == 'ywc'">申请退款</van-button>
        <!-- <van-button type="default" round size="normal" class="van-but">订单修改</van-button> -->
    </div>
    <van-popup v-model="isPay" position="bottom" >
        <van-radio-group v-model="payradio">
            <van-cell-group>
            <van-cell clickable @click="payradio = '1'">
                <template #default>
                    <img class="wximg" src="@/assets/images/wechat.png" mode="aspectFill">
                    微信支付
                </template>
                <template #right-icon>
                    <van-radio name="1" icon-size="16px"/>
                </template>
            </van-cell>
            <van-cell  clickable @click="payradio = '2'">
                <template #default>
                    <img class="wximg" src="@/assets/images/ye.png" mode="aspectFill">
                    余额
                </template>
                <template #right-icon>
                    <van-radio name="2" icon-size="16px"/>
                </template>
            </van-cell>
            </van-cell-group>
        </van-radio-group>
        <div class="line"></div>
        <div class="chang-pay-box">
            <div class="cancle-btn" @click="isPay=false">取 消</div>
            <div class="confirm-btn" @click="confirmPay">确 认</div>
        </div>
    </van-popup>
    <div class="mask" v-if="showMask">
        <div class="mask" v-if="showMask">
            <div class="audit-box">
                <div class="ewm">
                <img :src="appUrl+config[14].configValue" alt="">
                </div>
                <p class="sys">扫一扫上方二维码，添加专属客服为您服务！</p>
                <div class="wx" @click="_copy(config[15].configValue)">
                <img src="@/assets/images/wx.png" alt="">
                微信号：{{config[15].configValue}}  | 复制
                </div>
                <div class="wx" @click="_copy(config[16].configValue)">
                <img src="@/assets/images/tel.png" alt="">
                手机号：{{config[16].configValue}}  | 复制
                </div>
                <p class="save">长按图片保存</p>
            </div>
            <img src="@/assets/images/order/close.png" alt="" class="close" @click="showMask = false">
        </div>
    </div>
  </div>
</template>

<script>
import { assOrderInfo, businessCommonConfig, payOrderYuE, payOrder, cancelOrder } from "@/api/order"
export default {
    name: "coupon",
    data(){
        return {
            showMask: false,
            isPay: false,
            payradio: '1',
            status: "dfw",
            orderInfo: {},
            config: [],
            businessDesc: [{
                url: require("@/assets/images/home/icon_1.png"),
                tit: "官方认证"
            },{
                url: require("@/assets/images/home/icon_2.png"),
                tit: "爽约包赔"
            }],
        }
    },
    mounted(){
        this.getOrderInfo();
    },
    methods: {
        payOrderShow(){
            this.isPay = true;
        },
        toTel(){
            window.location.href=`tel:${this.orderInfo.contactsPhone}`;
        },
        artificial(){
            // 人工客服
            businessCommonConfig().then(res => {
                this.config = res.result;
                if(!this.config[14].configValue){
                    this.$toast('暂无客服配置！');
                }else {
                    this.showMask = true;
                }
            })
        },
        saveImg(imgsrc, name){
           //下载图片地址和图片名
            let image = new Image();
            // 解决跨域 Canvas 污染问题
            image.setAttribute("crossOrigin", "anonymous");
            image.onload = () => {
                let canvas = document.createElement("canvas");
                canvas.width = image.width;
                canvas.height = image.height;
                let context = canvas.getContext("2d");
                context.drawImage(image, 0, 0, image.width, image.height);
                let url = canvas.toDataURL("image/png"); //得到图片的base64编码数据
                let a = document.createElement("a"); // 生成一个a元素
                let event = new MouseEvent("click"); // 创建一个单击事件
                a.download = name || "photo"; // 设置图片名称
                a.href = url; // 将生成的URL设置为a.href属性
                a.dispatchEvent(event); // 触发a的单击事件
            };
            image.src = this.appUrl+this.config[14].configValue;
        },
        confirmPay(){
            let that = this;
            if(this.payradio == '1'){
                // 微信支付
                payOrder({
                    id: this.orderInfo.id,
                    enterpriseId: this.orderInfo.enterpriseId
                }).then(res => {
                    let data = res.result;
                    data.id = this.orderInfo.id;
                    let link = `../pay/pay?data=`+encodeURIComponent(JSON.stringify(data));
                     wx.miniProgram.reLaunch({
                        url: link
                    });
                })
            }else {
                // 余额
                payOrderYuE({
                    id: this.orderInfo.id,
                    enterpriseId: this.orderInfo.enterpriseId
                }).then(res => {
                    this.$toast(res.message);
                    this.getOrderInfo();
                })
            }
        },
        getOrderInfo(){
            this.$toast.loading();
            assOrderInfo({id: this.$route.query.id}).then(res => {
                this.$toast.clear();
                this.orderInfo = res.result;
            })
        },
        toSale(){
            this.$router.push('/complaint-sale?id='+this.orderInfo.id);
        },
        toPj(){
            this.$router.push('/evaluate-order?id='+this.orderInfo.id);
        },
        applyRefund(){
            this.$router.push('/refund?id='+ this.orderInfo.id);
        },
        getDiffTime(old_date){
            console.log(old_date+':00','cdk');
            let new_date = new Date().getTime();
            let old = new Date(old_date+':00').getTime();
            var subtime = (new_date - old) / 1000;    //计算时间差,并将毫秒转化为秒
            var days = parseInt(subtime / 86400);  //天  24*60*60*1000
            var hours = parseInt(subtime / 3600) - 24 * days;   //小时  60*60  总小时数-过去小时数=现在小时数
            var mins = parseInt(subtime % 3600 / 60);    //分钟 - (day*24)  以60秒为一整份  取余 剩下秒数 秒数/60就是分钟数
            var secs = parseInt(subtime % 60);   //以60秒为一整份  取余  剩下秒数
            return days + " 天 " + hours + "小时 ";
        },
        delOrder(item){
           let that = this;
            this.$dialog
            .confirm({
                message: `是否要取消此订单?`,
            })
            .then(() => {
                that.$toast.loading('正在取消...');
                cancelOrder({id:this.orderInfo.id, orderStatus: "yqx"}).then(res => {
                    that.$toast(res.message);
                    that.getOrderInfo();
                })
            })
            .catch(() => {});
        },
        toDetail(){
            this.$router.push('/order-detail');
        },
        // 复制操作
        _copy(context) {
            navigator.clipboard.writeText(context)
			.then(() => {
				this.$toast('复制成功');
			})
			.catch(err => {
				this.$toast('复制失败');
			});
        }
    }
}
</script>

<style lang="scss" scoped>
.coupon-wrap {
    width: 100%;
    height: 100vh;
    overflow: hidden;
    background: linear-gradient(0deg,#F6F6F6 80%,#6fa9f8 100%);
}
.van-popup{
    right: 0;
    width: 100%;
    margin: auto;
    background: none !important;
}
.van-popup .van-cell-group{
    width: 100%;
    margin: auto;
    border-radius: 5px 5px 0 0;
    background: #fff !important;
    overflow: hidden;
}
.van-popup .chang-pay-box{
    display: flex;
    align-items: center;
    width: 100%;
    margin: 0px auto;
    background: #fff;
}
.line {
    width: 100%;
    height: 5px;
    background: #EEEEEE;
}
.van-popup .chang-pay-box .cancle-btn,.van-popup .chang-pay-box .confirm-btn{
    flex-shrink: 0;
    width: 50%;
    height: 50px;
    font-size: 16px;
    text-align: center;
    line-height: 50px;
}
.van-popup .chang-pay-box .cancle-btn{
    border-right: 1px solid #E5E5E5;
    color: #333333;
}
.van-popup .chang-pay-box .confirm-btn{
    color: #2AD3BA;
}
.wximg {
    width: 20px;
    height: 20px;
    vertical-align: middle;
    display: inline-block;
    margin-right: 10px;
}

.banner {
    width: 100%;
    // height: 225px;
    padding-bottom: 20px;
    padding-left: 33px;
    background: url(@/assets/images/order/banner.png) no-repeat;
    background-size: cover;
    padding-top: 60px;
    .tit {
        font-size: 24px;
        font-family: Source Han Sans CN;
        font-weight: 500;
        color: #333333;
    }
    .time {
        font-size: 13px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #333333;
        line-height: 17px;
        opacity: 0.8;
        text-shadow: 0px 0px 2px rgba(0,0,0,0.14);
        margin-top: 16px;
        span {
            color: #FF0000;
        }
    }
    .line {
        width: 22px;
        height: 3px;
        background: #44A1FF;
        margin-top: 16px;
    }
}
.person-sevice {
    width: 100%;
    text-align: center;
    font-size: 12px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #333333;
    border-top: 1px solid #EEEEEE;
    padding-top: 6px;
    img {
        width: 12px;
        height: 12px;
        display: inline-block;
        vertical-align: middle;
        margin-right: 3px;
        margin-bottom: 2px;
    }
}
.order-main {
    display: flex;
    padding-bottom: 10px;
    .detail {
        flex: 1;
        margin-bottom: 10px;
        h6 {
            font-size: 13px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #333333;
            line-height: 22px;
        }
        .desc {
            font-size: 10px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #666666;
            margin-top: 5px;
            span {
                margin-left: 29px;
            }
        }
        a {
            float: right;
            font-size: 10px;
            font-family: Source Han Sans CN;
            font-weight: bold;
            color: #FF0000;
            small {
                font-size: 10px;
            }
        }
    }
    .feng {
        width: 80px;
        height: 80px;
        border-radius: 10px;
        object-fit: cover;
        flex-shrink: 0;
        margin-right: 6px;
    }
}
.order-detail {
    width: 90%;
    margin: 20px auto 10px auto;
    padding: 0 13px 14px 13px;
    background: #FFFFFF;
    box-shadow: 0px 2px 6px 0px rgba(189,189,189,0.21);
    border-radius: 7px;
    .order-header {
        width: 100%;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        .firm-text {
            display: flex;
            align-items: center;
            font-size: 12px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #333333;
        }
        .firm {
            width: 13px;
            height: 13px;
            margin-right: 3px;
        }
    }
    .desc-list {
        display: flex;
        align-items: center;
        border-bottom-left-radius: 10px;
        border-bottom-right-radius: 10px;
        .desc-item {
            font-size: 10px;
            color: #793327;
            &:last-child {
                margin-left: 10px;
            }
        }
        .van-icon {
            width: 8px;
            height: 8px;
            margin-right: 1px;
        }
    }
}
.order-brief {
    width: 90%;
    margin: 0 auto;
    padding: 15px 13px;
    background: #FFFFFF;
    box-shadow: 0px 2px 6px 0px rgba(189,189,189,0.21);
    border-radius: 7px;
    .order-desc {
        border-bottom: 1px solid #E5E5E5;
        padding-bottom: 8px;
    }
    p {
        font-size: 13px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #666666;
        line-height: 28px;
        &.p1 {
            margin-top: 10px;
            b {
                float: right;
                font-size: 17px;
                color: #FF0000;
                font-family: Source Han Sans CN;
                font-weight: bold;
                i {
                    font-size: 10px;
                    font-style: normal;
                }

            }
        }
        &.p3 {
            display: flex;
            align-items: center;
            justify-content: space-between;
            span {
                font-size: 13px;
                font-family: Source Han Sans CN;
                font-weight: 400;
                color: #333333;
            }
        }
        span {
            color: #333333;
            margin-left: 15px;
        }
    }
    .p4 {
        width: 100%;
        display: flex;
        align-items: flex-start;
        justify-content: space-between;
        font-size: 14px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        line-height: 28px;
        color: #666666;
        padding-left: 25px;
        border-bottom: 1px solid #E5E5E5;
        padding-bottom: 8px;
        .bz {
            display: flex;
            align-items: flex-start;
            width: 100%;
        }
        .remark {
            display: inline-block;
            flex-wrap: wrap;
            height: auto;
            flex-shrink: 0;
            width: 70%;color: #333333;
            margin-left: 15px;
        }
        .tel {
            flex-shrink: 0;
            img {
                width: 16px;
                height: 16px;
                vertical-align: middle;
                margin-right: 3px;
                display: inline-block;

            }
            font-size: 14px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #44A1FF;
        }
    }
    .p2 {
        width: 100%;
        height: 30px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        margin-top: 10px;
        font-size: 14px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #333333;
        span {
            color: #FF0000;
            font-size: 17px;
            font-family: Source Han Sans CN;
            font-weight: bold;
            margin-left: 5px;
            small {
                font-size: 10px;
            }
        }
    }
    h4 {
        width: 100%;
        height: 48px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 15px;
        font-family: Source Han Sans CN;
        font-weight: bold;
        color: #2E2E2E;
        position: relative;
        padding-left: 9px;
        border-bottom: 1px solid #E6E6E6;
        &::before {
            content: '';
            position: absolute;
            display: inline-block;
            left: 0;
            width: 2px;
            height: 14px;
            background: #237EFC;
        }
        span {
            font-size: 14px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #FF9E1C;
            display: inline-block;
            padding: 6px 12px;
            background: #FFF5E9;
            border-radius: 12px 0px 0px 12px;
            &.red {
                background: #FFE6E6;
                color: #FF0000;
            }
        }
    }
}
.pj {
    width: 90%;
    margin: 0 auto;
    padding: 0 11px 9px;
    background: #FFFFFF;
    box-shadow: 0px 0px 3px 0px rgba(218,218,218,0.75);
    border-radius: 5px;
    margin-top: 17px;
    .title {
        width: 100%;
        height: 35px;
        display: flex;
        align-items: center;
        border-bottom: 1px dashed #F5F5F5;;
        .user {
            display: flex;
            align-items: center;
            font-size: 14px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #333333;
            img {
                width: 21px;
                height: 21px;
                border-radius: 50%;
                margin-right: 7px;
            }
        }
        span {
            font-size: 12px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #999999;
            margin-left: 7px;
        }
    }
    .brief {
        display: flex;
        align-items: center;
        font-size: 12px;
        font-family: Source Han Sans CN;
        font-weight: 500;
        margin-top: 9px;
        img {
            width: 15px;
            height: 12px;
            margin-right: 3px;
        }
    }
    .shop-desc  {
        width: 100%;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        font-size: 11px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #999999;
        margin-top: 16px;
    }
    .desc{
        width: 100%;
        display: flex;
        align-items: center;
        font-size: 10px;
        height: 24px;
        background: #F6F6F6;
        margin-top: 11px;
        border-radius: 3px;
        .img {
            width: 24px;
            height: 24px;
            border-radius: 3px 0px 0px 3px;
            object-fit: cover;
            display: block;
        } 
        span {
            padding-left: 9px;
            font-size: 10px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #999999;
        }
    }
}
.submitBtn {
    width: 100%;
    height: 58px;
    position: fixed;
    bottom: 0;
    left: 0;
    background: #FFFFFF;
    box-shadow: 0px 0px 6px 0px rgba(202,202,202,0.49);
    display: flex;
    align-items: center;
    justify-content: flex-end;
    border-top: 1px solid #E6E6E6;
    padding-right: 23px;
    .van-but {
        font-size: 16px;
        font-family: Source Han Sans CN;
        font-weight: bold;
        color: #666666;
        margin-left: 9px;
    }
}
.mask {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, .5);
    z-index: 1000;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    .audit-box {
        padding: 24px;
        width: 269px;
        height: 332px;
        background: #FFFFFF;
        border-radius: 12px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        .ewm {
            width: 134px;
            height: 134px;
            border: 1px dashed  #44A1FF;
            margin: 0 auto;
            border-radius: 14px;
            img {
                width: 126px;
                height: 126px;
                object-fit: cover;
                margin: 0 auto;
            }
        }
        .sys {
            font-size: 7px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #999999;
            margin-top: 10px;
        }
        .save {
            text-align: center;
            font-size: 13px;
            font-family: Source Han Sans CN;
            font-weight: 500;
            color: #44A1FF;
            margin-top: 22px;
        }
        .wx {
            display: flex;
            align-items: center;
            font-size: 13px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #2B2B2B;
            margin-top: 22px;
            img {
                width: 21px;
                height: 21px;
                margin-right: 14px;
                display: inline-block;
                vertical-align: middle;
            }
        }
    }
    .close {
        width: 42px;
        height: 42px;
        margin-top: 26px;
    }
}
</style>