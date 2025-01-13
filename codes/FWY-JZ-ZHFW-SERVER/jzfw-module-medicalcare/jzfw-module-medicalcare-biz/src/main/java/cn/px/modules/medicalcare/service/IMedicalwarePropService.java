package cn.px.modules.medicalcare.service;

import cn.px.modules.medicalcare.entity.MedicalwareProp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 比例设定表
 * @@author 品讯科技
 * @Date:   2022-08-01
 * @Version: V1.0
 */
public interface IMedicalwarePropService extends IService<MedicalwareProp> {

    public MedicalwareProp selectOneProp(MedicalwareProp medicalwareProp);

}
