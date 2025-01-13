package cn.px.modules.ass.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import cn.px.modules.ass.entity.AssOrder;
import cn.px.modules.ass.entity.AssOrderComplaint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单投诉表
 * @@author 品讯科技
 * @Date:   2022-08-31
 * @Version: V1.0
 */
public interface AssOrderComplaintMapper extends BaseMapper<AssOrderComplaint> {

    AssOrderComplaint selectByOrderId(String id);

    List<AssOrderComplaint> selectAll(@Param("pojo") AssOrderComplaint assOrderComplaint);

}
