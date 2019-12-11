package pers.liujunyi.cloud.centre.service.user;

import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoQueryDto;
import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoVo;
import pers.liujunyi.cloud.centre.entity.user.UserDetailsInfo;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseMongoService;

import java.util.List;
import java.util.Map;

/***
 * 文件名称: UserDetailsInfoMongoService.java
 * 文件描述: 用户详细档案 Mongo Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface UserDetailsInfoMongoService extends BaseMongoService<UserDetailsInfo, Long> {

    /**
     * 根据 帐号id 获取员工详细数据
     * @param staffAccountsId
     * @param  staffStatus   0：正常  1：冻结  2：离职
     * @return
     */
    UserDetailsInfo findFirstByUserAccountsIdAndStaffStatus(Long staffAccountsId, Byte staffStatus);

    /**
     * 根据 帐号id 获取员工详细数据
     * @param staffAccountsId
     * @return
     */
    UserDetailsInfo findUserDetails(Long staffAccountsId);

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(UserDetailsInfoQueryDto query);

    /**
     * 根据 帐号id 获取员工详细数据
     * @param staffAccountsId
     * @return
     */
    ResultInfo findByUserAccountsId(Long staffAccountsId);

    /**
     * 根据 帐号id 获取员工详细数据
     * @param staffAccountsId
     * @return
     */
    UserDetailsInfoVo getStaffDetailsByUserAccountsId(Long staffAccountsId);


    /**
     * 根据 id 获取员工详细数据
     * @param id
     * @return
     */
    UserDetailsInfoVo getUserDetailsById(Long id);

    /**
     * 员工下拉框数据
     * @param query
     * @return
     */
    List<Map<String, String>> staffSelect(UserDetailsInfoQueryDto query);

    /**
     * 获取职工名称
     * @param ids
     * @return
     */
    Map<Long, String> getStaffNameMap(List<Long> ids);

    /**
     * 获取职工信息
     * @param ids
     * @return
     */
    Map<Long, UserDetailsInfo> getDetailsInfoMap(List<Long> ids);
}
