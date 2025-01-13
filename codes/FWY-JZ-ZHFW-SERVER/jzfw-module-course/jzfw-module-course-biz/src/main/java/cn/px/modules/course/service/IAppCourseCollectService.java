package cn.px.modules.course.service;


import cn.px.modules.course.entity.AppCourseCollect;
import cn.px.modules.course.vo.AppCourseVo;

import java.util.List;

/**
 * 课程收藏Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAppCourseCollectService
{

	/**
	 * 个人课程收藏
	 * @param userId
	 * @return
	 */
	public List<AppCourseVo> selectAppCourseCollectByUserId(Long userId);

    /**
     * 查询课程收藏
     *
     * @param courseId 课程收藏ID
     * @return 课程收藏
     */
    public AppCourseCollect selectAppCourseCollectById(Long courseId);

    /**
     * 查询课程收藏列表
     *
     * @param appCourseCollect 课程收藏
     * @return 课程收藏集合
     */
    public List<AppCourseCollect> selectAppCourseCollectList(AppCourseCollect appCourseCollect);

    /**
     * 新增课程收藏
     *
     * @param appCourseCollect 课程收藏
     * @return 结果
     */
    public int insertAppCourseCollect(AppCourseCollect appCourseCollect);

    /**
     * 修改课程收藏
     *
     * @param appCourseCollect 课程收藏
     * @return 结果
     */
    public int updateAppCourseCollect(AppCourseCollect appCourseCollect);

    /**
     * 批量删除课程收藏
     *
     * @param courseIds 需要删除的课程收藏ID
     * @return 结果
     */
    public int deleteAppCourseCollectByIds(Long[] courseIds);

    /**
     * 删除课程收藏信息
     *
     * @param courseId 课程收藏ID
     * @return 结果
     */
    public int deleteAppCourseCollectById(Long id);

    /**
     * 查询个人收藏
     * @param courseId
     * @return
     */
    public AppCourseCollect selectUseCourseCollectByCourseId(Long courseId, String userId);
}
