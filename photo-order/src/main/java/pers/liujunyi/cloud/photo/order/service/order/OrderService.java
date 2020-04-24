package pers.liujunyi.cloud.photo.order.service.order;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseJpaMongoService;
import pers.liujunyi.cloud.photo.order.domain.order.OrderDto;
import pers.liujunyi.cloud.photo.order.entity.order.OrderInfo;


/***
 * 文件名称: OrderService.java
 * 文件描述:  订单 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderService extends BaseJpaMongoService<OrderInfo, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(OrderDto record);

    /**
     * 修改状态
     * @param status   0：待支付  1：已取消  2：已支付
     * @param id
     * @return
     */
    ResultInfo updateStatus(Byte status, Long id);


    /**
     * 同步数据到Mongo中
     * @return
     */
    ResultInfo syncDataToMongo();


}
