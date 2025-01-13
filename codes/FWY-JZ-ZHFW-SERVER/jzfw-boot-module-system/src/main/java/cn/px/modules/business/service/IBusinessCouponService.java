package cn.px.modules.business.service;

import cn.px.modules.entity.BusinessCoupon;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 优惠券
 * @@author 品讯科技
 * @Date:   2023-03-14
 * @Version: V1.0
 */
public interface IBusinessCouponService extends IService<BusinessCoupon> {

    public void createJobByPublishDate(BusinessCoupon businessCoupon);

    /**
     * 发布优惠券
     * @param id
     */
    public void publishCoupon(String id);
}
