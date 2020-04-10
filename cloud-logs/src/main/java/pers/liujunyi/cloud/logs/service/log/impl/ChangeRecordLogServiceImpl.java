package pers.liujunyi.cloud.logs.service.log.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.service.impl.BaseJpaElasticsearchServiceImpl;
import pers.liujunyi.cloud.logs.entity.log.ChangeRecordLog;
import pers.liujunyi.cloud.logs.repository.elasticsearch.log.ChangeRecordLogElasticsearchRepository;
import pers.liujunyi.cloud.logs.repository.jpa.log.ChangeRecordLogRepository;
import pers.liujunyi.cloud.logs.service.log.ChangeRecordLogService;

import java.util.List;

/***
 * 文件名称: ChangeRecordLogServiceImpl.java
 * 文件描述: 操作日志记录 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class ChangeRecordLogServiceImpl extends BaseJpaElasticsearchServiceImpl<ChangeRecordLog, Long> implements ChangeRecordLogService {

    @Autowired
    private ChangeRecordLogRepository changeRecordLogRepository;
    @Autowired
    private ChangeRecordLogElasticsearchRepository changeRecordLogElasticsearchRepository;


    public ChangeRecordLogServiceImpl(BaseJpaRepository<ChangeRecordLog, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public int saveRecord(ChangeRecordLog record) {
        ChangeRecordLog saveObject = this.changeRecordLogRepository.save(record);
        if (saveObject == null || saveObject.getId() == null) {
            return 0;
        } else {
            ChangeRecordLog changeRecordLog = this.changeRecordLogElasticsearchRepository.save(saveObject);
            if (changeRecordLog == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int saveRecords(List<ChangeRecordLog> records) {
        List<ChangeRecordLog> saveObject = this.changeRecordLogRepository.saveAll(records);
        if (saveObject == null || saveObject.size() == 0) {
            return 0;
        } else {
            List<ChangeRecordLog> logList = (List<ChangeRecordLog>) this.changeRecordLogElasticsearchRepository.saveAll(records);
            if (logList == null || saveObject.size() == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return 1;
    }


}
