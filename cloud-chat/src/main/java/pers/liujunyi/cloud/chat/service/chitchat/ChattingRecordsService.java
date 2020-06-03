package pers.liujunyi.cloud.chat.service.chitchat;

import pers.liujunyi.cloud.chat.domain.chitchat.ChattingRecordsDto;
import pers.liujunyi.cloud.chat.entity.chitchat.ChattingRecords;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseJpaMongoService;


/***
 * 文件名称: ChattingRecordsService.java
 * 文件描述:  聊天纪录 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface ChattingRecordsService extends BaseJpaMongoService<ChattingRecords, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(ChattingRecordsDto record);

    /**
     * 修改状态
     * @param status
     * @param id
     * @return
     */
    ResultInfo updateStatus(Byte status, Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultInfo deleteSingle(Long id);



}
