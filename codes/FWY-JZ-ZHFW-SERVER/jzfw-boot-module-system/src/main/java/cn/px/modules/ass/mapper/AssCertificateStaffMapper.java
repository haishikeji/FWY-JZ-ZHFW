package cn.px.modules.ass.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.px.modules.ass.entity.AssCertificateStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 证书用户表
 * @@author 品讯科技
 * @Date:   2022-11-28
 * @Version: V1.0
 */
public interface AssCertificateStaffMapper extends BaseMapper<AssCertificateStaff> {
    /**
     * 通过用户id删除子表数据
     * @param StaffId
     * @return
     */
    public int deleteCertificateByStaffId(String StaffId);
    List<AssCertificateStaff> staffInfoList(String StaffId);
}
