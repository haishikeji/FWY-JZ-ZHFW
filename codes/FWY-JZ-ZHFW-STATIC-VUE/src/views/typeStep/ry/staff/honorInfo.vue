<template>
    <!-- 荣誉表彰 -->
    <div class="honor-info" style="padding-left:50px">
        <div>

            <el-carousel indicator-position="outside" dots-class="slick-dots" v-if="num">
                <div v-for="i in num" :key="i">
                    <el-carousel-item class="ccie-box">
                        <div v-for="ic in list.slice((i - 1) * 3, i * 3)" :key="ic.id" class="ccie-item">
                            <img :src="ic.honorPhoto" alt=""  class="ccie-img">
                            <div class="ccie-text">
                                <img src="@/assets/qydetail/a.png" alt="" class="text-icon">
                                <span class="tit">{{ ic.honorName }}</span>
                            </div>
                            <div class="ccie-time">

                                <div class="ks">
                                    {{ dist[ic.honorLevel] }}
                                </div>
                                <div>
                                    获得时间：{{ ic.honorDate }}
                                </div>
                            </div>

                        </div>
                    </el-carousel-item>
                </div>
            </el-carousel>
            <div v-else class="empty">
                <div>
                    <img src="@/assets/staff/empty.png" alt="">
                    <div class="text">暂无</div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import {enterpriseStaffHonorList,getDictItems} from "@/api/index.js"
// import { httpAction, getAction, getFileAccessHttpUrl } from '@/api/manage'
export default {
    name: 'honorInfo',
    props: ['Infodata'],
    data() {
        return {
            value: '',
            list: [

            ],
            dist: {
             
            },
            num: 0,

        }
    },
    watch: {
        Infodata: {
            deep: true,
            handler(val) {
                if (val) {
                //     getAction('enterprise/enterpriseStaffHonor/list', { staffId: val }).then(res => {

                //         this.list = res.result.records
                //         if (this.list.length) {
                //             Math
                //             this.num = Math.ceil(this.list.length / 3)
                //         }


                //     })
                }
            },
        },
    },
    methods: {
        getLevel(){
            getDictItems().then(res=>{
                var arr = res.result
                let leaveTypeDist={};
                arr.forEach(ele=>{
                leaveTypeDist[ele.value]=ele.text 
                }) 
                this.dist=leaveTypeDist
            })
        },
        getData(){
            enterpriseStaffHonorList({
                staffId: this.Infodata
            }).then(res=>{
                this.list = res.result.records
                if (this.list.length) {
                    this.num = Math.ceil(this.list.length / 3)
                }
            })
        },
    },
    mounted() {
        this.getLevel()
        this.getData()
        // getAction('enterprise/enterpriseStaffHonor/list', { staffId: this.Infodata }).then(res => {

        


        // })
    }
}
</script>
<style lang="scss">
.honor-info {
    .ant-carousel .slick-slide {
        height: 380px;
        overflow: hidden;
    }

    .ant-carousel .slick-dots-bottom {
        bottom: 0;
        height: 5px;
        border-radius: 30px;
    }

    .ant-carousel .slick-dots li button {
        background: #CCCCCC;
        height: 5px;
        border-radius: 30px;
    }

    .ant-carousel .slick-dots li {
        vertical-align: middle;
    }

    .ant-carousel .slick-dots li.slick-active button {
        background: #0C40C7;
        height: 5px;
        border-radius: 30px;
    }
}
</style>
<style lang="scss" scoped>
 
.honor-info {

    .empty {
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;

        img {
            width: 30%;
        }

        .text {
            color: #46BFBF;
            font-size: 20px;
            margin-top: -25px;
        }
    }
}

.ccie-box {
    display: flex;
    height:320px;
    // background: #07327F;
    // box-shadow: 0px 0px 16px 0px rgba(12, 64, 199, 0.11);

    .ccie-item {
        width: calc(100% / 2.4);
        background: rgba(21,73,146,.2);
        border: 1px solid #29F0F1;
        border-radius: 8px;
        padding: 10px;
        box-sizing: border-box;

        &:nth-child(2) {
            margin: 0 8px;
        }

        .ccie-img {
            width: 100%;
            height:215px;
            display: block;
            margin-bottom: 10px;
            border-radius: 5px;

        }

        .text-icon {
            width: 15px;
            vertical-align: middle;
            display: inline-block;
            margin-right: 15px;
        }

        .ccie-text {
            color: #fff;
            line-height: 26px;
            margin-bottom:8px;
        }

        .ccie-time {
            color: #fff;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: space-between;

            .ks {
                white-space: nowrap;
                padding: 5px 9px;
                background: rgba(21,73,146,.2);
                border: 1px solid #29F0F1;
                border-radius: 5px;

                font-size: 13px;
                color: #29F0F1;
            }
        }
    }
}

.upload-btns {
    height: 25px;
    line-height: 25px;
    display: inline-block;
    padding: 0px 14px;
    margin-bottom: 15px;
    color: #fff;
    font-size: 13px;
    cursor: pointer;
    background: #128BED;
    box-shadow: 0px 6px 16px 0px rgb(18 139 237 / 65%);
    border-radius: 0px 16px 16px 16px;
}

h1 {
    width: 100%;
    height: 70px;
    line-height: 70px;
    font-size: 15px;
    color: #000000;
    font-weight: 600;
    position: relative;
    margin-bottom: 0;

    &::before {
        content: '';
        position: absolute;
        left: 16px;
        top: 0;
        display: inline-block;
        height: 6px;
        width: 30px;
        border-radius: 0px 0px 2px 2px;
        background: #128bed;
    }
}
</style>