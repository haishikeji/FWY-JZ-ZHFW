package cn.px.modules.ass.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.modules.ass.entity.AssMatch;
import cn.px.modules.ass.mapper.AssMatchMapper;
import cn.px.modules.ass.service.IAssMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 比赛表
 * @@author 品讯科技
 * @Date:   2022-11-22
 * @Version: V1.0
 */
@Service
public class AssMatchServiceImpl extends ServiceImpl<AssMatchMapper, AssMatch> implements IAssMatchService {
    @Autowired
    private AssMatchMapper assMatchMapper;

    /**
     * 供app分页列表
     * @param page
     * @param assMatch
     * @return
     */
    @Override
    public Page<AssMatch> selectMatchByPage(Page<AssMatch> page, AssMatch assMatch){
        return page.setRecords(assMatchMapper.selectMatchByPage(page,assMatch));
    }
}
