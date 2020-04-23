package pers.liujunyi.cloud.centre.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;
import org.springframework.data.mongodb.core.mapping.Document;
import pers.liujunyi.cloud.common.annotation.CustomerField;
import pers.liujunyi.cloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/***
 * 文件名称: UserDetailsInfo.java
 * 文件描述: 用户详细档案信息
 * 公 司:
 * 内容摘要:
 *
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Data
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user_details_info")
@DynamicInsert
@DynamicUpdate
@Table(appliesTo = "user_details_info", comment = "用户详细档案表")
public class UserDetailsInfo extends BaseEntity {

    /** 用户编号 */
    @CustomerField(desc = "编号")
    @Column(length = 20, nullable = false, columnDefinition="varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号'")
    private String userNumber;

    /** 关联帐号ID */
    @CustomerField(desc = "关联帐号ID")
    @Column(nullable = false, columnDefinition=" bigint(20) NOT NULL COMMENT '关联帐号ID'")
    private Long userAccountsId;

    /** 用户真实姓名 */
    @CustomerField(desc = "真实姓名")
    @Column(length = 32, columnDefinition=" varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户真实姓名'")
    private String userFullName;

    /** 用户昵称 */
    @CustomerField(desc = "昵称")
    @Column(length = 32, columnDefinition="varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户昵称'")
    private String userNickName;

    /** 性别 0:男  1:女 */
    @CustomerField(desc = "性别 0:男  1:女")
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '性别 0:男  1:女'")
    private Byte userSex;

    /** 手机号 */
    @CustomerField(desc = "手机号")
    @Column(length = 11, columnDefinition=" char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号'")
    private String mobilePhone;

    /** 入职日期 */
    @CustomerField(desc = "入职日期")
    @Column(columnDefinition="date DEFAULT NULL COMMENT '入职日期'")
    private Date entryDate;

    /** 用户类别   0：超级管理员 1：普通管理员 2：内部职工 3：普通用户   */
    @CustomerField(desc = "用户类别 ")
    @Column(columnDefinition=" tinyint(4) DEFAULT '2' COMMENT '用户类别   0：超级管理员 1：普通管理员 2：内部职工 3：普通用户 '")
    private Byte userCategory;


    /** 所在省份 */
    @CustomerField(desc = "所在省份")
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '所在省份'")
    private Long province;

    /** 所在城市 */
    @CustomerField(desc = "所在城市")
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '所在城市'")
    private Long city;

    /** 所在行政区 */
    @CustomerField(desc = "所在行政区")
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '所在行政区'")
    private Long district;

    /**  身份证号  */
    @CustomerField(desc = "身份证号")
    @Column(length = 20, columnDefinition="varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号'")
    private String userIdentiyCard;

    /** 所在行政区街道 */
    @CustomerField(desc = "所在行政区街道")
    @Column(length = 50, columnDefinition="varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所在行政区街道'")
    private String street;

    /** 电子邮箱 */
    @CustomerField(desc = "电子邮箱")
    @Column(length = 65, columnDefinition="varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱'")
    private String userEmail;

    /** 出生日期 */
    @CustomerField(desc = "出生日期")
    @Column(columnDefinition="date DEFAULT NULL COMMENT '出生日期'")
    private Date birthday;

    /** 年龄 */
    @CustomerField(desc = "年龄")
    @Column(columnDefinition=" tinyint(4) DEFAULT NULL COMMENT '年龄'")
    private Byte userAge;

    /** 个人介绍 */
    @Column(length = 500, columnDefinition="varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '个人介绍'")
    private String userIntro;

    /** 状态：0：正常  1：冻结  2：离职 */
    @CustomerField(desc = "状态：0：正常  1：冻结  2：离职")
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '状态：0：正常  1：冻结  2：离职'")
    private Byte userStatus;

    /** 头像 */
    @Column(columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像'")
    private String userPortrait;

    /** 头像id  */
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '头像id'")
    private Long userPortraitId;


    /** 试用期 状态 1：试用期职工   2：正式职工 */
    @CustomerField(desc = "试用期 状态 ")
    @Column(length = 10, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '试用期 状态 1：试用期职工   2：正式职工 '")
    private String probationStatus;

    /** 在职年限 (月) */
    @Column(columnDefinition="int(11) DEFAULT NULL COMMENT '在职年限 (月)'")
    private Integer workingAge;

    /** 在职年限 年月日 格式 */
    @Column(length = 10, columnDefinition="varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '在职年限 年月日 格式'")
    private String  workingYears;

    /** 职位  */
    @CustomerField(desc = "职位")
    @Column(columnDefinition=" bigint(20) DEFAULT NULL COMMENT '职位'")
    private Long userPosition;

}