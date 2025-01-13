package cn.px.modules.medicalcare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.medicalcare.entity.MedicalwareMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: medicalware_msg
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
public interface MedicalwareMsgMapper extends BaseMapper<MedicalwareMsg> {
    /**
     *  获取当前用户的通知
     * @param userId
     * @return
     */
    public List<MedicalwareMsg> selectByRuserId(String userId);

    /**
     * 批量阅读
     * @param ids
     * @return
     */
    public int updateMsgByIds(String[] ids);
}
