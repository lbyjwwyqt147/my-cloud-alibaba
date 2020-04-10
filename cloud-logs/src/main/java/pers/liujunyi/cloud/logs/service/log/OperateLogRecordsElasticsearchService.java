package pers.liujunyi.cloud.logs.service.log;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseElasticsearchService;
import pers.liujunyi.cloud.logs.domain.log.OperateLogRecordsQueryDto;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;

/***
 * 文件名称: OperateLogRecordsElasticsearchService.java
 * 文件描述: 操作日志 Elasticsearch Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期: 2020年03月25日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OperateLogRecordsElasticsearchService extends BaseElasticsearchService<OperateLogRecords, Long> {

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(OperateLogRecordsQueryDto query);

}
