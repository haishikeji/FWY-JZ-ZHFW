package cn.px.modules.enterprise.service;

import cn.px.modules.enterprise.entity.EnterpriseStaffService;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 家政人员评级服务明细
 * @@author 品讯科技
 * @Date:   2022-08-30
 * @Version: V1.0
 */
public interface IEnterpriseStaffServiceService extends IService<EnterpriseStaffService> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<EnterpriseStaffService>
	 */
	public List<EnterpriseStaffService> selectByMainId(String mainId);

	/**
	 * 家政人员上传服务
	 * @param service
	 * @return
	 */
	public int saveData(EnterpriseStaffService service);

	/**
	 * 家政人员上传服务
	 * @param id 服务id
	 * @return
	 */
	public int deleteData(String id,String staffId);
}
