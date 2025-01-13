package cn.px.modules.medicalcare.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description: medicalware_service
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Data
@TableName("medicalware_service")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="medicalware_service对象", description="medicalware_service")
public class MedicalwareService implements Serializable {
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
    @Excel(name = "状态（0正常 1停用）", width = 15, dicCode = "status")
    @Dict(dicCode = "status")
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
	/**客服称呼*/
	@Excel(name = "客服称呼", width = 15)
    @ApiModelProperty(value = "客服称呼")
    private java.lang.String name;
	/**微信号*/
	@Excel(name = "微信号", width = 15)
    @ApiModelProperty(value = "微信号")
    private java.lang.String wxcard;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phone;
	/**微信二维码*/
	@Excel(name = "微信二维码", width = 15)
    @ApiModelProperty(value = "微信二维码")
    private java.lang.String wxImg;
}
