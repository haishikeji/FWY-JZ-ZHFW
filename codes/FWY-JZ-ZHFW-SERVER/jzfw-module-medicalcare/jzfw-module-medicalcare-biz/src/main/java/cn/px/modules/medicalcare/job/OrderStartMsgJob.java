package cn.px.modules.medicalcare.job;

import lombok.extern.slf4j.Slf4j;
import cn.px.common.util.DateUtils;
import cn.px.common.util.SpringContextUtils;
import cn.px.modules.medicalcare.service.IMedicalwareOrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 订单即将开始通知任务
 * @author: jeecg-boot
 */

@Slf4j
public class OrderStartMsgJob implements Job {

	private IMedicalwareOrderService medicalwareOrderService;

	private String parameter;
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		medicalwareOrderService = SpringContextUtils.getBean(IMedicalwareOrderService.class);
		log.info(String.format(" Jeecg-Boot 订单即将开始通知任务 OrderStartMsgJob !  时间:" + DateUtils.getTimestamp()));
		medicalwareOrderService.startMsgOrderByQuartz();
	}
}
