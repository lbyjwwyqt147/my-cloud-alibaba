package pers.liujunyi.cloud.logs.service.log.impl;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pers.liujunyi.cloud.common.dto.blogs.OperateLogRecordsDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.common.util.UserContext;
import pers.liujunyi.cloud.logs.entity.log.ChangeRecordLog;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;
import pers.liujunyi.cloud.logs.repository.elasticsearch.log.OperateLogRecordsElasticsearchRepository;
import pers.liujunyi.cloud.logs.repository.jpa.log.OperateLogRecordsRepository;
import pers.liujunyi.cloud.logs.service.log.ChangeRecordLogService;
import pers.liujunyi.cloud.logs.service.log.OperateLogRecordsService;

import java.util.Date;
import java.util.List;

/***
 * 文件名称: OperateLogRecordsServiceImpl.java
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
public class OperateLogRecordsServiceImpl implements OperateLogRecordsService {

    @Autowired
    private OperateLogRecordsRepository operateLogRecordsRepository;
    @Autowired
    private ChangeRecordLogService changeRecordLogService;
    @Autowired
    private OperateLogRecordsElasticsearchRepository operateLogRecordsElasticsearchRepository;


    @Override
    public ResultInfo saveRecord(OperateLogRecordsDto record) {
        OperateLogRecords chattingRecord = DozerBeanMapperUtil.copyProperties(record, OperateLogRecords.class);
        chattingRecord.setDataVersion(1L);
        chattingRecord.setUpdateTime(new Date());
        chattingRecord.setUpdateUserId(UserContext.currentUserId());

        OperateLogRecords saveObject = this.operateLogRecordsRepository.save(chattingRecord);
        if (saveObject == null || saveObject.getId() == null) {
            return ResultUtil.fail();
        } else {
            if (StringUtils.isNotBlank(record.getChangeDataItem())) {
                List<ChangeRecordLog> changeRecordLogList = JSONObject.parseArray(record.getChangeDataItem(), ChangeRecordLog.class);
                int count = this.changeRecordLogService.saveRecords(changeRecordLogList);
                if (count == 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultUtil.fail();
                }
            }
            OperateLogRecords operateLogRecords = this.operateLogRecordsElasticsearchRepository.save(saveObject);
            if (operateLogRecords == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultUtil.fail();
            }
        }
        return ResultUtil.success();
    }



}
