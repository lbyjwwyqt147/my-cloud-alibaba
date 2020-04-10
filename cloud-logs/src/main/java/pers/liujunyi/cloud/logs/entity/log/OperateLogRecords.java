package pers.liujunyi.cloud.logs.entity.log;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import pers.liujunyi.cloud.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/***
 * 文件名称: OperateLogRecords.java
 * 文件描述: 操作日志记录
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
@DynamicInsert
@DynamicUpdate
@Document(indexName = "cloud_operate_log_records", type = "operateLogRecords", shards = 1, replicas = 0)
@Table(appliesTo = "operate_log_records", comment = "操作日志记录")
public class OperateLogRecords extends BaseEntity {


    /** 操作人id */
    @Column(length = 20, nullable = false, columnDefinition="bigint(20)  NOT NULL COMMENT '用户id'")
    private Long operateUserId;

    /** 操作人编号 */
    @Field(type = FieldType.Auto, index = false)
    @Column(length = 20, nullable = false, columnDefinition="varchar(20)  NOT NULL COMMENT '用户编号'")
    private String operateUserNumber;

    /** 操作人名称 */
    @Column(length = 45,  columnDefinition="varchar(45)  DEFAULT NULL COMMENT '操作人名称'")
    private String operateUserName;

    /** 用户类别   0：超级管理员 1：普通管理员 2：内部职工 3：普通用户   */
    @Column(columnDefinition=" tinyint(4) DEFAULT '2' COMMENT '用户类别   0：超级管理员 1：普通管理员 2：内部职工 3：普通用户 '")
    private Byte operateUserType;

    /** 操作模块 */
    @Column(length = 32, nullable = false, columnDefinition="varchar(32)  NOT NULL COMMENT '操作模块'")
    private String operateModule;

    /** 应用名称 */
    @Column(length = 32, nullable = false, columnDefinition="varchar(32)  NOT NULL COMMENT '应用名称'")
    private String applicationName;

    /** 操作类型 */
    @Column(columnDefinition="tinyint(4)  COMMENT '操作类型 1:新增  2:修改 3: 删除 '")
    private Byte operateType;

    /** 日志类型 */
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '日志类型 0:正常请求日志  1:异常日志 3: 登录日志  4：登出日志 '")
    private Byte logType;

    /** 操作的表名 */
    @Column(length = 150, nullable = false, columnDefinition="varchar(150)  NOT NULL COMMENT '操作的表名'")
    private String tableName;

    /** 操作方法名 */
    @Column(length = 255, nullable = false, columnDefinition="varchar(255)  NOT NULL COMMENT '操作方法名'")
    private String operateMethod;

    /** 说明 */
    @Field(type = FieldType.Auto, index = false)
    @Column(length = 128, nullable = false, columnDefinition="varchar(128)  NOT NULL COMMENT '说明'")
    private String description;

    /** 参数 */
    @Field(type = FieldType.Auto, index = false)
    @Column(length = 128, nullable = false, columnDefinition="varchar(128)  NOT NULL COMMENT '参数'")
    private String parameters;

    /** 响应执行时间 */
    @Column( columnDefinition = " timestamp DEFAULT NULL COMMENT '响应执行时间'")
    private Date responseStartTime;

    /** 响应结束时间 */
    @Column( columnDefinition = " timestamp DEFAULT NULL COMMENT '响应结束时间'")
    private Date responseEndTime;

    /** 消耗时间(ms) */
    @Column(length = 20, nullable = false, columnDefinition="bigint(20) NOT NULL COMMENT '消耗总时'")
    private Long expendTime;

    /** 异常信息 */
    @Field(type = FieldType.Auto, index = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition="text  NOT NULL COMMENT '异常信息'")
    private String errorMessage;

    /** 操作结果 */
    @Field(type = FieldType.Auto, index = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition="text  NOT NULL COMMENT '操作结果'")
    private String resultMessage;

    /** 操作状态 */
    @Field(type = FieldType.Auto, index = false)
    @Column(columnDefinition="tinyint(4) DEFAULT '0' COMMENT '操作状态 0:成功  1:失败  '")
    private Byte operateStatus;

}