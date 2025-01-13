package cn.px.modules.business.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.px.common.api.vo.Result;
import cn.px.common.aspect.annotation.AutoLog;
import cn.px.common.util.BusinessStaffCashEnum;
import cn.px.modules.entity.BusinessCommonConfig;
import cn.px.modules.entity.BusinessStaffCash;
import cn.px.modules.entity.BusinessStaffInfo;
import cn.px.modules.business.service.IBusinessCommonConfigService;
import cn.px.modules.business.service.IBusinessStaffCashService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.px.common.system.base.controller.JeecgController;

import cn.px.modules.business.service.IBusinessStaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 分销入账
 * @@author 品讯科技
 * @Date:   2023-03-14
 * @Version: V1.0
 */
@Slf4j
@Api(tags="分销入账")
@RestController
@RequestMapping("/business/businessStaffCash")
public class BusinessStaffCashController extends JeecgController<BusinessStaffCash, IBusinessStaffCashService> {
	 @Autowired
	 private IBusinessStaffCashService businessStaffCashService;

	 @Autowired
	 private IBusinessStaffInfoService businessStaffInfoService;

	 @Autowired
	 private IBusinessCommonConfigService businessCommonConfigService;

	/**
	 * 分页列表查询
	 *
	 * @param businessStaffCash
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分销入账-分页列表查询")
	@ApiOperation(value="分销入账-分页列表查询", notes="分销入账-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BusinessStaffCash businessStaffCash,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<BusinessStaffCash> page = new Page<BusinessStaffCash>(pageNo, pageSize);
		IPage<BusinessStaffCash> pageList = businessStaffCashService.selectByPage(page, businessStaffCash);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param businessStaffCash
	 * @return
	 */
	@AutoLog(value = "分销入账-添加")
	@ApiOperation(value="分销入账-添加", notes="分销入账-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusinessStaffCash businessStaffCash) {
		//todo 根据通用设置 初始化分销入账状态
		List<BusinessCommonConfig> configList = businessCommonConfigService.list();
		BusinessCommonConfig isCash = configList.stream().filter(c -> "提现设置".equals(c.getConfigLabel())).findFirst().orElse(null);
		if(null == isCash || !"1".equals(isCash.getConfigValue())){
			Result.error("提现有误，请联系管理员");
		}

		//根据账户信息获取账户余额
		BusinessStaffInfo businessStaffInfo = businessStaffInfoService.getByStaffId(businessStaffCash.getStaffId());
		if (null == businessStaffInfo) {
			businessStaffInfo = new BusinessStaffInfo();
			businessStaffInfo.setStaffId(businessStaffCash.getStaffId());
			businessStaffInfo.setStaffName(businessStaffCash.getStaffName());
			businessStaffInfo.setStatus("zc");
			businessStaffInfo.setServiceNum(0);
			businessStaffInfo.setServicePrice(BigDecimal.ZERO);
			businessStaffInfo.setPopularizeNum(0);
			businessStaffInfo.setPopularizePrice(BigDecimal.ZERO);
			businessStaffInfo.setPaymentNum(0);
			businessStaffInfo.setPaymentPrice(BigDecimal.ZERO);
			businessStaffInfo.setCashNum(1);
			businessStaffInfo.setBalancePrice(BigDecimal.ZERO);
			businessStaffInfo.setUnRecorded(businessStaffCash.getPrice());
			businessStaffInfoService.save(businessStaffInfo);
		} else {
			businessStaffInfo.setCashNum(businessStaffInfo.getCashNum() + 1);
			businessStaffInfo.setUnRecorded(businessStaffInfo.getUnRecorded().add(businessStaffCash.getPrice()));
			businessStaffInfoService.updateById(businessStaffInfo);
		}

		businessStaffCash.setBalancePrice(businessStaffInfo.getBalancePrice() == null ?
				BigDecimal.ZERO : businessStaffInfo.getBalancePrice());
		businessStaffCash.setUnRecorded(businessStaffCash.getPrice());
		businessStaffCash.setCreateTime(new Date());

		//默认设置为待打款状态
		BusinessCommonConfig isApproval = configList.stream().filter(c -> "是否审批".equals(c.getConfigLabel())).findFirst().orElse(null);
		if(null != isApproval){
			switch (isApproval.getConfigValue()){
				case "0":
					// 不需审批
					//todo 系统打款
					businessStaffInfo.setPaymentNum(businessStaffInfo.getPaymentNum() + 1);
					businessStaffInfo.setPaymentPrice(businessStaffInfo.getPaymentPrice().add(businessStaffCash.getPrice()));
					businessStaffInfo.setBalancePrice(businessStaffInfo.getBalancePrice().add(businessStaffCash.getPrice()));
					businessStaffInfo.setUnRecorded(businessStaffInfo.getUnRecorded().subtract(businessStaffCash.getPrice()));
					businessStaffCash.setUnRecorded(businessStaffCash.getUnRecorded().subtract(businessStaffCash.getPrice()));
					businessStaffCash.setBalancePrice(businessStaffInfo.getBalancePrice());
					businessStaffCash.setStatus(BusinessStaffCashEnum.XTDK.getStatus());
					businessStaffCashService.updateById(businessStaffCash);
					businessStaffInfoService.updateByStaffId(businessStaffInfo);
					break;
                case "1":
					// 需要审批
                    businessStaffCash.setStatus(BusinessStaffCashEnum.DDK.getStatus());
                    break;
                case "2":
					// 指定金额以上
					BusinessCommonConfig priceLimit = configList.stream().filter(c -> "指定金额以上限制".equals(c.getConfigLabel())).findFirst().orElse(null);
					if(null != priceLimit
							&& businessStaffCash.getPrice().compareTo(new BigDecimal(priceLimit.getConfigValue())) >= 0){
						businessStaffCash.setStatus(BusinessStaffCashEnum.DDK.getStatus());
					}else{
						//todo 系统打款
						businessStaffInfo.setPaymentNum(businessStaffInfo.getPaymentNum() + 1);
						businessStaffInfo.setPaymentPrice(businessStaffInfo.getPaymentPrice().add(businessStaffCash.getPrice()));
						businessStaffInfo.setBalancePrice(businessStaffInfo.getBalancePrice().add(businessStaffCash.getPrice()));
						businessStaffInfo.setUnRecorded(businessStaffInfo.getUnRecorded().subtract(businessStaffCash.getPrice()));
						businessStaffCash.setUnRecorded(businessStaffCash.getUnRecorded().subtract(businessStaffCash.getPrice()));
						businessStaffCash.setBalancePrice(businessStaffInfo.getBalancePrice());
						businessStaffCash.setStatus(BusinessStaffCashEnum.XTDK.getStatus());
						businessStaffCashService.updateById(businessStaffCash);
						businessStaffInfoService.updateByStaffId(businessStaffInfo);
					}
                    break;
			}
		}
		businessStaffCashService.save(businessStaffCash);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param businessStaffCash
	 * @return
	 */
	@AutoLog(value = "分销入账-编辑")
	@ApiOperation(value="分销入账-编辑", notes="分销入账-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody BusinessStaffCash businessStaffCash) {
		businessStaffCashService.updateById(businessStaffCash);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分销入账-通过id删除")
	@ApiOperation(value="分销入账-通过id删除", notes="分销入账-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		businessStaffCashService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "分销入账-批量删除")
	@ApiOperation(value="分销入账-批量删除", notes="分销入账-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.businessStaffCashService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分销入账-通过id查询")
	@ApiOperation(value="分销入账-通过id查询", notes="分销入账-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusinessStaffCash businessStaffCash = businessStaffCashService.getById(id);
		return Result.OK(businessStaffCash);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param businessStaffCash
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, BusinessStaffCash businessStaffCash) {
      return super.exportXls(request, businessStaffCash, BusinessStaffCash.class, "分销入账");
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
      return super.importExcel(request, response, BusinessStaffCash.class);
  }

	 /**
	  * 打款
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "分销入账-打款")
	 @ApiOperation(value="分销入账-打款", notes="分销入账-打款")
	 @PostMapping(value = "/payment")
	 public Result<?> payment(@RequestParam(name="id",required=true) String id) {
		 BusinessStaffCash businessStaffCash = businessStaffCashService.getById(id);
		 if (null == businessStaffCash) {
			 return Result.error("打款信息有误");
		 }

		 // 修改工作人员提现信息表
		 BusinessStaffInfo businessStaffInfo = businessStaffInfoService.getByStaffId(businessStaffCash.getStaffId());
		 if (null == businessStaffInfo) {
			 return Result.error("工作人员提现信息有误");
		 }
		 //todo 打款操作

		 //修改信息
		 businessStaffInfo.setPaymentNum(businessStaffInfo.getPaymentNum() + 1);
		 businessStaffInfo.setPaymentPrice(businessStaffInfo.getPaymentPrice().add(businessStaffCash.getPrice()));
		 businessStaffInfo.setBalancePrice(businessStaffInfo.getBalancePrice().add(businessStaffCash.getPrice()));
		 businessStaffInfo.setUnRecorded(businessStaffInfo.getUnRecorded().subtract(businessStaffCash.getPrice()));
		 businessStaffCash.setUnRecorded(businessStaffCash.getUnRecorded().subtract(businessStaffCash.getPrice()));
		 businessStaffCash.setBalancePrice(businessStaffInfo.getBalancePrice());
		 businessStaffCash.setStatus(BusinessStaffCashEnum.YDK.getStatus());
		 businessStaffCashService.updateById(businessStaffCash);
		 businessStaffInfoService.updateByStaffId(businessStaffInfo);
		 return Result.OK("打款成功！");
	 }
}
