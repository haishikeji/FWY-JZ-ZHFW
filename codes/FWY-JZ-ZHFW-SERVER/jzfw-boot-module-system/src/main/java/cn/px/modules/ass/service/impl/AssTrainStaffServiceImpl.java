package cn.px.modules.ass.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.modules.ass.entity.AssMatchStaff;
import cn.px.modules.ass.entity.AssTrainStaff;
import cn.px.modules.ass.mapper.AssTrainStaffMapper;
import cn.px.modules.ass.service.IAssTrainStaffService;
import cn.px.modules.enterprise.entity.EnterpriseStaffInfo;
import cn.px.modules.enterprise.mapper.EnterpriseStaffGradeMapper;
import cn.px.modules.enterprise.mapper.EnterpriseStaffInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 培训报名表
 * @@author 品讯科技
 * @Date:   2022-11-22
 * @Version: V1.0
 */
@Service
public class AssTrainStaffServiceImpl extends ServiceImpl<AssTrainStaffMapper, AssTrainStaff> implements IAssTrainStaffService {
    @Autowired
    private AssTrainStaffMapper assTrainStaffMapper;
    @Autowired
    private EnterpriseStaffGradeMapper enterpriseStaffGradeMapper;
    @Autowired
    private EnterpriseStaffInfoMapper enterpriseStaffInfoMapper;
    /**
     * 新增，修改培训
     * @param train
     * @return
     */
    @Override
    @Transactional
    public int  saveData(AssTrainStaff train){
        int i =0;
        if(train.getId()==null){
            List<AssTrainStaff> trainList = assTrainStaffMapper.selectZTrainByStaffId(train.getStaffId());
            if(trainList==null||trainList.size()==0){
                i = assTrainStaffMapper.insert(train);
                //修改评级
                enterpriseStaffGradeMapper.updateGradeStatusByStaffId(train.getStaffId());
                //更新企业完整度
                EnterpriseStaffInfo info = enterpriseStaffInfoMapper.selectById(train.getStaffId());
                Integer informationPerfection = info.getInformationPerfection()-5;
                enterpriseStaffInfoMapper.updateInformationPerfection(informationPerfection,train.getStaffId());
            }else{
                i = assTrainStaffMapper.insert(train);
            }
        }else{
            i =  assTrainStaffMapper.updateById(train);
        }
        return i;
    }

    /**
     * 通过id删除家政人员培训
     * @param id
     * @param staffId
     * @return
     */
    @Override
    @Transactional
    public int delData(String id,String staffId){
        int i = assTrainStaffMapper.deleteById(id);
        List<AssTrainStaff> trainList = assTrainStaffMapper.selectZTrainByStaffId(staffId);
        if(trainList==null||trainList.size()==0){
            enterpriseStaffGradeMapper.updateGradeStatusByStaffId(staffId);
            //修改评级
            enterpriseStaffGradeMapper.updateGradeStatusByStaffId(staffId);
            //更新企业完整度
            EnterpriseStaffInfo info = enterpriseStaffInfoMapper.selectById(staffId);
            Integer informationPerfection = info.getInformationPerfection()-5;
            enterpriseStaffInfoMapper.updateInformationPerfection(informationPerfection,staffId);
        }
        return i;
    }

    @Override
    public Page<AssTrainStaff> selectTrainPageList(Page<AssTrainStaff> page, AssTrainStaff assTrainStaff) {
        return page.setRecords(assTrainStaffMapper.selectTrainPageList(page, assTrainStaff));
    }
}
