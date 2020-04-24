package pers.liujunyi.cloud.photo.distribute.domain.apportion;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.mongo.BaseMongoQuery;


/***
 * 文件名称: OrderApportionQueryDto.java
 * 文件描述: 订单分配 query dto
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
public class OrderApportionQueryDto extends BaseMongoQuery {
    private static final long serialVersionUID = 4787026307143139598L;


}

