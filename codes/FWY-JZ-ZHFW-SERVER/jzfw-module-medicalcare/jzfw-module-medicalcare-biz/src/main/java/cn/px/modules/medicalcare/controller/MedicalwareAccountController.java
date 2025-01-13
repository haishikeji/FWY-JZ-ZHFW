package cn.px.modules.medicalcare.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.modules.medicalcare.entity.MedicalwareAccount;
import cn.px.modules.medicalcare.service.IMedicalwareAccountService;

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
 * @Description: medicalware_account
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Api(tags="medicalware_account")
@RestController
@RequestMapping("/medicalcare/medicalwareAccount")
@Slf4j
public class MedicalwareAccountController extends JeecgController<MedicalwareAccount, IMedicalwareAccountService> {
	@Autowired
	private IMedicalwareAccountService medicalwareAccountService;

	/**
	 * 分页列表查询
	 *
	 * @param medicalwareAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "medicalware_account-分页列表查询")
	@ApiOperation(value="medicalware_account-分页列表查询", notes="medicalware_account-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MedicalwareAccount>> queryPageList(MedicalwareAccount medicalwareAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MedicalwareAccount> queryWrapper = QueryGenerator.initQueryWrapper(medicalwareAccount, req.getParameterMap());
		Page<MedicalwareAccount> page = new Page<MedicalwareAccount>(pageNo, pageSize);
		IPage<MedicalwareAccount> pageList = medicalwareAccountService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param medicalwareAccount
	 * @return
	 */
	@AutoLog(value = "medicalware_account-添加")
	@ApiOperation(value="medicalware_account-添加", notes="medicalware_account-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody MedicalwareAccount medicalwareAccount) {
		medicalwareAccountService.save(medicalwareAccount);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param medicalwareAccount
	 * @return
	 */
	@AutoLog(value = "medicalware_account-编辑")
	@ApiOperation(value="medicalware_account-编辑", notes="medicalware_account-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody MedicalwareAccount medicalwareAccount) {
		medicalwareAccountService.updateById(medicalwareAccount);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "medicalware_account-通过id删除")
	@ApiOperation(value="medicalware_account-通过id删除", notes="medicalware_account-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		medicalwareAccountService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "medicalware_account-批量删除")
	@ApiOperation(value="medicalware_account-批量删除", notes="medicalware_account-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.medicalwareAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "medicalware_account-通过id查询")
	@ApiOperation(value="medicalware_account-通过id查询", notes="medicalware_account-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MedicalwareAccount> queryById(@RequestParam(name="id",required=true) String id) {
		MedicalwareAccount medicalwareAccount = medicalwareAccountService.getById(id);
		if(medicalwareAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(medicalwareAccount);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param medicalwareAccount
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MedicalwareAccount medicalwareAccount) {
        return super.exportXls(request, medicalwareAccount, MedicalwareAccount.class, "medicalware_account");
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
        return super.importExcel(request, response, MedicalwareAccount.class);
    }

}
