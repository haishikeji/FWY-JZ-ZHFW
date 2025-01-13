package cn.px.modules.wechat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户数据
 */

@Data
public class WXLoginInfo implements Serializable {


    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;
}
