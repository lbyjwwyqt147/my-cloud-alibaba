package pers.liujunyi.cloud.logs.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import pers.liujunyi.cloud.common.repository.elasticsearch.BaseElasticsearchRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseElasticsearchServiceImpl;
import pers.liujunyi.cloud.logs.domain.log.OperateLogRecordsQueryDto;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;
import pers.liujunyi.cloud.logs.repository.elasticsearch.log.OperateLogRecordsElasticsearchRepository;
import pers.liujunyi.cloud.logs.service.log.OperateLogRecordsElasticsearchService;

/***
 * 文件名称: OperateLogRecordsElasticsearchServiceImpl.java
 * 文件描述: 操作日志 Elasticsearch Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020年03月25日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class OperateLogRecordsElasticsearchServiceImpl extends BaseElasticsearchServiceImpl<OperateLogRecords, Long> implements OperateLogRecordsElasticsearchService {

    @Autowired
    private OperateLogRecordsElasticsearchRepository operateLogRecordsElasticsearchRepository;

    public OperateLogRecordsElasticsearchServiceImpl(BaseElasticsearchRepository<OperateLogRecords, Long> baseElasticsearchRepository) {
        super(baseElasticsearchRepository);
    }


    @Override
    public ResultInfo findPageGird(OperateLogRecordsQueryDto query) {
        //分页参数
        Pageable pageable = query.toPageable(Sort.Direction.ASC, "responseStartTime");
        // 查询数据
        SearchQuery searchQuery = query.toSpecPageable(pageable);
        Page<OperateLogRecords> searchPageResults = this.operateLogRecordsElasticsearchRepository.search(searchQuery);
        Long totalElements =  searchPageResults.getTotalElements();
        ResultInfo result = ResultUtil.success(searchPageResults);
        result.setTotal(totalElements);
        return  result;
    }
}
