package cn.px.modules.ass.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.config.mapper.AbstractServiceDao;
import cn.px.modules.ass.entity.AssMatchStaff;

/**
 * @Description: 比赛报名表
 * @@author 品讯科技
 * @Date:   2022-11-22
 * @Version: V1.0
 */
public interface IAssMatchStaffService extends AbstractServiceDao<AssMatchStaff> {
    /**
     * 新增，修改比赛
     * @param match
     * @return
     */
    public int  saveData(AssMatchStaff match);

    /**
     * 通过id删除家政人员比赛
     * @param id
     * @param staffId
     * @return
     */
    public int delData(String id,String staffId);

    /**
     * 我的比赛
     * @param page
     * @param assMatchStaff
     * @return
     */
    public Page<AssMatchStaff> selectMatchPageList(Page<AssMatchStaff> page, AssMatchStaff assMatchStaff);
}
