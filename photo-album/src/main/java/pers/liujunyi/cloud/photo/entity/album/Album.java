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
import java.util.Date;

/***
 * 文件名称: Album.java
 * 文件描述: 相册信息
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
@Document(collection = "album")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "album", comment = "相册信息")
public class Album extends BaseEntity {

    private static final long serialVersionUID = 6100736012740286478L;

    /** 相册编号 */
    @Column(length = 20, nullable = false, columnDefinition="varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '相册编号'")
    private String albumNumber;

    /** 相册名称 */
    @Column(length = 32, nullable = false, columnDefinition="varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '相册名称'")
    private String albumName;

    /** 相册分类 例如：旅拍、活动 等 */
    @Column(length = 10, nullable = false, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '相册分类 例如：旅拍、活动 等'")
    private String albumClassify;

    /** 相册状态  0：已发布（可见）  1：不可见  2：草稿 */
    @Column(columnDefinition="tinyint(4) DEFAULT '2' COMMENT '相册状态 0：已发布（可见）  1：不可见  2：草稿'")
    @Indexed
    private Byte albumStatus;

    /** 背景音乐地址 */
    @Column( columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '背景音乐地址'")
    private String albumMusicAddress;

    /** 版权设置 */
    @Column(columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版权设置'")
    private String albumCopyright;

    /** 排序值 */
    @Column(columnDefinition="tinyint(4) DEFAULT '10' COMMENT '排序值'")
    private Byte albumPriority;

    /** 拍摄地点 */
    @Column(length = 32, nullable = false, columnDefinition="varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拍摄地点'")
    private String spotForPhotography;

    /** 相册描述 */
    @Column(columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相册描述'")
    private String albumDescription;

    /** 拍摄时间 */
    @Column(columnDefinition="date DEFAULT NULL COMMENT '拍摄时间'")
    private Date shootingsDate;

    /** 作者(摄影师)  */
    @Column(length = 32, columnDefinition="varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '作者(摄影师)'")
    private String albumPhotographyAuthor;


    /** 封面图地址 */
    @Column(nullable = false, columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图地址'")
    private String surfacePlot;

    /** 封面图ID */
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '封面图ID'")
    private Long surfacePlotId;

}