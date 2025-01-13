package cn.px.modules.skill.service.impl;

import cn.px.modules.skill.entity.SkillTheme;
import cn.px.modules.skill.mapper.SkillThemeMapper;
import cn.px.modules.skill.service.ISkillThemeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 技能竞赛主题表
 * @@author 品讯科技
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Service
public class SkillThemeServiceImpl extends ServiceImpl<SkillThemeMapper, SkillTheme> implements ISkillThemeService {

	@Autowired
	private SkillThemeMapper skillThemeMapper;

	@Override
	public List<SkillTheme> selectByMainId(String mainId) {
		return skillThemeMapper.selectByMainId(mainId);
	}
}
