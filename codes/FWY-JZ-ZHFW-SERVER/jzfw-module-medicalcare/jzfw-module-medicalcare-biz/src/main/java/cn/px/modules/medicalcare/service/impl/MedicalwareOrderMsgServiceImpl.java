package cn.px.modules.medicalcare.service.impl;

import cn.px.modules.medicalcare.entity.MedicalwareOrderMsg;
import cn.px.modules.medicalcare.mapper.MedicalwareOrderMsgMapper;
import cn.px.modules.medicalcare.service.IMedicalwareOrderMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单通知缓存表
 * @@author 品讯科技
 * @Date:   2022-07-17
 * @Version: V1.0
 */
@Service
public class MedicalwareOrderMsgServiceImpl extends ServiceImpl<MedicalwareOrderMsgMapper, MedicalwareOrderMsg> implements IMedicalwareOrderMsgService {

    @Autowired
    private MedicalwareOrderMsgMapper medicalwareOrderMsgMapper;

    @Override
    public MedicalwareOrderMsg selectOrderMsgByOrder(MedicalwareOrderMsg medicalwareOrderMsg) {
        return medicalwareOrderMsgMapper.selectOrderMsgByOrder(medicalwareOrderMsg);
    }

    public boolean removeOrderMsg() {
        int remove = medicalwareOrderMsgMapper.removeOrderMsg();
        return remove > 0 ? true : false;
    }
}
