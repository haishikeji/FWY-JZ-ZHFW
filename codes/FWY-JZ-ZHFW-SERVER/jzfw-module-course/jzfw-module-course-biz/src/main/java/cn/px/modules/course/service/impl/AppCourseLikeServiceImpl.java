package cn.px.modules.course.service.impl;

import cn.hutool.core.date.DateTime;
import cn.px.modules.course.entity.AppCourseLike;
import cn.px.modules.course.mapper.AppCourseLikeMapper;
import cn.px.modules.course.service.IAppCourseLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程点赞Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class AppCourseLikeServiceImpl implements IAppCourseLikeService
{
    @Autowired
    private AppCourseLikeMapper appCourseLikeMapper;

    /**
     * 查询课程点赞
     *
     * @param courseId 课程点赞ID
     * @return 课程点赞
     */
    @Override
    public AppCourseLike selectAppCourseLikeById(Long courseId)
    {
        return appCourseLikeMapper.selectAppCourseLikeById(courseId);
    }

    /**
     * 查询课程点赞列表
     *
     * @param appCourseLike 课程点赞
     * @return 课程点赞
     */
    @Override
    public List<AppCourseLike> selectAppCourseLikeList(AppCourseLike appCourseLike)
    {
        return appCourseLikeMapper.selectAppCourseLikeList(appCourseLike);
    }

    /**
     * 新增课程点赞
     *
     * @param appCourseLike 课程点赞
     * @return 结果
     */
    @Override
    public int insertAppCourseLike(AppCourseLike appCourseLike)
    {
        appCourseLike.setCreateTime(DateTime.now());
        return appCourseLikeMapper.insertAppCourseLike(appCourseLike);
    }

    /**
     * 修改课程点赞
     *
     * @param appCourseLike 课程点赞
     * @return 结果
     */
    @Override
    public int updateAppCourseLike(AppCourseLike appCourseLike)
    {
        return appCourseLikeMapper.updateAppCourseLike(appCourseLike);
    }

    /**
     * 批量删除课程点赞
     *
     * @param courseIds 需要删除的课程点赞ID
     * @return 结果
     */
    @Override
    public int deleteAppCourseLikeByIds(Long[] courseIds)
    {
        return appCourseLikeMapper.deleteAppCourseLikeByIds(courseIds);
    }

    /**
     * 删除课程点赞信息
     *
     * @param courseId 课程点赞ID
     * @return 结果
     */
    @Override
    public int deleteAppCourseLikeById(Long id)
    {
        return appCourseLikeMapper.deleteAppCourseLikeById(id);
    }

	@Override
	public AppCourseLike selectUseCourseLikeByCourseId(Long courseId, Long userId) {
		return appCourseLikeMapper.selectUseCourseLikeByCourseId(courseId, userId);
	}
}
