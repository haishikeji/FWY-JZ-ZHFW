package cn.px.modules.skill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.modules.skill.entity.SkillAwardsPersonnel;
import cn.px.modules.skill.entity.SkillWorkAwards;

import java.util.List;

/**
 * @Description: 技能竞赛奖项人员
 * @@author 品讯科技
 * @Date:   2023-08-16
 * @Version: V1.0
 */
public interface  ISkillAwardsPersonnelService extends IService<SkillAwardsPersonnel> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<SkillAwardsPersonnel>
	 */
	public List<SkillAwardsPersonnel> selectByMainId(String mainId);
	public List<SkillAwardsPersonnel> countPaiMing();
	public SkillAwardsPersonnel findById(String id);


	/**
	 * 获取比赛结果
	 * @return
	 */
	public List<SkillWorkAwards> getSkillResult(SkillAwardsPersonnel skillAwardsPersonnel);
	public Page<SkillAwardsPersonnel> selectListAll(Page<SkillAwardsPersonnel> iPage, SkillAwardsPersonnel skillAwardsPersonnel);
}
