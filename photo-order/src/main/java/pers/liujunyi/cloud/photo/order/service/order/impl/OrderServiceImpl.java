package pers.liujunyi.cloud.photo.order.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseJpaMongoServiceImpl;
import pers.liujunyi.cloud.common.util.RemoteCloudUtil;
import pers.liujunyi.cloud.photo.order.domain.order.OrderDto;
import pers.liujunyi.cloud.photo.order.entity.order.OrderInfo;
import pers.liujunyi.cloud.photo.order.repository.jpa.order.OrderRepository;
import pers.liujunyi.cloud.photo.order.service.order.OrderMongoService;
import pers.liujunyi.cloud.photo.order.service.order.OrderService;

/***
 * 文件名称: OrderServiceImpl.java
 * 文件描述: 订单 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class OrderServiceImpl extends BaseJpaMongoServiceImpl<OrderInfo, Long> implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMongoService orderMongoService;
    @Autowired
    private RemoteCloudUtil remoteCloudUtil;


    public OrderServiceImpl(BaseJpaRepository<OrderInfo, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public ResultInfo saveRecord(OrderDto record) {

        return ResultUtil.success();
    }

    @Override
    public ResultInfo updateStatus(Byte status, Long id) {

        return ResultUtil.fail();
    }

    @Override
    public ResultInfo syncDataToMongo() {
        super.syncDataMongoDb();
        return ResultUtil.success();
    }

}
