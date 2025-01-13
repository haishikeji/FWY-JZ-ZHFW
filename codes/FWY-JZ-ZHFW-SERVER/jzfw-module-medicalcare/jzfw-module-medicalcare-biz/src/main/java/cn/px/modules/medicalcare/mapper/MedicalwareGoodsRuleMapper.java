package cn.px.modules.medicalcare.mapper;

import java.util.List;
import cn.px.modules.medicalcare.entity.MedicalwareGoodsRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: medicalware_goods_rule
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
public interface MedicalwareGoodsRuleMapper extends BaseMapper<MedicalwareGoodsRule> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<MedicalwareGoodsRule>
   */
	public List<MedicalwareGoodsRule> selectByMainId(@Param("mainId") String mainId);
}
