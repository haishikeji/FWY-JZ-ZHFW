package cn.px.modules.skill.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: skill_node_trends_upvote
 * @@author 品讯科技
 * @Date:   2023-08-21
 * @Version: V1.0
 */
@Data
@TableName("skill_node_trends_upvote")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="skill_node_trends_upvote对象", description="skill_node_trends_upvote")
public class SkillNodeTrendsUpvote implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**动态_节点iD*/
	@Excel(name = "动态_节点iD", width = 15)
    @ApiModelProperty(value = "动态_节点iD")
    private String trendsId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**1已点赞,2取消点赞*/
	@Excel(name = "1已点赞,2取消点赞", width = 15)
    @ApiModelProperty(value = "1已点赞,2取消点赞")
    private String status;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;
}
