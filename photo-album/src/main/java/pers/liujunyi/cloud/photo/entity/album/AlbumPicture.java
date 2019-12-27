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
 * 文件名称: AlbumPicture.java
 * 文件描述: 相册对应的图片信息
 * 公 司:
 * 内容摘要:
 *
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
@Document(collection = "album_picture")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "album_picture", comment = "相册对应的图片信息")
public class AlbumPicture extends BaseEntity {

    private static final long serialVersionUID = 6528758581332889489L;

    /** 相册ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '相册ID'")
    @Indexed
    private Long albumId;

    /** 照片ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '照片ID'")
    private Long pictureId;

    /** 照片地址 */
    @Column(nullable = false, columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '照片地址'")
    private String pictureLocation;

    /** 照片名称 */
    @Column(length = 45, nullable = false, columnDefinition="varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '照片地址'")
    private String pictureName;

    /** 照片说明 */
    @Column(length = 50,  columnDefinition="varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '照片说明'")
    private String description;

    /** 排序值 */
    @Column(columnDefinition="tinyint(4) DEFAULT '10' COMMENT '排序值'")
    private Byte picturePriority;

    /** 文件分类 0：图片 1：文档  2：视频  5：其他 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '文件分类 0：图片 1：文档  2：视频  5：其他'")
    private Byte pictureCategory;

    /** 文件大小 */
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '文件大小'")
    private Long pictureSize;

    /** 文件类型 */
    @Column(length = 6,  columnDefinition="varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件类型'")
    private String pictureType;


}