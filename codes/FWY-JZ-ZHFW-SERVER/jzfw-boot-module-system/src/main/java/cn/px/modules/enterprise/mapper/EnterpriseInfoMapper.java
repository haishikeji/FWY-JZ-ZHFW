package cn.px.modules.enterprise.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import cn.px.config.mapper.AbstractMapper;
import cn.px.modules.enterprise.entity.EnterpriseInfo;

/**
 * @Description: 企业信息表
 * @@author 品讯科技
 * @Date:   2022-08-05
 * @Version: V1.0
 */
public interface EnterpriseInfoMapper extends AbstractMapper<EnterpriseInfo> {
    /**
     * 获取企业下拉选数据
     * @return
     */
    public List<Map> selectEnterpriseList();

    /**
     * 协会端首页统计
     * @return
     */
    public List<Map> selectHomePageStatistics(String enterpriseId);
    public List<EnterpriseInfo> enterpriseList();

    /**
     * 协会端首页统计学历统计
     * @return
     */
    public List<Map> selectEducation(String enterpriseId);

    /**
     * 协会端首页统计性别
     * @return
     */
    public List<Map> selectSex();

    /**
     * 协会端首页统计家政员数量
     * @return
     */
    public List<Map> selectQyTopStaff();

    /**
     * 协会端首页统计客户数
     * @return
     */
    public List<Map> selectQyTopUser();

    /**
     * 协会端首页统计订单数
     * @return
     */
    public List<Map> selectQyTopOrders();

    /**
     * 注册资金
     * @return
     */
    public List<Map> selectRegisteredCapital();

    /**
     * 太原市区域统计
     * @return
     */
    public List<Map> selectProvince();

    /**
     * 太原市营业额统计
     * @return
     */
    public List<Map> selectTurnover();

    /**
     * 获取企业数量和家政员数量
     * @return
     */
    public List<Map> selectCount();

    /**
     * 获取家政员经验年限统计
     * @return
     */
    public List<Map> selectExperienceAgeLimit();

    /**
     * 获取家政员
     * @return
     */
    public List<Map> selectCertificatesNumber();

    /**
     * 获取家政服务类目企业数量
     * @return
     */
    public List<Map> selectServe();

    /**
     * 获取需要处理的年审企业
     * @return
     */
    public List<EnterpriseInfo> selectAuditByTime();

    /**
     * 查询评分排行
     * @param enterpriseId
     * @return
     */
    public String queryEnterpriseSort(String enterpriseId);

    /**
     * 判断优惠券批次头部字符串是否重复
     * @param enterpriseInfo
     * @return
     */
    public Integer couponBatchHeadIsExit(EnterpriseInfo enterpriseInfo);

    List<Map<String, Object>> getYearOrderCount(@Param("enterpriseId") Long enterpriseId);
}
