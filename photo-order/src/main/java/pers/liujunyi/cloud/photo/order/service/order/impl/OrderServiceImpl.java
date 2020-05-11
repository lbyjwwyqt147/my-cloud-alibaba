package pers.liujunyi.cloud.photo.order.service.order.impl;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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
@Log4j2
@Service
public class OrderServiceImpl extends BaseJpaMongoServiceImpl<OrderInfo, Long> implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMongoService orderMongoService;
    @Autowired
    private RemoteCloudUtil remoteCloudUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    HttpServletRequest request;

    private final String PAY_SERVICE_HOST = "http://127.0.0.1:18088/api/v1/verify/order/apportion/s";


    public OrderServiceImpl(BaseJpaRepository<OrderInfo, Long> baseRepository) {
        super(baseRepository);
    }

    /**
     * 方法上已经通过AOP设置了本地事物
     * @param record
     * @return
     */
    @Override
    public ResultInfo saveRecord(OrderDto record) {
        log.info("事务xid: " + RootContext.getXID());
        OrderInfo order = new OrderInfo();
        order.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        order.setOrderName("测试订单");
        order.setOrderClassify("1");
        order.setOrderStatus((byte) 0);
        order.setOrderRemarks("测试下单回滚");
        OrderInfo saveObj = this.orderRepository.save(order);
        if (saveObj != null && saveObj.getId() != null) {
           // this.orderMongoService.save(saveObj);
            //orderRepository.setStatus(6L, (byte)2, new Date());
            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                headers.add(key, value);
            }
            //调用其他服务
            ResultInfo result = restTemplate.postForObject(PAY_SERVICE_HOST, new HttpEntity<String>(headers), ResultInfo.class);
            log.info(result.getMessage());
            // 使用注解开启分布式事务时，若要求事务回滚，必须将异常抛出到事务的发起方，被事务发起方的 @GlobalTransactional 注解感知到。provide 直接抛出异常 或 定义错误码由 consumer 判断再抛出异常。
            if (!result.getSuccess()) {
                log.info("载入事务 " +  RootContext.getXID() + " 进行回滚" );
                try {
                    GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                } catch (TransactionException e) {
                    e.printStackTrace();
                }
            }
        }
        int i = 0/0;
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


    /**
     * 在业务的发起方的方法上使用@GlobalTransactional开启全局事务，Seata 会将事务的 xid 通过拦截器添加到调用其他服务的请求中，实现分布式事务
     * 业务发起方方法上增加全局事务的注解@GlobalTransactional  其他远端服务的方法中增加注解@Transactional，表示开启事务。
     * 需要将本地事物放到全局事物GlobalTransactional 内层
     * 使用注解开启分布式事务时，若要求事务回滚，必须将异常抛出到事务的发起方，被事务发起方的 @GlobalTransactional 注解感知到。provide 直接抛出异常 或 定义错误码由 consumer 判断再抛出异常。
     * @param record
     * @return
     */
    @GlobalTransactional
    @Override
    public ResultInfo test(OrderDto record) {
        // saveRecord 本身携带本地事物， 将本地事物放到全局事物GlobalTransactional 内层
        return saveRecord(record);
    }

}
