package cn.px.modules.medicalcare.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: medicalware_goods_attr
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@ApiModel(value="medicalware_goods_attr对象", description="medicalware_goods_attr")
@Data
@TableName("medicalware_goods_attr")
public class MedicalwareGoodsAttr implements Serializable {
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
	/**商品表的商品ID*/
    @ApiModelProperty(value = "商品表的商品ID")
    private java.lang.String goodsId;
	/**商品参数名称*/
	@Excel(name = "商品参数名称", width = 15)
    @ApiModelProperty(value = "商品参数名称")
    private java.lang.String attribute;
	/**商品参数值*/
	@Excel(name = "商品参数值", width = 15)
    @ApiModelProperty(value = "商品参数值")
    private Long value;
	/**参数价格*/
	@Excel(name = "参数价格", width = 15)
    @ApiModelProperty(value = "参数价格")
    private java.math.BigDecimal attrPrice;
}
