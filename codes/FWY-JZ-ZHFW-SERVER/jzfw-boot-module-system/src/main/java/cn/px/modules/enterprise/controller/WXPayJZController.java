package cn.px.modules.enterprise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import cn.px.common.api.vo.Result;
import cn.px.common.util.DateUtils;
import cn.px.modules.ass.entity.AssOrder;
import cn.px.modules.ass.entity.JZOrderStatusEnum;
import cn.px.modules.ass.service.IAssOrderService;
import cn.px.modules.ass.vo.changliangenum.orderMsgTypeEnum;
import cn.px.modules.enterprise.entity.EnterpriseOwner;
import cn.px.modules.enterprise.entity.EnterpriseOwnerUseLog;
import cn.px.modules.enterprise.service.IEnterpriseOwnerService;
import cn.px.modules.enterprise.service.IEnterpriseOwnerUseLogService;
import cn.px.modules.message.entity.AssOrderMsg;
import cn.px.modules.message.service.IAssOrderMsgService;
import cn.px.modules.wechat.sdk.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: jeecg-boot
 * @description: 微信回调-家政相关接口
 * @create: 2022-07-26 18:23
 **/
@RestController
@RequestMapping("/wxpay/jz")
@Slf4j
@Api(tags = "微信回调-家政相关接口")
public class WXPayJZController {

    @Autowired
    private IAssOrderService assOrderService;
    @Autowired
    private IEnterpriseOwnerUseLogService enterpriseOwnerUseLogService;
    @Autowired
    private IEnterpriseOwnerService enterpriseOwnerService;
    @Autowired
    private IAssOrderMsgService assOrderMsgService;
    @Transactional
    @RequestMapping(value = "/payCallBack", method = RequestMethod.POST)
    public String payCallBack(HttpServletRequest request) throws Exception {
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1){
            builder.append(new String(bs,0,len));
        }
        String s = builder.toString();
        //使用帮助类将xml接口的字符串转换成map
        Map<String,Object> map = WXPayUtil.xmlToMap(s);
//        System.out.println("map.toString() >>>>>>>>>>>>>>>>>>>>>>> "+map.toString());

        System.out.println("支付结果****************************** "+map);
        String orderSn1 = map.get("out_trade_no").toString();
        AssOrder assOrderUser = assOrderService.getOrderByOrderNo(orderSn1);
        if(map!=null && "success".equalsIgnoreCase(map.get("result_code").toString())){
            //支付成功
            String orderSn = map.get("out_trade_no").toString();
            AssOrder assOrder = assOrderService.getOrderByOrderNo(orderSn);
            boolean result = false;
            //充值 | 下单
            if (assOrder != null) {
                assOrder.setPayPrice(new BigDecimal(map.get("cash_fee").toString()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                assOrder.setOrderStatus(JZOrderStatusEnum.DFP.getKey());
                assOrder.setPayId(map.get("transaction_id").toString());
                assOrder.setPayType("wx");
                assOrder.setPayTime(DateUtils.getDate());
                result = assOrderService.updateById(assOrder);
                AssOrderMsg assOrderMsg = new AssOrderMsg();
                assOrderMsg.setUserId(assOrder.getUserId());
                assOrderMsg.setOrderId(assOrder.getId());
                assOrderMsg.setOrderSn(assOrder.getOrderNo());
                assOrderMsg.setIsRead("0");
                assOrderMsg.setCreateTime(new Date());
                assOrderMsg.setMsgType(orderMsgTypeEnum.ZFCG.getKey());
                assOrderMsg.setMsg("尊敬的用户，您的订单支付成功，请及时查收！");
                assOrderMsgService.save(assOrderMsg);
            } else {
                QueryWrapper<EnterpriseOwnerUseLog> rechargeQueryWrapper = new QueryWrapper<>();
                rechargeQueryWrapper.eq("order_no", orderSn);
                EnterpriseOwnerUseLog recharge = enterpriseOwnerUseLogService.getOne(rechargeQueryWrapper);
                EnterpriseOwner owner = enterpriseOwnerService.getById(recharge.getOwnerId());
                owner.setBalance(owner.getBalance().add(recharge.getOperatePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                result = enterpriseOwnerService.updateById(owner);
                if (result) {
                    recharge.setPayTime(DateUtils.getDate());
                    recharge.setPayStatus("1");
                    enterpriseOwnerUseLogService.updateById(recharge);
                }
            }
            System.out.println("--orderSn::::"+result);
            //3.响应微信支付平台
            if(result){
                HashMap<String,Object> resp = new HashMap<>();
                resp.put("appid",map.get("appid").toString());
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                return WXPayUtil.mapToXml(resp);
            }
        }
        //支付失败
        AssOrderMsg assOrderUserMsg = new AssOrderMsg();
        assOrderUserMsg.setUserId(assOrderUser.getUserId());
        assOrderUserMsg.setOrderId(assOrderUser.getId());
        assOrderUserMsg.setOrderSn(assOrderUser.getOrderNo());
        assOrderUserMsg.setIsRead("0");
        assOrderUserMsg.setCreateTime(new Date());
        assOrderUserMsg.setMsgType(orderMsgTypeEnum.DFK.getKey());
        assOrderUserMsg.setMsg("尊敬的用户，您的订单支付成功失败，请及时查收！");
        assOrderMsgService.save(assOrderUserMsg);
        return "支付回调！";
    }

    @RequestMapping(value = "/refundCallBack", method = RequestMethod.POST)
    public Result<?> refundCallBack(HttpServletRequest request) throws Exception {
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1){
            builder.append(new String(bs,0,len));
        }
        String s = builder.toString();
        //使用帮助类将xml接口的字符串转换成map
        Map<String,Object> map = WXPayUtil.xmlToMap(s);
//        System.out.println("map.toString() >>>>>>>>>>>>>>>>>>>>>>> "+map.toString());

        System.out.println("退款结果****************************** "+map);

        if(map!=null && "success".equalsIgnoreCase(map.get("result_code").toString())){
            //支付成功
            String orderSn = map.get("out_trade_no").toString();
            AssOrder assOrder = assOrderService.getOrderByOrderNo(orderSn);
            boolean result = false;

        }
        return Result.OK("退款回调！");
    }

}
