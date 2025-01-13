package cn.px.modules.medicalcare.service.impl;

import cn.px.modules.medicalcare.entity.MedicalwareMsg;
import cn.px.modules.medicalcare.mapper.MedicalwareAccountMapper;
import cn.px.modules.medicalcare.mapper.MedicalwareMsgMapper;
import cn.px.modules.medicalcare.service.IMedicalwareMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: medicalware_msg
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Service
public class MedicalwareMsgServiceImpl extends ServiceImpl<MedicalwareMsgMapper, MedicalwareMsg> implements IMedicalwareMsgService {
    @Autowired
    private MedicalwareMsgMapper medicalwareMsgMapper;

    /**
     *  获取当前用户的通知
     * @param userId
     * @return
     */
    @Override
    public List<MedicalwareMsg> selectByRuserId(String userId){
        return medicalwareMsgMapper.selectByRuserId(userId);
    }
}
