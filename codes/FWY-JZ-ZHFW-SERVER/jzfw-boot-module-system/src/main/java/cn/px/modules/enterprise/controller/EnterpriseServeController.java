package cn.px.modules.enterprise.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.common.system.vo.LoginUser;
import cn.px.modules.enterprise.entity.EnterpriseServe;
import cn.px.modules.enterprise.service.IEnterpriseServeService;

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
 * @Description: 企业服务表
 * @@author 品讯科技
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Api(tags="企业服务表")
@RestController
@RequestMapping("/enterprise/enterpriseServe")
@Slf4j
public class EnterpriseServeController extends JeecgController<EnterpriseServe, IEnterpriseServeService> {
	@Autowired
	private IEnterpriseServeService enterpriseServeService;

	/**
	 * 分页列表查询
	 *
	 * @param enterpriseServe
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "企业服务表-分页列表查询")
	@ApiOperation(value="企业服务表-分页列表查询", notes="企业服务表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EnterpriseServe>> queryPageList(EnterpriseServe enterpriseServe,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser sysUser =(LoginUser) SecurityUtils.getSubject().getPrincipal();
		enterpriseServe.setEnterpriseId(sysUser.getEnterpriseId());
		QueryWrapper<EnterpriseServe> queryWrapper = QueryGenerator.initQueryWrapper(enterpriseServe, req.getParameterMap());
		Page<EnterpriseServe> page = new Page<EnterpriseServe>(pageNo, pageSize);
		IPage<EnterpriseServe> pageList = enterpriseServeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param enterpriseServe
	 * @return
	 */
	@AutoLog(value = "企业服务表-添加")
	@ApiOperation(value="企业服务表-添加", notes="企业服务表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody EnterpriseServe enterpriseServe) {
		enterpriseServeService.save(enterpriseServe);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param enterpriseServe
	 * @return
	 */
	@AutoLog(value = "企业服务表-编辑")
	@ApiOperation(value="企业服务表-编辑", notes="企业服务表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody EnterpriseServe enterpriseServe) {
		enterpriseServeService.updateById(enterpriseServe);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "企业服务表-通过id删除")
	@ApiOperation(value="企业服务表-通过id删除", notes="企业服务表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		enterpriseServeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "企业服务表-批量删除")
	@ApiOperation(value="企业服务表-批量删除", notes="企业服务表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.enterpriseServeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "企业服务表-通过id查询")
	@ApiOperation(value="企业服务表-通过id查询", notes="企业服务表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EnterpriseServe> queryById(@RequestParam(name="id",required=true) String id) {
		EnterpriseServe enterpriseServe = enterpriseServeService.getById(id);
		if(enterpriseServe==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(enterpriseServe);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param enterpriseServe
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EnterpriseServe enterpriseServe) {
        return super.exportXls(request, enterpriseServe, EnterpriseServe.class, "企业服务表");
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
        return super.importExcel(request, response, EnterpriseServe.class);
    }

}
