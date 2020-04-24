package pers.liujunyi.cloud.photo.distribute.service.apportion;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseMongoService;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionQueryDto;
import pers.liujunyi.cloud.photo.distribute.entity.apportion.OrderApportion;


/***
 * 文件名称: OrderApportionMongoService.java
 * 文件描述: 订单分配 Mongo Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderApportionMongoService extends BaseMongoService<OrderApportion, Long> {

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(OrderApportionQueryDto query);

    /**
     * 根据ID 获取详细数据(包含相册图片信息)
     * @param id
     * @return
     */
    ResultInfo details(Long id);


}
