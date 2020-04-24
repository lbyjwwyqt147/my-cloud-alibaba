package pers.liujunyi.cloud.photo.distribute.repository.jpa.apportion;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.photo.distribute.entity.apportion.OrderApportion;

/***
 * 文件名称: OrderApportionRepository.java
 * 文件描述: 订单分配 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface OrderApportionRepository extends BaseJpaRepository<OrderApportion, Long> {

    /**
     * 修改状态
     * @param status  0：正常  1：禁用过期
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query(value = "update order_apportion u set u.status = ?1, u.update_time = now(), u.data_version = data_version+1 where u.id = ?2 ", nativeQuery = true)
    int setStatusById(Byte status, Long id);

}
