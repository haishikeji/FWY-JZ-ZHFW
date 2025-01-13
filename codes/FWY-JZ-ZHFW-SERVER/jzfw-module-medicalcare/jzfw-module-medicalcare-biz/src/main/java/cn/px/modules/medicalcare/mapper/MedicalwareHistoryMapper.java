package cn.px.modules.medicalcare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.medicalcare.entity.MedicalwareHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: medicalware_history
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
public interface MedicalwareHistoryMapper extends BaseMapper<MedicalwareHistory> {

    public List<String> selectHistoryLimit(@Param("staffId") String staffId);

    public int deleteBatchByIds(@Param("array") String[] ids);

    public int removeHistory(MedicalwareHistory medicalwareHistory);

}
