package pers.liujunyi.cloud.centre.service.user;

import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoDto;
import pers.liujunyi.cloud.centre.entity.user.UserDetailsInfo;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseService;

import java.util.Date;
import java.util.List;

/***
 * 文件名称: UserDetailsInfoService.java
 * 文件描述:  用户详细档案 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface UserDetailsInfoService extends BaseService<UserDetailsInfo, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(UserDetailsInfoDto record);

    /**
     * 修改状态
     * @param status   0：正常  1：冻结  2：离职
     * @param ids
     * @return
     */
    ResultInfo updateStatus(Byte status, List<Long> ids, List<Long> userIds, String putParams);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    ResultInfo deleteBatch(List<Long> ids, List<Long> userIds);

    /**
     * 单条删除
     * @param id
     * @param userId
     * @return
     */
    ResultInfo deleteSingle(Long id, Long userId);

    /**
     * 同步数据到Mongo中
     * @return
     */
    ResultInfo syncDataToMongo();

    /**
     * 设置头像
     * @param id 员工id
     * @param portrait  头像url
     * @param portraitId  头像id
     * @return
     */
    ResultInfo setPortrait(Long id, String portrait, Long portraitId);

    /**
     * 设置员工离职信息
     * @param id
     * @param userId
     * @param date    离职日期
     * @param dimissionReason  离职原因
     * @param dataVersion
     * @return
     */
    ResultInfo setCurDimissionInfo(Long id, Long userId, Date date, String dimissionReason, Long dataVersion);
}
