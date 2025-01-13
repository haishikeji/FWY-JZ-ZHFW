package cn.px.modules.course.mapper;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.course.entity.AppCourseStudy;
import cn.px.modules.course.vo.AppCourseVo;

import java.util.List;

/**
 * 课程学习Mapper接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface AppCourseStudyMapper
{
	/**
	 * 个人课程學習
	 * @param userId
	 * @return
	 */
	public List<AppCourseVo> selectAppCourseStudyByUserId(Long userId);

	/**
	 * 课程报名人数
	 * @param id
	 * @return
	 */
	public int getCountCourse(Long id);

    /**
     * 查询课程学习
     *
     * @param courseId 课程学习ID
     * @return 课程学习
     */
    public AppCourseStudy selectAppCourseStudyById(Long courseId);

    /**
     * 查询课程学习列表
     *
     * @param appCourseStudy 课程学习
     * @return 课程学习集合
     */
    public List<AppCourseStudy> selectAppCourseStudyList(AppCourseStudy appCourseStudy);

    /**
     * 新增课程学习
     *
     * @param appCourseStudy 课程学习
     * @return 结果
     */
    public int insertAppCourseStudy(AppCourseStudy appCourseStudy);

    /**
     * 修改课程学习
     *
     * @param appCourseStudy 课程学习
     * @return 结果
     */
    public int updateAppCourseStudy(AppCourseStudy appCourseStudy);

    /**
     * 删除课程学习
     *
     * @param courseId 课程学习ID
     * @return 结果
     */
    public int deleteAppCourseStudyById(Long courseId);

    /**
     * 批量删除课程学习
     *
     * @param courseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppCourseStudyByIds(Long[] courseIds);

    /**
	 * 课程个人详情
	 * @param courseId
	 * @return
	 */
	public AppCourseStudy selectUseCourseStudyByCourseId(@Param("courseId") Long courseId, @Param("userId") Long userId);

	/**
	 * 删除个人课程关联表
	 * @param courseId 课程id
	 * @param userId 用户id
	 * @return
	 */
	public int deleteAppCourseStudyByUserId(@Param("courseId") Long courseId, @Param("userId") Long userId);
}
