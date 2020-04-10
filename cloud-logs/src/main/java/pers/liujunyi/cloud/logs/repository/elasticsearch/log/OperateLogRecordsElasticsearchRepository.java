package pers.liujunyi.cloud.logs.repository.elasticsearch.log;

import org.springframework.stereotype.Repository;
import pers.liujunyi.cloud.common.repository.elasticsearch.BaseElasticsearchRepository;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;

/***
 * 文件名称: OperateLogRecordsElasticsearchRepository.java
 * 文件描述: 操作日志 Elasticsearch Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期: 2020年03月25日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Repository
public interface OperateLogRecordsElasticsearchRepository extends BaseElasticsearchRepository<OperateLogRecords, Long> {

}
