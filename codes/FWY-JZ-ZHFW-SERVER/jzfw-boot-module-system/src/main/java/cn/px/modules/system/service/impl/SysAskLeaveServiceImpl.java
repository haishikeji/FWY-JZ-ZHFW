package cn.px.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.shiro.SecurityUtils;
import cn.px.common.system.vo.LoginUser;
import cn.px.common.util.CodeUtil;
import cn.px.common.util.DateUtils;
import cn.px.common.util.oConvertUtils;
import cn.px.modules.enterprise.entity.EnterpriseStaffInfo;
import cn.px.modules.enterprise.service.IEnterpriseStaffInfoService;
import cn.px.modules.quartz.entity.QuartzJob;
import cn.px.modules.quartz.service.IQuartzJobService;
import cn.px.modules.system.entity.SysAskLeave;
import cn.px.modules.system.mapper.SysAskLeaveMapper;
import cn.px.modules.system.service.ISysAskLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @Description: 请假表
 * @@author 品讯科技
 * @Date:   2022-12-05
 * @Version: V1.0
 */
@Service
public class SysAskLeaveServiceImpl extends ServiceImpl<SysAskLeaveMapper, SysAskLeave> implements ISysAskLeaveService {

    @Autowired
    private SysAskLeaveMapper askLeaveMapper;
    @Autowired
    private IEnterpriseStaffInfoService staffInfoService;

    @Autowired
    private IQuartzJobService quartzJobService;

    @Override
    public boolean save(SysAskLeave sysAskLeave){
        sysAskLeave.setCheckCode(CodeUtil.getCodeHourS("QJ"));
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysAskLeave.setCreateBy(loginUser.getId());
        sysAskLeave.setCreateTime(DateUtils.getDate());
        sysAskLeave.setEnterpriseId(loginUser.getEnterpriseId());
        sysAskLeave.setStaffId(loginUser.getId());
        if (oConvertUtils.isEmpty(sysAskLeave.getStatus())) {
            sysAskLeave.setStatus("dsh");
        }
        return SqlHelper.retBool(askLeaveMapper.insert(sysAskLeave));
    }

    /**
     * 修改请假信息
     * @Date: 2023/2/22
     * @Param: sysAskLeave
     * @return: boolean
     */
    @Transactional
    @Override
    public boolean updateById(SysAskLeave sysAskLeave) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysAskLeave.setCheckUserId(loginUser.getId());
        sysAskLeave.setCheckTime(DateUtils.getDate());
        sysAskLeave.setUpdateBy(loginUser.getId());
        sysAskLeave.setUpdateTime(DateUtils.getDate());
        boolean update = SqlHelper.retBool(askLeaveMapper.updateById(sysAskLeave));
        //审核通过更新家政人员状态
        if (update && "ysh".equals(sysAskLeave.getStatus())) {
            EnterpriseStaffInfo staffInfo = new EnterpriseStaffInfo();
            staffInfo.setId(sysAskLeave.getStaffId());
            staffInfo.setStatus("rest");
            staffInfoService.updateById(staffInfo);
            createJob(sysAskLeave);
        }
        return update;
    }

    /**
     * 根据发布时间创建定时任务
     */
    public void createJob(SysAskLeave sysAskLeave) {
        Calendar publish = Calendar.getInstance();
        publish.setTime(sysAskLeave.getEndTime());

        StringBuffer cronStr = new StringBuffer();
        cronStr.append(publish.get(Calendar.SECOND)).append(" ");
        cronStr.append(publish.get(Calendar.MINUTE)).append(" ");
        cronStr.append(publish.get(Calendar.HOUR_OF_DAY)).append(" ");
        cronStr.append(publish.get(Calendar.DAY_OF_MONTH)).append(" ");
        cronStr.append(publish.get(Calendar.MONTH) + 1).append(" ");
        cronStr.append("? ");
        cronStr.append(publish.get(Calendar.YEAR));
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setCronExpression(cronStr.toString());
        quartzJob.setJobClassName("cn.px.modules.system.job.SysAskLeaveJob");
        quartzJob.setParameter(sysAskLeave.getStaffId());
        quartzJob.setDescription("家政人员 " + sysAskLeave.getStaffId() + " 请假结束时间修改状态定时执行任务");
        quartzJobService.save(quartzJob);
    }

    @Override
    public List<SysAskLeave> selectByMainId(String mainId) {
        return askLeaveMapper.selectByMainId(mainId);
    }

    /**
     * 遍历所有工作人员信息，刷新工作状态
     * @Date: 2023/2/22
     * @Param:
     * @return: boolean
     */
    @Override
    public boolean ergodicAllRefreshStatus() {
        QueryWrapper<EnterpriseStaffInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "rest");
        List<EnterpriseStaffInfo> staffInfoList = staffInfoService.list(queryWrapper);
        for (EnterpriseStaffInfo staffInfo : staffInfoList) {
            List<SysAskLeave> askLeaveList = this.selectByMainId(staffInfo.getId());
            if (askLeaveList != null && askLeaveList.size() > 0) {
                SysAskLeave leave = askLeaveList.get(0);
                if (DateUtils.getDate().before(leave.getEndTime())) {}
            }
        }
        return false;
    }

    /**
     * 根据指定日期查询用户请假信息
     * @Date: 2023/2/22
     * @Param: userId 用户id
     * @Param: date 时间
     * @return: SysAskLeave
     */
    public SysAskLeave getLeaveInfo(String userId, String date) {
        return askLeaveMapper.getLeaveInfo(userId, date);
    }
}
