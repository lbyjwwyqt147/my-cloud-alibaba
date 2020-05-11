package pers.liujunyi.cloud.photo.order.repository.jpa.order;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.photo.order.entity.order.OrderInfo;

import java.util.Date;

/***
 * 文件名称: OrderRepository.java
 * 文件描述: 订单 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderRepository extends BaseJpaRepository<OrderInfo, Long> {

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query(value = "update order_info u set u.order_status = ?2, u.update_time = ?3 where u.id = ?1  ", nativeQuery = true)
    int updateStatus(Long id, Byte orderStatus, Date updateTime);

    /**
     * 修改状态
     * @param id
     * @param orderStatus
     * @param updateTime
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query("update OrderInfo u set u.orderStatus = ?2, u.updateTime = ?3 where u.id = ?1 ")
    int setStatus(Long id, Byte orderStatus, Date updateTime);

}
