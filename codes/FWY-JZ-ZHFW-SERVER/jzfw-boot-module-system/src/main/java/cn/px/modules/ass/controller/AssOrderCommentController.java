package cn.px.modules.ass.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import cn.px.common.api.vo.Result;
import cn.px.common.system.query.QueryGenerator;
import cn.px.common.system.vo.LoginUser;
import cn.px.modules.ass.entity.AssOrderComment;
import cn.px.modules.ass.mapper.AssOrderMapper;
import cn.px.modules.ass.service.IAssOrderCommentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import cn.px.modules.enterprise.service.IEnterpriseOwnerService;
import cn.px.modules.enterprise.service.IEnterpriseStaffInfoService;
import cn.px.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.px.common.aspect.annotation.AutoLog;

 /**
 * @Description: 订单评价表
 * @@author 品讯科技
 * @Date:   2022-08-31
 * @Version: V1.0
 */
@Api(tags="订单评价表")
@RestController
@RequestMapping("/ass/assOrderComment")
@Slf4j
public class AssOrderCommentController extends JeecgController<AssOrderComment, IAssOrderCommentService> {
	 @Autowired
	 private IEnterpriseStaffInfoService iEnterpriseStaffInfoService;
	 @Autowired
	 private IAssOrderCommentService assOrderCommentService;
	 @Autowired
	 private IEnterpriseOwnerService enterpriseOwnerService;
	 @Autowired
	 private AssOrderMapper assOrderMapper;
	/**
	 * 分页列表查询
	 *
	 * @param assOrderComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "订单评价表-分页列表查询")
	@ApiOperation(value="订单评价表-分页列表查询", notes="订单评价表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AssOrderComment>> queryPageList(AssOrderComment assOrderComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		if (StringUtils.isNotBlank(assOrderComment.getCommentType())
				&& "yt".equals(assOrderComment.getCommentType())) {
			assOrderComment.setCommentType(null);
		}
		QueryWrapper<AssOrderComment> queryWrapper = QueryGenerator.initQueryWrapper(assOrderComment, req.getParameterMap());
		if (StringUtils.isNotBlank(assOrderComment.getCommentType())
				&& "yt".equals(assOrderComment.getCommentType())) {
			queryWrapper.isNotNull("comment_pic");
		}
		Page<AssOrderComment> page = new Page<AssOrderComment>(pageNo, pageSize);
		IPage<AssOrderComment> pageList = assOrderCommentService.page(page, queryWrapper);
		List<AssOrderComment> commentList = pageList.getRecords();
		commentList.forEach(c -> {
			c.setOwner(enterpriseOwnerService.getById(c.getUserid()));
			c.setAssOrder(assOrderMapper.selectByOrderId(c.getOrderId()));
			c.setStaffName(iEnterpriseStaffInfoService.getById(c.getStaffId()).getStaffName());
		});
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param assOrderComment
	 * @return
	 */
	@AutoLog(value = "订单评价表-添加")
	@ApiOperation(value="订单评价表-添加", notes="订单评价表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AssOrderComment assOrderComment) {
		String star = assOrderComment.getStar();
		Integer starInt = Integer.valueOf(star);
		if (StringUtils.isNotBlank(star)&&starInt>=4){
			assOrderComment.setCommentType("hp");
		}else if (StringUtils.isNotBlank(star)&&starInt<4){
			assOrderComment.setCommentType("cp");
		}
		assOrderCommentService.save(assOrderComment);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *-
	 * @param assOrderComment
	 * @return
	 */
	@AutoLog(value = "订单评价表-编辑")
	@ApiOperation(value="订单评价表-编辑", notes="订单评价表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AssOrderComment assOrderComment) {
		assOrderCommentService.updateById(assOrderComment);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单评价表-通过id删除")
	@ApiOperation(value="订单评价表-通过id删除", notes="订单评价表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		assOrderCommentService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单评价表-批量删除")
	@ApiOperation(value="订单评价表-批量删除", notes="订单评价表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.assOrderCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "订单评价表-通过id查询")
	@ApiOperation(value="订单评价表-通过id查询", notes="订单评价表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AssOrderComment> queryById(@RequestParam(name="id",required=true) String id) {
		AssOrderComment assOrderComment = assOrderCommentService.getById(id);
		if(assOrderComment==null) {
			return Result.error("未找到对应数据");
		}
		assOrderComment.setAssOrder(assOrderMapper.selectByOrderId(assOrderComment.getOrderId()));
		return Result.OK(assOrderComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param assOrderComment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AssOrderComment assOrderComment) {
        return super.exportXls(request, assOrderComment, AssOrderComment.class, "订单评价表");
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
        return super.importExcel(request, response, AssOrderComment.class);
    }

	 /**
	  * 分页获取用户所有订单 含详细信息
	  *
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @ApiOperation(value="订单表-分页获取用户所有订单 含详细信息", notes="订单表-分页获取用户所有订单 含详细信息")
	 @GetMapping(value = "/list/by/user")
	 public Result<List<AssOrderComment>> queryPageListByUser(AssOrderComment assOrderComment,
																 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 assOrderComment.setUserid(sysUser.getId());
		 Integer pageIndex = (pageNo - 1) * 10;
		 assOrderComment.setPageIndex(pageIndex);
		 assOrderComment.setPageSize(pageSize);
		 List<AssOrderComment> pageList = assOrderCommentService.selectAll(assOrderComment);
		 return Result.OK(pageList);
	 }
}
