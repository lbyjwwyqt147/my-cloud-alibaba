package pers.liujunyi.cloud.photo.distribute.entity.apportion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pers.liujunyi.cloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/***
 * 文件名称: 接单人ID.java
 * 文件描述: 订单分配
 * 公 司:
 * 内容摘要:

 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Data
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "order_apportion")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "order_apportion", comment = "订单分配")
public class OrderApportion extends BaseEntity {


    private static final long serialVersionUID = 1621758775214608087L;

    /** 订单ID */
    @Column(length = 20, nullable = false, columnDefinition="bigint(20) NOT NULL COMMENT '订单ID'")
    private Long orderId;

    /** 订单编号 */
    @Column(length = 25, nullable = false, columnDefinition="varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号'")
    private String orderNumber;

    /** 订单名称 */
    @Column(length = 64, nullable = false, columnDefinition="varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单名称'")
    private String orderName;

    /** 订单分类  */
    @Column(length = 10, nullable = false, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单分类 '")
    private String orderClassify;

    /** 状态   0：正常  1：已过期 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '状态   0：正常  1：已过期 '")
    @Indexed
    private Byte status;

    /** 接单人ID */
    @Column(length = 20, nullable = false, columnDefinition="bigint(20) NOT NULL COMMENT '接单人ID'")
    private Long userId;

    /** 接单编号 */
    @Column(length = 25, nullable = false, columnDefinition="varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接单编号'")
    private String apportionNumber;

    /** 备注 */
    @Column(columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注'")
    private String remarks;

}