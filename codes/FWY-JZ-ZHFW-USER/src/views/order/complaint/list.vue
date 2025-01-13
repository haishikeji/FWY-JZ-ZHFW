<template>
  <div class="coupon-wrap">
     <van-pull-refresh v-model="refreshing" @refresh="onRefresh" class="coupon_list" v-if="orderList.length > 0">
        <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
            <div class="order-brief" v-for="(item,i) in orderList" :key="i" @click="toDetail(item)">
                <div class="order-detail">
                    <div class="order-header">
                        <div class="firm-text">
                            <img src="@/assets/images/sevice/firm.png" alt="" class="firm"> {{item.enterpriseName}}
                        </div>
                    </div>
                    <div class="order-main">
                        <img :src="appUrl+item.assOrder.businessService.headImgUrl.split(',')[0]" alt=""  class="feng" v-if="item.assOrder&&item.assOrder.businessService&&item.assOrder.businessService.headImgUrl.indexOf('/')!=-1">
                        <img src="@/assets/images/sevice/detail.png" alt=""  class="feng" v-else>
                        <div class="detail">
                            <h6>
                                {{item.assOrder.serviceName}}
                            </h6>
                            <p class="desc">数量：{{item.assOrder.serveNumber}} <span v-if="item.assOrder.businessService&&item.assOrder.businessService.setMeal">套餐：{{item.businessService&&item.businessService.setMeal&&JSON.parse(item.businessService.setMeal)[0]&&JSON.parse(item.businessService.setMeal)[0].tcname || '暂无'}}</span></p>
                             <a href="#"><small>¥</small>{{item.assOrder.orderPrice}}</a>
                        </div>
                    </div>
                </div>
                <div class="estimate">
                    <van-button type="default" round size="small" @click="toDetail(item)" class="van-but" >投诉详情</van-button>
                </div>
            </div>
        </van-list>
    </van-pull-refresh>
    <div class="empty" v-if="finished && orderList.length == 0">
      <img src="@/assets/images/order/order.png" alt="" class="empty-img">
      <p class="empty-text">暂无投诉的订单</p>
    </div>
  </div>
</template>

<script>
import { assOrderList, complaintOrderList } from '@/api/order'
export default {
    name: "coupon",
    data(){
        return {
            refreshing: false,
            loading: true,
            finished: true,
            orderList: [],
            businessDesc: [],
            pageNo: 1,
            pageSize: 10,
        }
    },
    mounted(){
        this.getOrderList();
    },
    methods: {
        getOrderList(){
            this.$toast.loading();
            complaintOrderList({userId: JSON.parse(localStorage.getItem('userInfo')).id, pageNo: this.pageNo, pageSize: this.pageSize}).then(res => {
                this.$toast.clear()
                this.loading = false;
                this.refreshing = false;
                if(this.pageNo == 1){
                    this.orderList = [];
                }
                this.orderList = this.orderList.concat(res.result);
                if (res.result.length == 0) {
                    this.finished = true;
                }else {
                    this.finished = false;
                }
            }).catch(() => {
                this.loading = false;
                this.finished = true;
                this.refreshing = false;
            });
        },
        onLoad(){
            this.pageNo += 1;
            this.getOrderList();
        },
        toDetail(item){
            this.$router.push('/complaint-detail?id='+item.id);
        },
        onRefresh(){
            this.pageNo = 1;
            this.getOrderList();
        },
    }
}
</script>

<style lang="scss" scoped>
.coupon-wrap {
    width: 100%;
    height: 100vh;
    background: linear-gradient(0deg,#F6F6F6 80%,#6fa9f8 100%);
    overflow: hidden;
    padding-top: 40px;
}
.estimate {
    width: 100%;
    height: 57px;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    border-top: 1px solid #E6E6E6;
    padding-right: 14px;
    .van-but {
        margin-left: 12px;
    }
}
.coupon_list {
    width: 100%;
    height: calc(100vh - 140px);
    padding-bottom: 100px;
    overflow-y: auto;
}
.order-detail {
    width: 100%;
    padding: 0 13px 14px 13px;
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
        .desc {
            font-size: 10px;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #999999;
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
.wximg {
    width: 20px;
    height: 20px;
    vertical-align: middle;
    display: inline-block;
    margin-right: 10px;
}

.banner {
    width: 100%;
    height: 225px;
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
    height: 36px;
    display: flex;
    align-items: center;
    background: linear-gradient(to right, #FFFFFF, #F4F8FF, #FFFFFF);
    font-size: 10px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #333333;
    span {
        font-size: 13px;
    }
    img {
        width: 12px;
        height: 12px;
        display: inline-block;
        vertical-align: middle;
        margin-right: 7px;
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
.order-brief {
    width: 90%;
    margin: 14px auto;
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
.empty {
    width: 100%;
    height: calc(100vh - 150px);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .empty-img {
        width: 208px;
        height: 208px;
    }
    .empty-text {
        font-size: 17px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #999999;
        margin-top: 29px;
    }
}
</style>