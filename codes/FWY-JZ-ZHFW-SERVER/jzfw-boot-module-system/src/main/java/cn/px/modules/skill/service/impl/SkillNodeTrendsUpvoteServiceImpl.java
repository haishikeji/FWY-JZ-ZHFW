package cn.px.modules.skill.service.impl;

import cn.px.common.util.DateUtils;
import cn.px.modules.skill.entity.SkillNodeTrendsUpvote;
import cn.px.modules.skill.mapper.SkillNodeTrendsMapper;
import cn.px.modules.skill.mapper.SkillNodeTrendsUpvoteMapper;
import cn.px.modules.skill.service.ISkillNodeTrendsUpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: skill_node_trends_upvote
 * @@author 品讯科技
 * @Date: 2023-08-21
 * @Version: V1.0
 */
@Service
public class SkillNodeTrendsUpvoteServiceImpl extends ServiceImpl<SkillNodeTrendsUpvoteMapper, SkillNodeTrendsUpvote> implements ISkillNodeTrendsUpvoteService {

    @Autowired
    private SkillNodeTrendsUpvoteMapper skillNodeTrendsUpvoteMapper;
    @Autowired
    private SkillNodeTrendsMapper skillNodeTrendsMapper;

    @Override
    public SkillNodeTrendsUpvote isWhetherUpvote(SkillNodeTrendsUpvote skillNodeTrendsUpvote) {
        return skillNodeTrendsUpvoteMapper.isWhetherUpvote(skillNodeTrendsUpvote);
    }

    @Override
    public Integer updateStatus(SkillNodeTrendsUpvote skillNodeTrendsUpvote) {
        return skillNodeTrendsUpvoteMapper.updateStatus(skillNodeTrendsUpvote);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upvote(SkillNodeTrendsUpvote skillNodeTrendsUpvote) {

        //先去查点赞表是否存在评论信息
        SkillNodeTrendsUpvote whetherUpvote = this.isWhetherUpvote(skillNodeTrendsUpvote);
        //判断用户是否点赞第一次点赞
        if (whetherUpvote==null) {
            //未点赞插入一条新数据
            skillNodeTrendsUpvote.setStatus("1");
            //节点动态点赞数加1
            skillNodeTrendsMapper.autoSupportNumber(skillNodeTrendsUpvote.getTrendsId());
            skillNodeTrendsUpvote.setCreateTime(DateUtils.getDate());
            return skillNodeTrendsUpvoteMapper.insert(skillNodeTrendsUpvote);
        } else {
            //已点赞就修改点赞状态为取消点赞
            if ("1".equals(whetherUpvote.getStatus())){
                skillNodeTrendsUpvote.setUpdateTime(DateUtils.getDate());
                skillNodeTrendsUpvote.setStatus("2");
                //这个接口是减去当前评论的点赞数量
                skillNodeTrendsMapper.minusSupportNumber(skillNodeTrendsUpvote.getTrendsId());
            }else {
                //如果用户继续点赞走的逻辑修改状态为已点赞
                skillNodeTrendsUpvote.setUpdateTime(DateUtils.getDate());
                skillNodeTrendsUpvote.setStatus("1");
                //这个接口是自增当前评论的点赞数量
                skillNodeTrendsMapper.autoSupportNumber(skillNodeTrendsUpvote.getTrendsId());
            }
            //最后走修改接口
            Integer integer = skillNodeTrendsUpvoteMapper.updateStatus(skillNodeTrendsUpvote);
            return integer;
        }
    }
}
