package pers.liujunyi.cloud.photo.order.domain.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.dto.BaseDto;

/***
 * 文件名称: OrderDto.java
 * 文件描述: 订单
 * 公 司:
 * 内容摘要:
 *
 * 完成日期:2019年03月11日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends BaseDto {


    private static final long serialVersionUID = 3102642009250030724L;
}
