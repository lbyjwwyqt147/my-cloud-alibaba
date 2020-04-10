package pers.liujunyi.cloud.logs.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.liujunyi.cloud.common.repository.elasticsearch.BaseElasticsearchRepository;
import pers.liujunyi.cloud.common.service.impl.BaseElasticsearchServiceImpl;
import pers.liujunyi.cloud.logs.entity.log.ChangeRecordLog;
import pers.liujunyi.cloud.logs.repository.elasticsearch.log.ChangeRecordLogElasticsearchRepository;
import pers.liujunyi.cloud.logs.service.log.ChangeRecordLogElasticsearchService;

import java.util.List;

/***
 * 文件名称: ChangeRecordLogElasticsearchServiceImpl.java
 * 文件描述: 变更记录 Elasticsearch Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020年03月25日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class ChangeRecordLogElasticsearchServiceImpl extends BaseElasticsearchServiceImpl<ChangeRecordLog, Long> implements ChangeRecordLogElasticsearchService {

    @Autowired
    private ChangeRecordLogElasticsearchRepository changeRecordLogElasticsearchRepository;

    public ChangeRecordLogElasticsearchServiceImpl(BaseElasticsearchRepository<ChangeRecordLog, Long> baseElasticsearchRepository) {
        super(baseElasticsearchRepository);
    }


    @Override
    public List<ChangeRecordLog> findByLogId(String logId) {
        return this.changeRecordLogElasticsearchRepository.findByLogId(logId);
    }
}
