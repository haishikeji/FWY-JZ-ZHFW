package cn.px.modules.course.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用（频道）分类对象 app_obj_type
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class AppObjType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 父行业ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 分类编码 */
    @Excel(name = "分类编码")
    private String typeCode;

    /** 频道名称 */
    @Excel(name = "频道名称")
    private String title;

    /** 频道logo */
    @Excel(name = "频道logo")
    private String typeLogo;
    /** 图标地址 */
    @Excel(name = "图标地址")
    private String imgUrl;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 父级行业名称 */
    private String parentName;

    /** 子行业 */
    private List<AppObjType> children = new ArrayList<AppObjType>();

    private int courseNum;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }
    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

    public String getDescribe()
    {
        return describe;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }
    /** 频道logo */
    public String getTypeLogo() { return typeLogo; }

    public void setTypeLogo(String typeLogo) { this.typeLogo = typeLogo; }

    public String getParentName()
    {
        return parentName;
    }
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<AppObjType> getChildren()
    {
        return children;
    }
    public void setChildren(List<AppObjType> children)
    {
        this.children = children;
    }

    public int getCourseNum() {
        return courseNum;
    }
    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("title", getTitle())
            .append("imgUrl", getImgUrl())
            .append("describe", getDescribe())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("orderNum", getOrderNum())
            .toString();
    }
}
