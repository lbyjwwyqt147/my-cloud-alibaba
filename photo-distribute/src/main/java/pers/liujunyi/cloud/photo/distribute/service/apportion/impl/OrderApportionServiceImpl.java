package pers.liujunyi.cloud.photo.distribute.service.apportion.impl;

import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseJpaMongoServiceImpl;
import pers.liujunyi.cloud.common.util.RemoteCloudUtil;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionDto;
import pers.liujunyi.cloud.photo.distribute.entity.apportion.OrderApportion;
import pers.liujunyi.cloud.photo.distribute.repository.jpa.apportion.OrderApportionRepository;
import pers.liujunyi.cloud.photo.distribute.service.apportion.OrderApportionMongoService;
import pers.liujunyi.cloud.photo.distribute.service.apportion.OrderApportionService;

/***
 * 文件名称: OrderApportionServiceImpl.java
 * 文件描述: 订单分配 Service Impl
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
public class OrderApportionServiceImpl extends BaseJpaMongoServiceImpl<OrderApportion, Long> implements OrderApportionService {

    @Autowired
    private OrderApportionRepository orderApportionRepository;
    @Autowired
    private OrderApportionMongoService orderApportionMongoService;

    @Autowired
    private RemoteCloudUtil remoteCloudUtil;


    public OrderApportionServiceImpl(BaseJpaRepository<OrderApportion, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public ResultInfo saveRecord(OrderApportionDto record) {
        OrderApportion info = new OrderApportion();
        info.setOrderId(1L);
        info.setOrderNumber("10");
        info.setOrderName("测试回滚");
        info.setOrderClassify("10");
        info.setStatus((byte) 0);
        info.setUserId(1L);
        info.setApportionNumber("110");
        info.setRemarks("测试");
        log.info("事务xid：" + RootContext.getXID());
        OrderApportion saveObj = this.orderApportionRepository.save(info);
        if (saveObj.getId() != null) {
          //  this.orderApportionMongoService.save(saveObj);
        }
       // int i = 0/0;
        return ResultUtil.success();
    }

    @Override
    public ResultInfo updateStatus(Byte status, Long id) {

        return ResultUtil.fail();
    }


    @Override
    public ResultInfo deleteSingle(Long id) {

        return ResultUtil.success();
    }

    @Override
    public ResultInfo syncDataToMongo() {
        super.syncDataMongoDb();

        return ResultUtil.success();
    }

}
