package cn.px.modules.medicalcare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.medicalcare.entity.MedicalwareOrderNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单节点
 * @@author 品讯科技
 * @Date:   2022-08-08
 * @Version: V1.0
 */
public interface MedicalwareOrderNodeMapper extends BaseMapper<MedicalwareOrderNode> {

    public List<MedicalwareOrderNode> selectOrderNodeByOrderId(@Param("orderId") String orderId);

}
