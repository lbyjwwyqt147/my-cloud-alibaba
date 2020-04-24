package pers.liujunyi.cloud.photo.order.domain.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单 vo
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderVo extends OrderDto {


    private static final long serialVersionUID = -5561523891355064111L;
}
