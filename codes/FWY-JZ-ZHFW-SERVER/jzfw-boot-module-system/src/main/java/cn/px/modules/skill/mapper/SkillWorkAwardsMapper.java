package cn.px.modules.skill.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import cn.px.modules.skill.entity.SkillWorkAwards;

/**
 * @Description: 奖项设置详情
 * @@author 品讯科技
 * @Date:   2023-08-15
 * @Version: V1.0
 */
public interface SkillWorkAwardsMapper extends BaseMapper<SkillWorkAwards> {

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
   * @return List<SkillWorkAwards>
   */
	public List<SkillWorkAwards> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 通过比赛id查询对应奖项
	 *
	 * @param mainId 主表id
	 * @return List<SkillWorkAwards>
	 */
	public List<SkillWorkAwards> selectBySkillId(@Param("mainId") String mainId,@Param("workId") String workId);
}
