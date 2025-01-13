package cn.px.modules.medicalcare.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.modules.medicalcare.entity.MedicalwareAuthentication;
import cn.px.modules.medicalcare.entity.MedicalwareDistribBalance;
import cn.px.modules.medicalcare.entity.MedicalwareStaff;
import cn.px.modules.medicalcare.service.IMedicalwareAuthenticationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import cn.px.modules.medicalcare.service.IMedicalwareDistribBalanceService;
import cn.px.modules.medicalcare.service.IMedicalwareStaffService;
import cn.px.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.px.common.aspect.annotation.AutoLog;

 /**
 * @Description: medicalware_authentication
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Api(tags="medicalware_authentication")
@RestController
@RequestMapping("/medicalcare/medicalwareAuthentication")
@Slf4j
public class MedicalwareAuthenticationController extends JeecgController<MedicalwareAuthentication, IMedicalwareAuthenticationService> {
	@Autowired
	private IMedicalwareAuthenticationService medicalwareAuthenticationService;
	@Autowired
	private IMedicalwareStaffService medicalwareStaffService;
	@Autowired
	private IMedicalwareDistribBalanceService medicalwareDistribBalanceService;

	/**
	 * 分页列表查询
	 *
	 * @param medicalwareAuthentication
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "medicalware_authentication-分页列表查询")
	@ApiOperation(value="medicalware_authentication-分页列表查询", notes="medicalware_authentication-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MedicalwareAuthentication>> queryPageList(MedicalwareAuthentication medicalwareAuthentication,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		medicalwareAuthentication.setRealName("*"+medicalwareAuthentication.getRealName()+"*");
//		medicalwareAuthentication.setIdcard("*"+medicalwareAuthentication.getIdcard()+"*");
		medicalwareAuthentication.setWxcard("*"+medicalwareAuthentication.getWxcard()+"*");
		QueryWrapper<MedicalwareAuthentication> queryWrapper = QueryGenerator.initQueryWrapper(medicalwareAuthentication, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		Page<MedicalwareAuthentication> page = new Page<MedicalwareAuthentication>(pageNo, pageSize);
		IPage<MedicalwareAuthentication> pageList = medicalwareAuthenticationService.selectPageList(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param medicalwareAuthentication
	 * @return
	 */
	@AutoLog(value = "medicalware_authentication-添加")
	@ApiOperation(value="medicalware_authentication-添加", notes="medicalware_authentication-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody MedicalwareAuthentication medicalwareAuthentication) {
		medicalwareAuthenticationService.save(medicalwareAuthentication);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param medicalwareAuthentication
	 * @return
	 */
	@Transactional
	@AutoLog(value = "medicalware_authentication-编辑")
	@ApiOperation(value="medicalware_authentication-编辑", notes="medicalware_authentication-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody MedicalwareAuthentication medicalwareAuthentication) {
		boolean update = medicalwareAuthenticationService.updateById(medicalwareAuthentication);
		if (update && medicalwareAuthentication.getStfType().equals("user-type-1") && medicalwareAuthentication.getStatus().equals("0")) {
			MedicalwareStaff medicalwareStaff = medicalwareStaffService.getById(medicalwareAuthentication.getStaffId());
			medicalwareStaff.setStfType("user-type-2");
			if (medicalwareAuthentication.getRealAvatar() != null && medicalwareAuthentication.getRealAvatar().length() > 0) {
				medicalwareStaff.setStfPic(medicalwareAuthentication.getRealAvatar());
			}
			medicalwareStaffService.updateById(medicalwareStaff);

			MedicalwareDistribBalance medicalwareDistribBalance = medicalwareDistribBalanceService.selectBalanceByUserId(medicalwareStaff.getId());
			if (medicalwareDistribBalance != null) {
				return Result.error("数据异常!");
			}
			medicalwareDistribBalance = new MedicalwareDistribBalance();
			medicalwareDistribBalance.setUserId(medicalwareStaff.getId());
			medicalwareDistribBalance.setBalance(new BigDecimal(0));
			medicalwareDistribBalance.setPutCash(new BigDecimal(0));
			medicalwareDistribBalance.setNoEntryCash(new BigDecimal(0));
			medicalwareDistribBalanceService.save(medicalwareDistribBalance);
		}
		return Result.OK("操作成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "medicalware_authentication-通过id删除")
	@ApiOperation(value="medicalware_authentication-通过id删除", notes="medicalware_authentication-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		medicalwareAuthenticationService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "medicalware_authentication-批量删除")
	@ApiOperation(value="medicalware_authentication-批量删除", notes="medicalware_authentication-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.medicalwareAuthenticationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "medicalware_authentication-通过id查询")
	@ApiOperation(value="medicalware_authentication-通过id查询", notes="medicalware_authentication-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MedicalwareAuthentication> queryById(@RequestParam(name="id",required=true) String id) {
		MedicalwareAuthentication medicalwareAuthentication = medicalwareAuthenticationService.getById(id);
		if(medicalwareAuthentication==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(medicalwareAuthentication);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param medicalwareAuthentication
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MedicalwareAuthentication medicalwareAuthentication) {
        return super.exportXls(request, medicalwareAuthentication, MedicalwareAuthentication.class, "medicalware_authentication");
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
        return super.importExcel(request, response, MedicalwareAuthentication.class);
    }

}
