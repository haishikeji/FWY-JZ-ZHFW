package cn.px.modules.medicalcare.service.impl;

import cn.px.config.mapper.AbstractService;
import cn.px.modules.medicalcare.entity.MedicalwareOrder;
import cn.px.modules.medicalcare.entity.MedicalwareOrderComment;
import cn.px.modules.medicalcare.mapper.MedicalwareOrderCommentMapper;
import cn.px.modules.medicalcare.mapper.MedicalwareOrderMapper;
import cn.px.modules.medicalcare.service.IMedicalwareOrderCommentService;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 订单评价
 * @@author 品讯科技
 * @Date:   2022-07-08
 * @Version: V1.0
 */
@Service
public class MedicalwareOrderCommentServiceImpl extends AbstractService<MedicalwareOrderCommentMapper, MedicalwareOrderComment> implements IMedicalwareOrderCommentService {

	@Autowired
	private MedicalwareOrderCommentMapper medicalwareOrderCommentMapper;
	@Autowired
	private MedicalwareOrderMapper medicalwareOrderMapper;

	@Override
	public List<MedicalwareOrderComment> selectByMainId(String mainId) {
		return medicalwareOrderCommentMapper.selectByMainId(mainId);
	}
	/**
	 * 评价
	 * @param orderComment
	 * @return
	 */
	public MedicalwareOrderComment add(MedicalwareOrderComment orderComment){
		int i = medicalwareOrderCommentMapper.insert(orderComment);
		if(i>0){
			MedicalwareOrder order =new MedicalwareOrder();
			order.setId(orderComment.getOrderId());
			order.setOrderStatus("order-status-6");
			order.setStatus("order-status-6");
			medicalwareOrderMapper.updateById(order);
		}
		return orderComment;
	}
}
