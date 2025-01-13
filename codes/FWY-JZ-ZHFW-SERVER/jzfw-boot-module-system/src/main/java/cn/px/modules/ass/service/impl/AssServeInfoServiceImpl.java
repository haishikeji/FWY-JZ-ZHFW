package cn.px.modules.ass.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import cn.px.common.constant.CommonConstant;
import cn.px.common.system.vo.LoginUser;
import cn.px.modules.ass.entity.AssServeCategory;
import cn.px.modules.ass.entity.AssServeInfo;
import cn.px.modules.ass.mapper.AssServeCategoryMapper;
import cn.px.modules.ass.mapper.AssServeInfoMapper;
import cn.px.modules.ass.service.IAssServeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description: 服务项目管理
 * @@author 品讯科技
 * @Date:   2022-08-09
 * @Version: V1.0
 */
@Service
public class AssServeInfoServiceImpl extends ServiceImpl<AssServeInfoMapper, AssServeInfo> implements IAssServeInfoService {


    @Autowired
    private AssServeInfoMapper serveInfoMapper;

    @Autowired
    private AssServeCategoryMapper serveCategoryMapper;

    /**
     * 服务保存接口
     * @return
     */
    @Override
    @Transactional
    public int saveData(AssServeInfo serveInfo){
        //获取当前登录人
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //获取企业id
        serveInfo.setEnterpriseId(user.getEnterpriseId());

        int i = 0;

        //判断修改删除
        if(StringUtils.isEmpty(serveInfo.getId())){//为空为新增

            //新增服务
            serveInfo.setDelFlag(CommonConstant.DEL_FLAG_0);
            serveInfo.setStatus("01");
            //新增排序
            if(StringUtils.isEmpty(serveInfo.getSort())){//查询最大排序
                serveInfo.setSort(serveInfoMapper.getServeMaxSort(serveInfo.getEnterpriseId()));
            }

            i = serveInfoMapper.insert(serveInfo);



        }else {//不为空为修改
            i = serveInfoMapper.updateById(serveInfo);

            if(i > 0){
                //删除分类
                serveCategoryMapper.delete(Wrappers.<AssServeCategory>query().lambda().eq(AssServeCategory::getSevreId,serveInfo.getId()));
            }
        }

        if(i > 0){
            //新增分类
            switch (serveInfo.getServeClassify()){//判断分类
                case "01"://单选服务
                    AssServeCategory category = serveInfo.getServeCategory();
                    category.setSevreId(serveInfo.getId());
                    serveCategoryMapper.insert(category);
                    break;
                case "02"://套餐服务
                    List<AssServeCategory> categoryList = serveInfo.getServeCategoryList();
                    if(categoryList != null && categoryList.size() > 0){
                        for (AssServeCategory serveCategory : categoryList){
                            serveCategory.setSevreId(serveInfo.getId());
                            serveCategoryMapper.insert(serveCategory);
                        }
                    }
                    break;
            }
        }

        return i;
    }
}
