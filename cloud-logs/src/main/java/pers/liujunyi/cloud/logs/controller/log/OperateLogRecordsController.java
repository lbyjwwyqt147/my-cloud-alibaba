package pers.liujunyi.cloud.logs.controller.log;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.liujunyi.cloud.common.annotation.ApiVersion;
import pers.liujunyi.cloud.common.controller.BaseController;
import pers.liujunyi.cloud.common.dto.blogs.OperateLogRecordsDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.logs.domain.log.OperateLogRecordsQueryDto;
import pers.liujunyi.cloud.logs.repository.elasticsearch.log.ChangeRecordLogElasticsearchRepository;
import pers.liujunyi.cloud.logs.service.log.OperateLogRecordsElasticsearchService;
import pers.liujunyi.cloud.logs.service.log.OperateLogRecordsService;

import javax.validation.Valid;

/***
 * 文件名称: OperateLogRecordsController.java
 * 文件描述: 操作日志记录 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "操作日志记录 API")
@RestController
public class OperateLogRecordsController extends BaseController {

    @Autowired
    private OperateLogRecordsService operateLogRecordsService;
    @Autowired
    private ChangeRecordLogElasticsearchRepository changeRecordLogElasticsearchRepository;
    @Autowired
    private OperateLogRecordsElasticsearchService operateLogRecordsElasticsearchService;


    /**
     * 保存数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "保存数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @PostMapping(value = "ignore/logs/records/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@RequestBody String param) {
        OperateLogRecordsDto record = JSONObject.parseObject(param, OperateLogRecordsDto.class);
        return this.operateLogRecordsService.saveRecord(record);
    }

    /**
     * 根据操作日志ID 查询变更记录
     *
     * @param logId
     * @return
     */
    @ApiOperation(value = "根据操作日志ID 查询变更记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @PostMapping(value = "table/logs/change/grid")
    @ApiVersion(1)
    public ResultInfo findChanLogs(String logId) {
        return ResultUtil.success(this.changeRecordLogElasticsearchRepository.findByLogId(logId));
    }


    /**
     * 操作日志分页列表数据
     *
     * @param query
     * @return
     */
    @ApiOperation(value = "操作日志分页列表数据", notes = "适用于分页grid 显示数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @GetMapping(value = "table/logs/grid")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid OperateLogRecordsQueryDto query) {
        return this.operateLogRecordsElasticsearchService.findPageGird(query);
    }

}
