package cn.px.modules.ass.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.modules.ass.entity.AssServeSpecificationDetails;
import cn.px.modules.ass.service.IAssServeSpecificationDetailsService;

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
 * @Description: 服务规格i明细管理
 * @@author 品讯科技
 * @Date:   2022-08-09
 * @Version: V1.0
 */
@Api(tags="服务规格i明细管理")
@RestController
@RequestMapping("/ass/assServeSpecificationDetails")
@Slf4j
public class AssServeSpecificationDetailsController extends JeecgController<AssServeSpecificationDetails, IAssServeSpecificationDetailsService> {
	@Autowired
	private IAssServeSpecificationDetailsService assServeSpecificationDetailsService;

	/**
	 * 分页列表查询
	 *
	 * @param assServeSpecificationDetails
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "服务规格i明细管理-分页列表查询")
	@ApiOperation(value="服务规格i明细管理-分页列表查询", notes="服务规格i明细管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AssServeSpecificationDetails>> queryPageList(AssServeSpecificationDetails assServeSpecificationDetails,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AssServeSpecificationDetails> queryWrapper = QueryGenerator.initQueryWrapper(assServeSpecificationDetails, req.getParameterMap());
		Page<AssServeSpecificationDetails> page = new Page<AssServeSpecificationDetails>(pageNo, pageSize);
		IPage<AssServeSpecificationDetails> pageList = assServeSpecificationDetailsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param assServeSpecificationDetails
	 * @return
	 */
	@AutoLog(value = "服务规格i明细管理-添加")
	@ApiOperation(value="服务规格i明细管理-添加", notes="服务规格i明细管理-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AssServeSpecificationDetails assServeSpecificationDetails) {
		assServeSpecificationDetailsService.save(assServeSpecificationDetails);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param assServeSpecificationDetails
	 * @return
	 */
	@AutoLog(value = "服务规格i明细管理-编辑")
	@ApiOperation(value="服务规格i明细管理-编辑", notes="服务规格i明细管理-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AssServeSpecificationDetails assServeSpecificationDetails) {
		assServeSpecificationDetailsService.updateById(assServeSpecificationDetails);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "服务规格i明细管理-通过id删除")
	@ApiOperation(value="服务规格i明细管理-通过id删除", notes="服务规格i明细管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		assServeSpecificationDetailsService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "服务规格i明细管理-批量删除")
	@ApiOperation(value="服务规格i明细管理-批量删除", notes="服务规格i明细管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.assServeSpecificationDetailsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "服务规格i明细管理-通过id查询")
	@ApiOperation(value="服务规格i明细管理-通过id查询", notes="服务规格i明细管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AssServeSpecificationDetails> queryById(@RequestParam(name="id",required=true) String id) {
		AssServeSpecificationDetails assServeSpecificationDetails = assServeSpecificationDetailsService.getById(id);
		if(assServeSpecificationDetails==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(assServeSpecificationDetails);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param assServeSpecificationDetails
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AssServeSpecificationDetails assServeSpecificationDetails) {
        return super.exportXls(request, assServeSpecificationDetails, AssServeSpecificationDetails.class, "服务规格i明细管理");
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
        return super.importExcel(request, response, AssServeSpecificationDetails.class);
    }

}
