package cn.px.modules.medicalcare.service.impl;

import cn.px.config.mapper.AbstractService;
import cn.px.modules.medicalcare.entity.MedicalwareAccount;
import cn.px.modules.medicalcare.mapper.MedicalwareAccountMapper;
import cn.px.modules.medicalcare.service.IMedicalwareAccountService;
import cn.px.modules.medicalcare.vo.MedicalwareAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: medicalware_account
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
@Service
public class MedicalwareAccountServiceImpl extends AbstractService<MedicalwareAccountMapper, MedicalwareAccount> implements IMedicalwareAccountService {
    @Autowired
    private MedicalwareAccountMapper medicalwareAccountMapper;

    /**
     * 获取账单流水
     * @param vo 查询条件
     * @return
     */
    @Override
    public MedicalwareAccount selectOneNew(MedicalwareAccount medicalwareAccount){
        return  medicalwareAccountMapper.selectOneNew(medicalwareAccount);
    }

    /**
     * 获取账单流水
     * @param vo 查询条件
     * @return
     */
    @Override
    public List<MedicalwareAccountVo> selectMonthDate(MedicalwareAccountVo vo){
        return  medicalwareAccountMapper.selectMonthDate(vo);
    }

    /**
     * 获取佣金流水
     * @param medicalwareAccount 查询条件
     * @return
     */
    @Override
    public List<MedicalwareAccountVo> selectCommissionMonthDate(MedicalwareAccountVo medicalwareAccount){
        return  medicalwareAccountMapper.selectCommissionMonthDate(medicalwareAccount);
    }
}
