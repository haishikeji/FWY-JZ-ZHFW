package cn.px.modules.course.service.impl;

import cn.px.common.util.DateUtils;
import cn.px.modules.course.entity.AppCourseChapter;
import cn.px.modules.course.mapper.AppCourseChapterMapper;
import cn.px.modules.course.mapper.AppCourseStudyItemMapper;
import cn.px.modules.course.service.IAppCourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程章节Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class AppCourseChapterServiceImpl implements IAppCourseChapterService
{
    @Autowired
    private AppCourseChapterMapper appCourseChapterMapper;

    @Autowired
    private AppCourseStudyItemMapper appCourseStudyItemMapper;
    /**
     * 查询课程章节
     *
     * @param courseId 课程章节ID
     * @return 课程章节
     */
    @Override
    public List<AppCourseChapter> selectAppCourseChapterByCourseId(Long courseId)
    {
        return appCourseChapterMapper.selectAppCourseChapterByCourseId(courseId);
    }

    /**
     * 查询课程章节
     *
     * @param chapterId 课程章节ID
     * @return 课程章节
     */
    @Override
    public AppCourseChapter selectAppCourseChapterById(Long chapterId)
    {
        return appCourseChapterMapper.selectAppCourseChapterById(chapterId);
    }

    /**
     * 查询课程章节列表
     *
     * @param appCourseChapter 课程章节
     * @return 课程章节
     */
    @Override
    public List<AppCourseChapter> selectAppCourseChapterList(AppCourseChapter appCourseChapter)
    {
        return appCourseChapterMapper.selectAppCourseChapterList(appCourseChapter);
    }

    /**
     * 新增课程章节
     *
     * @param appCourseChapter 课程章节
     * @return 结果
     */
    @Override
    public int insertAppCourseChapter(AppCourseChapter appCourseChapter)
    {
        appCourseChapter.setCreateTime(DateUtils.getDate());
        return appCourseChapterMapper.insertAppCourseChapter(appCourseChapter);
    }

    /**
     * 修改课程章节
     *
     * @param appCourseChapter 课程章节
     * @return 结果
     */
    @Override
    public int updateAppCourseChapter(AppCourseChapter appCourseChapter)
    {
        return appCourseChapterMapper.updateAppCourseChapter(appCourseChapter);
    }

    /**
     * 批量删除课程章节
     *
     * @param chapterIds 需要删除的课程章节ID
     * @return 结果
     */
    @Override
    public int deleteAppCourseChapterByIds(Long[] chapterIds) {

        return appCourseChapterMapper.deleteAppCourseChapterByIds(chapterIds);
    }

    /**
     * 删除课程章节信息
     *
     * @param chapterId 课程章节ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteAppCourseChapterById(Long chapterId) {
        int i =appCourseChapterMapper.deleteAppCourseChapterById(chapterId);
        if(i>0){
            appCourseStudyItemMapper.deleteAppCourseStudyItemByChapterId(chapterId);
        }
        return i;
    }
}
