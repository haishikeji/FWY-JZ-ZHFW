<template>
    <div class="people-info" style="padding-left:50px">
        <!-- 个人风采 -->
        <div class="content">
            <div class="box left">
                <div class="top">
                    <img src="@/assets/staff/lwht.png" alt="">
                    <div>
                        <div class="name">劳务合同</div>
                        <div class="ysc" v-if="lwht.contractType">已上传</div>
                        <div class="ysc" v-else>未上传</div>
                    </div>
                </div>
                <div class="dow" v-if="lwht.contractType">
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit">合同名称</span>
                        <span> {{ lwht.contractName || '家政服务劳动合同' }} </span>
                    </div>
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit">有效期限</span>
                        <span>{{ lwht.contractTerm }}</span>
                    </div>
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit">合同文件</span>
                        <span> <a target="_blank" :href="lwht.contractUrl">劳务合同--{{ form.staffName }} </a>
                        </span>
                    </div>
                </div>


            </div>
            <div class="box left">
                <div class="top">
                    <img src="@/assets/staff/bxht.png" alt="">
                    <div>
                        <div class="name"> 保险合同 </div>
                        <div class="ysc" v-if="bxht.contractType"> 已上传 </div>
                        <div class="ysc" v-else> 未上传 </div>
                    </div>
                </div>
                <div class="dow" v-if="bxht.contractType">
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit"> 合同名称 </span>
                        <span> {{ bxht.contractName || '家政服务保险合同'
                        }} </span>
                    </div>
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit">有效期限</span>
                        <span>{{ bxht.contractTerm }}</span>
                    </div>
                    <div class="list">
                        <img src="@/assets/qydetail/fcicon.png" alt="">
                        <span class="tit">合同文件</span>
                        <span> <a target="_blank" :href="bxht.contractUrl">保险合同--{{ form.staffName }} </a>
                        </span>
                    </div>
                </div>
                <!-- <div class="down" v-else>
                    <div>无</div>
                </div> -->
            </div>
        </div>
    </div>
</template>

<script>
import { contractList } from "@/api/index.js"
// import { httpAction, getAction } from '@/api/manage'
// import { getFileAccessHttpUrl } from '@/api/manage'
export default {
    name: "contractInfo",
    props: ['Infodata'],
    data() {
        return {
            form: {},
            list: [],
            lwht: { contractUrl: '', contractType: null },
            bxht: { contractUrl: '', contractType: null },
        }
    },
    methods: {
        getData(){
            contractList({
                staffId: this.Infodata.id
            }).then(res=>{
                this.form = this.Infodata
                this.list = res.result.records
                console.log(this.list);
                var lwht = this.list.filter(ele => {
                    return ele.contractType == 'lwht'
                })

                var bxht = this.list.filter(ele => {
                    return ele.contractType == 'fwbx'
                })

                if (lwht.length) {
                    this.lwht = lwht[0]
                }
                if (bxht.length) {
                    this.bxht = bxht[0]
                }
            })
        },
        getImgView(text) {
            if (text && text.indexOf(",") > 0) {
                text = text.substring(0, text.indexOf(","))
            }
            return getFileAccessHttpUrl(text)
        },
    },
    watch: {
        Infodata: {
            deep: true,
            handler(val) {
                if (val.id) {
                    this.form = val
                    // getAction('ass/assContract/list', { staffId: val.id }).then(res => {

                    //     this.list = res.result.records
                    //     console.log(this.list);
                    //     var lwht = this.list.filter(ele => {
                    //         return ele.contractType == 'lwht'
                    //     })

                    //     var bxht = this.list.filter(ele => {
                    //         return ele.contractType == 'fwbx'
                    //     })

                    //     if (lwht.length) {
                    //         this.lwht = lwht[0]
                    //     }
                    //     if (bxht.length) {
                    //         this.bxht = bxht[0]
                    //     }


                    // })
                }

            },
        },
    }, mounted() {
        this.getData()
        // getAction('ass/assContract/list', { staffId: this.Infodata.id }).then(res => {
        //     this.form = this.Infodata
        //     this.list = res.result.records
        //     console.log(this.list);
        //     var lwht = this.list.filter(ele => {
        //         return ele.contractType == 'lwht'
        //     })

        //     var bxht = this.list.filter(ele => {
        //         return ele.contractType == 'fwbx'
        //     })

        //     if (lwht.length) {
        //         this.lwht = lwht[0]
        //     }
        //     if (bxht.length) {
        //         this.bxht = bxht[0]
        //     }


        // })

    },
}
</script>
<style lang="scss" scoped>
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

.people-info {
   margin-bottom: 20px;
    .content {

        display: flex;
        justify-content: flex-start;
        // align-items: center;
        width: 100%;

        .box {
            width: 40%;
            padding: 10px;
            background: rgba(7,50,127,0.5);
            border: 1px solid #29F0F1;
            border-radius: 10px;
            height: auto;
            margin-right:20px;

            .top {
                display: flex;

                align-items: center;

                img {
                    width: 100px;
                    height: 100px;
                }

                div {

                    margin-left: 25px;

                    .name {

                        font-size: 24px;
                        font-family: Source Han Sans CN;
                        font-weight: 400;
                        color: #fff;
                        margin-bottom: 16px;
                        white-space: nowrap;
                    }

                    .ysc {
                        background: #FFFFFF;
                        border: 1px solid #3D66FD;
                        border-radius: 5px;
                        padding: 5px 10px;
                        width: 68px;
                        white-space: nowrap;
                        font-family: Source Han Sans CN;
                        font-weight: 400;
                        color: #3D66FD;

                    }
                }
            }

            .dow {
                margin-top: 20px;
                padding-left: 20px;

                .list {
                    display: flex;
                    margin-bottom: 20px;
                    align-items: center;

                    img {
                        width: 10px;
                        height: 10px;
                        background: #29F0F1;
                        border-radius: 50%;
                        margin-right: 14px;
                    }

                    span {

                        font-size: 14px;
                        font-family: Source Han Sans CN;
                        font-weight: 500;
                        color: #fff;


                        a {

                            font-size: 14px;
                            font-family: Source Han Sans CN;
                            font-weight: 500;
                            color: #29F0F1;

                        }

                    }

                    .tit {
                        margin-right: 30px;
                        white-space: nowrap;
                    }
                }
            }
        }
    }
}
</style>