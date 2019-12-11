package pers.liujunyi.cloud.centre.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.jpa.annotation.MatchType;
import pers.liujunyi.cloud.common.query.jpa.annotation.QueryCondition;
import pers.liujunyi.cloud.common.query.mongo.BaseMongoQuery;

/***
 * 文件名称: UserDetailsInfoQueryDto.java
 * 文件描述: 员工档案 query dto
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月22日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailsInfoQueryDto extends BaseMongoQuery {
    private static final long serialVersionUID = -1582195362858567940L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @QueryCondition(func = MatchType.like)
    private String userNumber;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "员工姓名")
    @QueryCondition(func = MatchType.like)
    private String userFullName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @QueryCondition()
    private String mobilePhone;

    /**
     * 职位
     */
    @ApiModelProperty(value = "职务")
    @QueryCondition()
    private Byte userPosition;

    /**
     * 状态：0：正常  1：禁用  2：离职
     */
    @ApiModelProperty(value = "状态")
    @QueryCondition()
    private Byte userStatus;

}
