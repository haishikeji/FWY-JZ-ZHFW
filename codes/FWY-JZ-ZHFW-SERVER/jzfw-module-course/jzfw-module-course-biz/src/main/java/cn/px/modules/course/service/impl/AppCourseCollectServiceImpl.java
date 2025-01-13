package cn.px.modules.course.service.impl;

import cn.hutool.core.date.DateTime;
import cn.px.modules.course.entity.AppCourseCollect;
import cn.px.modules.course.mapper.AppCourseCollectMapper;
import cn.px.modules.course.service.IAppCourseCollectService;
import cn.px.modules.course.vo.AppCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程收藏Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class AppCourseCollectServiceImpl implements IAppCourseCollectService
{
    @Autowired
    private AppCourseCollectMapper appCourseCollectMapper;

    /**
     * 查询课程收藏
     *
     * @param courseId 课程收藏ID
     * @return 课程收藏
     */
    @Override
    public AppCourseCollect selectAppCourseCollectById(Long courseId)
    {
        return appCourseCollectMapper.selectAppCourseCollectById(courseId);
    }

    /**
     * 查询课程收藏列表
     *
     * @param appCourseCollect 课程收藏
     * @return 课程收藏
     */
    @Override
    public List<AppCourseCollect> selectAppCourseCollectList(AppCourseCollect appCourseCollect)
    {
        return appCourseCollectMapper.selectAppCourseCollectList(appCourseCollect);
    }

    /**
     * 新增课程收藏
     *
     * @param appCourseCollect 课程收藏
     * @return 结果
     */
    @Override
    public int insertAppCourseCollect(AppCourseCollect appCourseCollect)
    {
        appCourseCollect.setCreateTime(DateTime.now());
        return appCourseCollectMapper.insertAppCourseCollect(appCourseCollect);
    }

    /**
     * 修改课程收藏
     *
     * @param appCourseCollect 课程收藏
     * @return 结果
     */
    @Override
    public int updateAppCourseCollect(AppCourseCollect appCourseCollect)
    {
        return appCourseCollectMapper.updateAppCourseCollect(appCourseCollect);
    }

    /**
     * 批量删除课程收藏
     *
     * @param courseIds 需要删除的课程收藏ID
     * @return 结果
     */
    @Override
    public int deleteAppCourseCollectByIds(Long[] courseIds)
    {
        return appCourseCollectMapper.deleteAppCourseCollectByIds(courseIds);
    }

    /**
     * 删除课程收藏信息
     *
     * @param courseId 课程收藏ID
     * @return 结果
     */
    @Override
    public int deleteAppCourseCollectById(Long id)
    {
        return appCourseCollectMapper.deleteAppCourseCollectById(id);
    }

	@Override
	public List<AppCourseVo>  selectAppCourseCollectByUserId(Long userId) {
		return appCourseCollectMapper.selectAppCourseCollectByUserId(userId);
	}

	@Override
	public AppCourseCollect selectUseCourseCollectByCourseId(Long courseId, String userId) {
		return appCourseCollectMapper.selectUseCourseCollectByCourseId(courseId, userId);
	}
}
