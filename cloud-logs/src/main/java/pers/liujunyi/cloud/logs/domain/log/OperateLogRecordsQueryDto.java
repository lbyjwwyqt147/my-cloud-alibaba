package pers.liujunyi.cloud.logs.domain.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.elasticsearch.BaseEsQuery;
import pers.liujunyi.cloud.common.query.jpa.annotation.MatchType;
import pers.liujunyi.cloud.common.query.jpa.annotation.QueryCondition;


/***
 * 文件名称: OperateLogRecordsQueryDto.java
 * 文件描述: 操作日志记录 query dto
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月26日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLogRecordsQueryDto extends BaseEsQuery {


    /** 操作人id */
    @ApiModelProperty(value = "操作人")
    @QueryCondition()
    private Long operateUserId;

    /** 操作模块 */
    @ApiModelProperty(value = "操作模块")
    @QueryCondition(func = MatchType.like)
    private String operateModule;

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型")
    @QueryCondition()
    private Byte operateType;

    /** 日志类型 */
    @ApiModelProperty(value = "日志类型")
    @QueryCondition()
    private Byte logType;

    /** 操作状态 */
    @ApiModelProperty(value = "操作状态")
    @QueryCondition()
    private Byte operateStatus;

    /** 操作人名称 */
    @ApiModelProperty(value = "操作人名称")
    @QueryCondition(func = MatchType.like)
    private String operateUserName;

}
