package cn.px.modules.course.vo;


import cn.px.modules.course.entity.AppObjType;
import cn.px.modules.course.entity.BaseEntity;

import java.util.List;

/**
 * 包裹器
 * @author 品讯科技
 *
 */
@SuppressWarnings("serial")
public class AppObjVo extends BaseEntity {


	/**
	 * 学习人数
	 */
	private int studyNum;

	/**
	 * 封面图
	 */
	private String coverImg;


	/**
	 * 业务类型
	 */
	private String typeId;

	/**
	 * 学习班名称
	 */
	private String title;

	 /** 讲师id */
    private Long teacherId;

    private String teacherName;

    private String teacherImg;

    private String type;

    /** 课程id */
    private Long courseId;


    private Long studyId;

    /**
	 * 频道list
	 */
	private List<AppObjType> objTypes;


	public int getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(int studyNum) {
		this.studyNum = studyNum;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public List<AppObjType> getObjTypes() {
		return objTypes;
	}

	public void setObjTypes(List<AppObjType> objTypes) {
		this.objTypes = objTypes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getStudyId() {
		return studyId;
	}

	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	public String getTeacherImg() {
		return teacherImg;
	}

	public void setTeacherImg(String teacherImg) {
		this.teacherImg = teacherImg;
	}

}
