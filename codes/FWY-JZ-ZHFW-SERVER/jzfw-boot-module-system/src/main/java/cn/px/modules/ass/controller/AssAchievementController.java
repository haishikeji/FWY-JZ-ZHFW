package cn.px.modules.ass.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.modules.ass.entity.AssAchievement;
import cn.px.modules.ass.service.IAssAchievementService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import cn.px.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.px.common.aspect.annotation.AutoLog;

 /**
 * @Description: 成就表
 * @@author 品讯科技
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Api(tags="成就表")
@RestController
@RequestMapping("/ass/assAchievement")
@Slf4j
public class AssAchievementController extends JeecgController<AssAchievement, IAssAchievementService> {
	@Autowired
	private IAssAchievementService assAchievementService;

	/**
	 * 分页列表查询
	 *
	 * @param assAchievement
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "成就表-分页列表查询")
	@ApiOperation(value="成就表-分页列表查询", notes="成就表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AssAchievement>> queryPageList(AssAchievement assAchievement,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AssAchievement> queryWrapper = QueryGenerator.initQueryWrapper(assAchievement, req.getParameterMap());
		Page<AssAchievement> page = new Page<AssAchievement>(pageNo, pageSize);
		IPage<AssAchievement> pageList = assAchievementService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param assAchievement
	 * @return
	 */
	@AutoLog(value = "成就表-添加")
	@ApiOperation(value="成就表-添加", notes="成就表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AssAchievement assAchievement) {
		assAchievementService.save(assAchievement);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param assAchievement
	 * @return
	 */
	@AutoLog(value = "成就表-编辑")
	@ApiOperation(value="成就表-编辑", notes="成就表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AssAchievement assAchievement) {
		assAchievementService.updateById(assAchievement);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "成就表-通过id删除")
	@ApiOperation(value="成就表-通过id删除", notes="成就表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		assAchievementService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "成就表-批量删除")
	@ApiOperation(value="成就表-批量删除", notes="成就表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.assAchievementService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "成就表-通过id查询")
	@ApiOperation(value="成就表-通过id查询", notes="成就表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AssAchievement> queryById(@RequestParam(name="id",required=true) String id) {
		AssAchievement assAchievement = assAchievementService.getById(id);
		if(assAchievement==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(assAchievement);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param assAchievement
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AssAchievement assAchievement) {
        return super.exportXls(request, assAchievement, AssAchievement.class, "成就表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AssAchievement.class);
    }

}
