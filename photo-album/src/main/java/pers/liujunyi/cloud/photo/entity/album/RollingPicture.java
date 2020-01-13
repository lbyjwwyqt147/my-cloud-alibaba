package pers.liujunyi.cloud.photo.entity.album;

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
 * 文件名称: RollingPicture.java
 * 文件描述: 页面滚动图片管理
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
@Document(collection = "rolling_picture")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "rolling_picture", comment = "页面滚动图片管理")
public class RollingPicture extends BaseEntity {
    private static final long serialVersionUID = 5388585049076603542L;

    /** 照片ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '照片ID'")
    private Long pictureId;

    /** 照片地址 */
    @Column(nullable = false, columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '照片地址'")
    private String pictureLocation;

    /** 状态 0：展示 1：不展示 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '状态 0：展示 1：不展示'")
    @Indexed
    private Byte status;

    /** 页面代码 例如：1001：首页 等 */
    @Column(nullable = false, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 页面代码 例如：1001：首页 等'")
    @Indexed
    private String pageCode;

    /** 页面中所在位置 */
    @Column(nullable = false, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 页面中所在位置'")
    @Indexed
    private String pagePosition;

    /** 优先级 数字从小到大排列 */
    @Column(columnDefinition="tinyint(4) DEFAULT '10' COMMENT '排序值'")
    private Byte priority;

    /** href */
    @Column(columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT ' 点击图片访问的 href'")
    private String hrefLink;

    /** 文件分类 0：图片 1：文档  2：视频  5：其他 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '文件分类 0：图片 1：文档  2：视频  5：其他'")
    private Byte pictureCategory;


    /** 业务ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '业务ID'")
    private Long businessId;
}