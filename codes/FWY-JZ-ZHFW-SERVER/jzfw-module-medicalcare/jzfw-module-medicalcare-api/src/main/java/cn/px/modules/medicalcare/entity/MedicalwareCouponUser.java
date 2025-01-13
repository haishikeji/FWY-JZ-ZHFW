package cn.px.modules.medicalcare.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import cn.px.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: medicalware_coupon_user
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Data
@TableName("medicalware_coupon_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="medicalware_coupon_user对象", description="medicalware_coupon_user")
public class MedicalwareCouponUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**uuid*/
	@Excel(name = "uuid", width = 15)
    @ApiModelProperty(value = "uuid")
    private java.lang.String uuid;
	/**otherId*/
	@Excel(name = "otherId", width = 15)
    @ApiModelProperty(value = "otherId")
    private java.lang.String otherId;
	/**otherId1*/
	@Excel(name = "otherId1", width = 15)
    @ApiModelProperty(value = "otherId1")
    private java.lang.String otherId1;
	/**otherId2*/
	@Excel(name = "otherId2", width = 15)
    @ApiModelProperty(value = "otherId2")
    private java.lang.String otherId2;
	/**otherMsg*/
	@Excel(name = "otherMsg", width = 15)
    @ApiModelProperty(value = "otherMsg")
    private java.lang.String otherMsg;
	/**otherMsg1*/
	@Excel(name = "otherMsg1", width = 15)
    @ApiModelProperty(value = "otherMsg1")
    private java.lang.String otherMsg1;
	/**otherMsg2*/
	@Excel(name = "otherMsg2", width = 15)
    @ApiModelProperty(value = "otherMsg2")
    private java.lang.String otherMsg2;
	/**删除标志（0代表存在 2代表删除）*/
	@Excel(name = "删除标志（0代表存在 2代表删除）", width = 15)
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private java.lang.String delFlag;
	/**状态（0正常 1停用）*/
	@Excel(name = "状态（0正常 1停用）", width = 15)
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private java.lang.String status;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @Dict(dictTable = "medicalware_staff",dicCode ="id",dicText = "stf_name" )
    @ApiModelProperty(value = "用户ID")
    private java.lang.String userId;
	/**优惠券ID*/
	@Excel(name = "优惠券ID", width = 15)
    @Dict(dictTable = "medicalware_coupon",dicCode ="id",dicText = "name" )
    @ApiModelProperty(value = "优惠券ID")
    private java.lang.String couponId;
	/**使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；*/
	@Excel(name = "使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；", width = 15, dicCode = "coupon_use_ywy")
    @Dict(dicCode = "coupon_use_ywy")
    @ApiModelProperty(value = "使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；")
    private java.lang.Integer cpStatus;
	/**使用时间*/
	@Excel(name = "使用时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用时间")
    private java.util.Date useTime;
	/**有效期开始时间*/
	@Excel(name = "有效期开始时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期开始时间")
    private java.util.Date startDate;
	/**有效期截至时间*/
	@Excel(name = "有效期截至时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期截至时间")
    private java.util.Date endDate;
	/**订单ID*/
	@Excel(name = "订单ID", width = 15)
    @ApiModelProperty(value = "订单ID")
    private java.lang.String orderId;

    /** 优惠卷详情*/
    @TableField(exist = false)
    @ApiModelProperty(value = "优惠卷详情")
	private MedicalwareCoupon coupon;
}
