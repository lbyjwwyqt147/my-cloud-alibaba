package pers.liujunyi.cloud.centre.repository.mongo.user;


import pers.liujunyi.cloud.centre.entity.user.UserDetailsInfo;
import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;

/***
 * 文件名称: UserDetailsInfoMongoRepository.java
 * 文件描述: 用户详细档案信息 Mongo Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface UserDetailsInfoMongoRepository extends BaseMongoRepository<UserDetailsInfo, Long> {

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
    UserDetailsInfo findFirstByUserAccountsId(Long staffAccountsId);

}
