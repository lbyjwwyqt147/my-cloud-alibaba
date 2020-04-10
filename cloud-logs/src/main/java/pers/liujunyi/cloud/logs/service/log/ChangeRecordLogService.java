package pers.liujunyi.cloud.logs.service.log;


import pers.liujunyi.cloud.common.service.BaseJpaElasticsearchService;
import pers.liujunyi.cloud.logs.entity.log.ChangeRecordLog;

import java.util.List;

/***
 * 文件名称: ChangeRecordLogService.java
 * 文件描述:  变更记录  Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface ChangeRecordLogService extends BaseJpaElasticsearchService<ChangeRecordLog, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    int saveRecord(ChangeRecordLog record);

    /**
     * 保存数据
     * @param records
     * @return
     */
    int saveRecords(List<ChangeRecordLog> records);
}
