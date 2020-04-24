package pers.liujunyi.cloud.photo.distribute.domain.apportion;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分配 vo
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderApportionVo extends OrderApportionDto {


    private static final long serialVersionUID = 6404498968042892455L;
}
