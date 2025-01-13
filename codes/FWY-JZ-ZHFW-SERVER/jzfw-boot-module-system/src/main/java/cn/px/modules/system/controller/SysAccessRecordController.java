package cn.px.modules.system.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.modules.system.entity.SysAccessRecord;
import cn.px.modules.system.service.ISysAccessRecordService;

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
 * @Description: 访问记录表
 * @@author 品讯科技
 * @Date:   2022-07-12
 * @Version: V1.0
 */
@Api(tags="访问记录表")
@RestController
@RequestMapping("/system/sysAccessRecord")
@Slf4j
public class SysAccessRecordController extends JeecgController<SysAccessRecord, ISysAccessRecordService> {
	@Autowired
	private ISysAccessRecordService sysAccessRecordService;

	/**
	 * 分页列表查询
	 *
	 * @param sysAccessRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "访问记录表-分页列表查询")
	@ApiOperation(value="访问记录表-分页列表查询", notes="访问记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SysAccessRecord>> queryPageList(SysAccessRecord sysAccessRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysAccessRecord> queryWrapper = QueryGenerator.initQueryWrapper(sysAccessRecord, req.getParameterMap());
		Page<SysAccessRecord> page = new Page<SysAccessRecord>(pageNo, pageSize);
		IPage<SysAccessRecord> pageList = sysAccessRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param sysAccessRecord
	 * @return
	 */
	@AutoLog(value = "访问记录表-添加")
	@ApiOperation(value="访问记录表-添加", notes="访问记录表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody SysAccessRecord sysAccessRecord) {
		sysAccessRecordService.save(sysAccessRecord);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param sysAccessRecord
	 * @return
	 */
	@AutoLog(value = "访问记录表-编辑")
	@ApiOperation(value="访问记录表-编辑", notes="访问记录表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SysAccessRecord sysAccessRecord) {
		sysAccessRecordService.updateById(sysAccessRecord);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "访问记录表-通过id删除")
	@ApiOperation(value="访问记录表-通过id删除", notes="访问记录表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		sysAccessRecordService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "访问记录表-批量删除")
	@ApiOperation(value="访问记录表-批量删除", notes="访问记录表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysAccessRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "访问记录表-通过id查询")
	@ApiOperation(value="访问记录表-通过id查询", notes="访问记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SysAccessRecord> queryById(@RequestParam(name="id",required=true) String id) {
		SysAccessRecord sysAccessRecord = sysAccessRecordService.getById(id);
		if(sysAccessRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(sysAccessRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysAccessRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysAccessRecord sysAccessRecord) {
        return super.exportXls(request, sysAccessRecord, SysAccessRecord.class, "访问记录表");
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
        return super.importExcel(request, response, SysAccessRecord.class);
    }

}
