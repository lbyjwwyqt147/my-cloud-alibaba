package pers.liujunyi.cloud.photo.distribute.service.apportion;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseJpaMongoService;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionDto;
import pers.liujunyi.cloud.photo.distribute.entity.apportion.OrderApportion;


/***
 * 文件名称: OrderApportionService.java
 * 文件描述:  订单分配 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderApportionService extends BaseJpaMongoService<OrderApportion, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(OrderApportionDto record);

    /**
     * 修改状态
     * @param status   0：正常  1：已过期
     * @param id
     * @return
     */
    ResultInfo updateStatus(Byte status, Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultInfo deleteSingle(Long id);

    /**
     * 同步数据到Mongo中
     * @return
     */
    ResultInfo syncDataToMongo();


}
