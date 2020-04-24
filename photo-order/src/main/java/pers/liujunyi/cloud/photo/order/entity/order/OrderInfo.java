package pers.liujunyi.cloud.photo.order.entity.order;

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
 * 文件名称: OrderInfo.java
 * 文件描述: 订单信息
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
@Document(collection = "order_info")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "order_info", comment = "订单信息")
public class OrderInfo extends BaseEntity {


    private static final long serialVersionUID = 7867810618254028512L;

    /** 订单编号 */
    @Column(length = 25, nullable = false, columnDefinition="varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号'")
    private String orderNumber;

    /** 订单名称 */
    @Column(length = 64, nullable = false, columnDefinition="varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单名称'")
    private String orderName;

    /** 订单分类  */
    @Column(length = 10, nullable = false, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单分类 '")
    private String orderClassify;

    /** 订单状态   0：待支付  1：已取消  2：已支付 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '订单状态   0：待支付  1：已取消  2：已支付'")
    @Indexed
    private Byte orderStatus;

    /** 订单备注 */
    @Column(columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单备注'")
    private String orderRemarks;

}