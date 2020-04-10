package pers.liujunyi.cloud.logs.service.log;


import pers.liujunyi.cloud.common.dto.blogs.OperateLogRecordsDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseJpaElasticsearchService;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;

/***
 * 文件名称: OperateLogRecordsService.java
 * 文件描述:  操作日志记录  Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OperateLogRecordsService extends BaseJpaElasticsearchService<OperateLogRecords, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(OperateLogRecordsDto record);

}
