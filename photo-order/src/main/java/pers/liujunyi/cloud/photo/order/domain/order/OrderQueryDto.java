package pers.liujunyi.cloud.photo.order.domain.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.mongo.BaseMongoQuery;


/***
 * 文件名称: OrderQueryDto.java
 * 文件描述: 订单 query dto
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
public class OrderQueryDto extends BaseMongoQuery {

    private static final long serialVersionUID = 6401319051807067756L;
}
