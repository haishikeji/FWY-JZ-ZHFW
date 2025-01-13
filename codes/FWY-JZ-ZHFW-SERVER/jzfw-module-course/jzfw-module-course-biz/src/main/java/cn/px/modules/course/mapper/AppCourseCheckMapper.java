package cn.px.modules.course.mapper;


import cn.px.modules.course.entity.AppCourseCheck;

import java.util.List;

/**
 * 课程审核Mapper接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface AppCourseCheckMapper
{
    /**
     * 查询课程审核
     *
     * @param checkId 课程审核ID
     * @return 课程审核
     */
    public AppCourseCheck selectAppCourseCheckById(Long checkId);

    /**
     * 查询课程审核列表
     *
     * @param appCourseCheck 课程审核
     * @return 课程审核集合
     */
    public List<AppCourseCheck> selectAppCourseCheckList(AppCourseCheck appCourseCheck);

    /**
     * 新增课程审核
     *
     * @param appCourseCheck 课程审核
     * @return 结果
     */
    public int insertAppCourseCheck(AppCourseCheck appCourseCheck);

    /**
     * 修改课程审核
     *
     * @param appCourseCheck 课程审核
     * @return 结果
     */
    public int updateAppCourseCheck(AppCourseCheck appCourseCheck);

    /**
     * 删除课程审核
     *
     * @param checkId 课程审核ID
     * @return 结果
     */
    public int deleteAppCourseCheckById(Long checkId);

    /**
     * 删除课程审核
     *
     * @param courseId 课程ID
     * @return 结果
     */
    public int deleteAppCourseCheckByCourseId(Long courseId);

    /**
     * 批量删除课程审核
     *
     * @param checkIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppCourseCheckByIds(Long[] checkIds);
}
