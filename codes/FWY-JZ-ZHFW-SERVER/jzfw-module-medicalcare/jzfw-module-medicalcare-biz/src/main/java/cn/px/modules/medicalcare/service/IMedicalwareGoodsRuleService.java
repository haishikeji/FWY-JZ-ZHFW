package cn.px.modules.medicalcare.service;

import cn.px.modules.medicalcare.entity.MedicalwareGoodsRule;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: medicalware_goods_rule
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
public interface IMedicalwareGoodsRuleService extends IService<MedicalwareGoodsRule> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<MedicalwareGoodsRule>
	 */
	public List<MedicalwareGoodsRule> selectByMainId(String mainId);
}
