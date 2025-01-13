package cn.px.modules.medicalcare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.medicalcare.entity.MedicalwareCouponUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: medicalware_coupon_user
 * @@author 品讯科技
 * @Date:   2022-07-07
 * @Version: V1.0
 */
public interface MedicalwareCouponUserMapper extends BaseMapper<MedicalwareCouponUser> {
    /**
     * 我的优惠券列表
     * @param userId 用户id
     * @return
     */
    public List<MedicalwareCouponUser> seleCtcouponUserByUserId(String userId);

    public List<MedicalwareCouponUser> selectCouponUserOverdue();
}
