package pers.liujunyi.cloud.logs.repository.elasticsearch.log;

import org.springframework.stereotype.Repository;
import pers.liujunyi.cloud.common.repository.elasticsearch.BaseElasticsearchRepository;
import pers.liujunyi.cloud.logs.entity.log.ChangeRecordLog;

import java.util.List;

/***
 * 文件名称: ChangeRecordLogElasticsearchRepository.java
 * 文件描述: 变更记录 Elasticsearch Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020年03月25日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Repository
public interface ChangeRecordLogElasticsearchRepository extends BaseElasticsearchRepository<ChangeRecordLog, Long> {

    /**
     * 根据 logId 获取日志变更记录
     * @param logId
     * @return
     */
    List<ChangeRecordLog> findByLogId(String logId);

}
