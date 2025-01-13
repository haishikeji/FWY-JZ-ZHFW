package cn.px.modules.course.vo;

public class AppSearchVo {

	/**
	 * 城市code
	 */
	private String cityCode;

	/**
	 * 是否有试卷
	 */
	private String isTopic;

	private Long userId;

	/** 业务id */
    private Long businessId;

	/**
	 * 证书id
	 */
	private Long ccieId;

	/**
	 * 试卷id
	 */
	private Long topicId;

	private String code;

	private String keyword;

	/**
	 * 部门id
	 */
	private Long deptId;

	private String pointName;

    /** 搜索标题 */
    private String title;

    /**
     * 讲师id
     */
    private Long teacherId;

    private Long[] typeIds;

    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long[] getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(Long[] typeIds) {
		this.typeIds = typeIds;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIsTopic() {
		return isTopic;
	}

	public void setIsTopic(String isTopic) {
		this.isTopic = isTopic;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getCcieId() {
		return ccieId;
	}

	public void setCcieId(Long ccieId) {
		this.ccieId = ccieId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
