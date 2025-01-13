package cn.px.modules.skill.vo;

import java.util.List;
import lombok.Data;
import cn.px.modules.skill.entity.SkillWorkAwards;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 奖项设置
 * @@author 品讯科技
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Data
@ApiModel(value="skill_awardsPage对象", description="奖项设置")
public class SkillAwardsPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**工种Id*/
	@Excel(name = "工种Id", width = 15)
	@ApiModelProperty(value = "工种Id")
    private String workId;
	/**比赛Id*/
	@Excel(name = "比赛Id", width = 15)
	@ApiModelProperty(value = "比赛Id")
    private String skillId;
	/**奖项名称*/
	@Excel(name = "奖项名称", width = 15)
	@ApiModelProperty(value = "奖项名称")
    private String name;
	/**人数*/
	@Excel(name = "人数", width = 15)
	@ApiModelProperty(value = "人数")
    private String number;
	/**是否有证书*/
	@Excel(name = "是否有证书", width = 15)
	@ApiModelProperty(value = "是否有证书")
    private String isZhengshu;
	/**简介*/
	@Excel(name = "简介", width = 15)
	@ApiModelProperty(value = "简介")
    private String introduce;
	/**奖金*/
	@Excel(name = "奖金", width = 15)
	@ApiModelProperty(value = "奖金")
    private String price;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "更新日期")
    private Date updateTime;

	@ExcelCollection(name="奖项设置详情")
	@ApiModelProperty(value = "奖项设置详情")
	private List<SkillWorkAwards> skillWorkAwardsList;
	private List<SkillAwardsPage> skillAwardsPage;
}
