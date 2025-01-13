package cn.px.modules.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import cn.px.common.api.vo.Result;
import cn.px.common.system.vo.LoginUser;
import cn.px.modules.system.entity.SysConfig;
import cn.px.modules.system.service.ISysConfigService;
import cn.px.modules.wechat.entity.WxConfigEntity;
import cn.px.modules.wechat.sdk.WXSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wxconfig")
@Slf4j
@Api(tags = "微信相关接口")
public class WXConfigController {
    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 获取wxsdk参数
     * @return
     */
    @ApiOperation(value = "获取wxsdk参数", notes = "获取用户openid")
    @RequestMapping(value = "/wxsdk/config", method = RequestMethod.GET)
    public Result<?> getWxSdkConfig(String enterpriseId) {
        //String enterpriseId="";
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser.getEnterpriseId()!=null)
            enterpriseId=sysUser.getEnterpriseId();

        if(StringUtils.isEmpty(enterpriseId)){
            return Result.error("请使用企业账号进行操作!");
        }
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterprise_id",enterpriseId);
        SysConfig sysConfig= sysConfigService.getOne(queryWrapper);
        if(sysConfig==null){
            //新建默认配置
            sysConfig=new SysConfig();
            sysConfig.setEnterpriseId(enterpriseId);
            sysConfig.setConfigName("微信SDK");
            sysConfig.setConfigKey("wxConfig");
            sysConfig.setConfigType("sysconfig-wx");//微信平台
            sysConfig.setConfigCal("sysconfig-wx-sdk");// SDK参数
            sysConfigService.save(sysConfig);
            String configJson=sysConfig.getConfigValue();

        }
        return Result.OK(sysConfig).success("获取成功");
    }

    /**
     * 获取wxsdk参数
     * @return
     */
    @ApiOperation(value = "保存wxsdk参数", notes = "获取用户openid")
    @RequestMapping(value = "/wxsdk/config", method = RequestMethod.POST)
    public Result<?> saveWxSdkConfig(@RequestBody SysConfig sysConfig) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysConfig.setEnterpriseId(sysUser.getEnterpriseId());
        if(StringUtils.isEmpty(sysConfig.getEnterpriseId())){
            return Result.error("请使用企业账号进行操作!");
        }
        sysConfig.setConfigName("微信SDK");
        sysConfig.setConfigType("sysconfig-wx");//微信平台
        sysConfig.setConfigCal("sysconfig-wx-sdk");// SDK参数
        boolean urows=sysConfigService.updateById(sysConfig);
        if(urows && StringUtils.isNotEmpty(sysConfig.getConfigValue())) {
            WxConfigEntity wxConfigEntity= JSON.parseObject(sysConfig.getConfigValue(),WxConfigEntity.class);
            WXSDK.regWxsdkConfig(sysConfig.getEnterpriseId(), wxConfigEntity);
        }
        return Result.OK("配置成功!");
    }


}
