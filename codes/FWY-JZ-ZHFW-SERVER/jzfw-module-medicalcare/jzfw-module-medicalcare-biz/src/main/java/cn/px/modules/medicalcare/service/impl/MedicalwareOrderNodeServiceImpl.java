package cn.px.modules.medicalcare.service.impl;

import cn.px.modules.medicalcare.entity.MedicalwareOrderNode;
import cn.px.modules.medicalcare.mapper.MedicalwareOrderNodeMapper;
import cn.px.modules.medicalcare.service.IMedicalwareOrderNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 订单节点
 * @@author 品讯科技
 * @Date:   2022-08-08
 * @Version: V1.0
 */
@Service
public class MedicalwareOrderNodeServiceImpl extends ServiceImpl<MedicalwareOrderNodeMapper, MedicalwareOrderNode> implements IMedicalwareOrderNodeService {

    @Autowired
    private MedicalwareOrderNodeMapper medicalwareOrderNodeMapper;

    public List<MedicalwareOrderNode> selectOrderNodeByOrderId(String orderId) {
        return medicalwareOrderNodeMapper.selectOrderNodeByOrderId(orderId);
    }
}
