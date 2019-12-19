package pers.liujunyi.cloud.centre.repository.jpa.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pers.liujunyi.cloud.centre.entity.user.UserDetailsInfo;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;

import java.util.Date;
import java.util.List;

/***
 * 文件名称: StaffDetailsInfoRepository.java
 * 文件描述: 职工档案信息 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface UserDetailsInfoRepository extends BaseJpaRepository<UserDetailsInfo, Long> {

    /**
     * 修改状态
     * @param userStatus  0：正常  1：冻结  2：离职
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query(value = "update user_details_info u set u.user_status = ?1, u.update_time = ?2 where u.id in (?3)", nativeQuery = true)
    int setUserStatusByIds(Byte userStatus, Date updateTime, List<Long> ids);

    /**
     * 设置头像
     * @param id 员工id
     * @param portrait  头像url
     * @param portraitId  头像id
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query("update UserDetailsInfo u set u.userPortrait = ?2, u.updateTime = ?4, u.userPortraitId = ?3 where u.id = ?1 ")
    int setPortrait(Long id, String portrait, Long portraitId, Date updateTime);


}