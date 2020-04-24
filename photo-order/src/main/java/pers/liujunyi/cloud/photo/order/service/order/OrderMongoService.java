package pers.liujunyi.cloud.photo.order.service.order;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseMongoService;
import pers.liujunyi.cloud.photo.order.domain.order.OrderQueryDto;
import pers.liujunyi.cloud.photo.order.entity.order.OrderInfo;


/***
 * 文件名称: OrderMongoService.java
 * 文件描述: 订单 Mongo Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderMongoService extends BaseMongoService<OrderInfo, Long> {

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(OrderQueryDto query);

    /**
     * 根据ID 获取详细数据
     * @param id
     * @return
     */
    ResultInfo details(Long id);

}
