<template>
  <!-- 荣誉信息 -->
  <div class="honor-info">
    <h1>荣誉信息</h1>
    <div>
        <a-carousel dot-position="bottom" dots-class="slick-dots" style="height: 320px">
            <div v-for="(item,i) in enterpriseHonor" :key="i">
                <div class="ccie-box">
                    <div v-for="(ic,ind) in item.items" :key="ind" class="ccie-item">
                        <img :src="getImgView(ic.honorCertificatePhoto)" alt="" class="ccie-img"/>
                        <p class="ccie-text">
                            <img src="@/assets/qydetail/fcicon.png" alt="" class="text-icon">
                            <span class="tit">{{ic.honorDescribe}}</span>
                        </p>
                        <p class="ccie-time">获得时间：{{ic.acquisitionTime}} <img src="@/assets/qydetail/line.png" alt=""></p>
                    </div>
                </div>
            </div>
        </a-carousel>
    </div>
  </div>
</template>

<script>
import {getAction, getFileAccessHttpUrl} from '@/api/manage'
export default {
  name: 'honorInfo',
  props: ["enterpriseId"],
  data() {
    return {
        enterpriseHonor: [],
        url: {
            list:"/enterprise/enterpriseHonor/list"
        }
    }
  },
  mounted(){
    this.getHonorList();
  },
  methods: {
    /* 图片预览 */
    getImgView(text){
        if(text && text.indexOf(",")>0){
            text = text.substring(0,text.indexOf(","))
        }
        return getFileAccessHttpUrl(text)
    },
    getHonorList(){
        getAction(this.url.list, {enterpriseId: this.enterpriseId}).then(res => {
            let result = [];
            console.log(res.result);
            console.log(this.enterpriseId);
            if(res.result.records&&res.result.records.length > 0 ){
                for(let i = 0; i < res.result.records.length; i += 3){
                    result.push({
                        items: res.result.records.slice(i, i+3)
                    })
                }
                this.enterpriseHonor = result;
            }
        })
    }
  }
}
</script>
<style lang="less">
.honor-info {
    .ant-carousel .slick-slide {
        height: 320px !important;
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
<style lang="less" scoped>
/deep/ .ant-upload.ant-upload-select-picture-card {
  width: 110px !important;
  height: 110px !important;
  background: #F4F4F4;
  border: 1px solid #E5E5E5;
  border-radius: 6px;
  margin: 0 auto;
}
/deep/ .imgupload .iconp {
    padding: 0;
}
.honor-info {
  margin-top: 50px;
  border-top: 1px solid #EBEBEB;
}
.ccie-box {
    display: flex;
    background: #FAFAFA;
    box-shadow: 0px 0px 16px 0px rgba(12,64,199,0.11);
    .ccie-item {
        width: calc(100% / 2.4);
        background: #FAFAFA;
        border: 6px solid #FFFFFF;
        border-radius: 8px;
        padding: 10px;
        box-sizing: border-box;
        &:nth-child(2) {
            margin: 0 8px;
        }
        .ccie-img {
            width: 100%;
            height: 187px;
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
            color: #000000;
            line-height: 26px;
        }
        .ccie-time {
            color: #666666;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            img {
                width: 53%;
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