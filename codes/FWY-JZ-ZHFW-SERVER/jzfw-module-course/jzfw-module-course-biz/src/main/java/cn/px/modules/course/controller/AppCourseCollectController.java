package cn.px.modules.course.controller;

import org.apache.shiro.SecurityUtils;
import cn.px.common.system.vo.LoginUser;
import cn.px.modules.course.base.AjaxResult;
import cn.px.modules.course.base.BaseController;
import cn.px.modules.course.base.page.TableDataInfo;
import cn.px.modules.course.entity.AppCourseCollect;
import cn.px.modules.course.service.IAppCourseCollectService;
import cn.px.modules.course.service.IAppCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程收藏Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/business/collect")
public class AppCourseCollectController extends BaseController
{
    @Autowired
    private IAppCourseService appCourseService;
    @Autowired
    private IAppCourseCollectService appCourseCollectService;
    /**
     * 课程收藏List
     *
     * @param userId
     * @return
     */
    @GetMapping("/courseCollectList")
    public AjaxResult courseCollectList(Long userId) {
        startPage();
        return AjaxResult.success(getDataTableNew(appCourseService.selectUserCollectCourse(userId)));
    }
    /**
     * 课程收藏
     *
     * @param courseId
     * @return
     */
    @GetMapping("/toCourseCollect")
    public AjaxResult toCourseCollect(Long courseId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        AppCourseCollect acc = appCourseCollectService.selectUseCourseCollectByCourseId(courseId, loginUser.getId());
        if (acc == null) {
            acc = new AppCourseCollect();
            acc.setCourseId(courseId);
            acc.setUserId(loginUser.getId());
            appCourseCollectService.insertAppCourseCollect(acc);
            return AjaxResult.success("isCollect", 1);
        } else {
            appCourseCollectService.deleteAppCourseCollectById(acc.getId());
            return AjaxResult.success("isCollect", 0);
        }

    }
    @GetMapping("/list")
    public TableDataInfo list(AppCourseCollect appCourseCollect)
    {
        startPage();
        List<AppCourseCollect> list = appCourseCollectService.selectAppCourseCollectList(appCourseCollect);
        return getDataTable(list);
    }

   /* @GetMapping("/export")
    public AjaxResult export(AppCourseCollect appCourseCollect)
    {
        List<AppCourseCollect> list = appCourseCollectService.selectAppCourseCollectList(appCourseCollect);
        ExcelUtil<AppCourseCollect> util = new ExcelUtil<AppCourseCollect>(AppCourseCollect.class);
        return util.exportExcel(list, "collect");
    }
*/
    @GetMapping(value = "/{courseId}")
    public AjaxResult getInfo(@PathVariable("courseId") Long courseId)
    {
        return AjaxResult.success(appCourseCollectService.selectAppCourseCollectById(courseId));
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody AppCourseCollect appCourseCollect)
    {
        return toAjax(appCourseCollectService.insertAppCourseCollect(appCourseCollect));
    }


    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody AppCourseCollect appCourseCollect)
    {
        return toAjax(appCourseCollectService.updateAppCourseCollect(appCourseCollect));
    }


	@DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds)
    {
        return toAjax(appCourseCollectService.deleteAppCourseCollectByIds(courseIds));
    }
}
