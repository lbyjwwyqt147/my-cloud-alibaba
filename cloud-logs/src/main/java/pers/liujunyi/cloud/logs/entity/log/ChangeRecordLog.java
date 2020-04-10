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

/***
 * 文件名称: ChangeRecordLog.java
 * 文件描述: 变更记录日志
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
@Document(indexName = "cloud_change_record_log", type = "changeRecordLog", shards = 1, replicas = 0)
@Table(appliesTo = "change_record_log", comment = "变更记录日志")
public class ChangeRecordLog extends BaseEntity {


    /** 操作日志id */
    @Column(length = 45, nullable = false, columnDefinition="varchar(45) NOT NULL COMMENT '操作日志id'")
    private String logId;

    /** 字段名称 */
    @Field(type = FieldType.Auto, index = false)
    @Column(length = 64, nullable = false, columnDefinition="varchar(64) NOT NULL COMMENT '字段名称'")
    private String fieldName;

    /** 字段描述 */
    @Field(type = FieldType.Auto, index = false)
    @Column(length = 255, nullable = false, columnDefinition="varchar(255) NOT NULL COMMENT '字段描述'")
    private String fieldDescription;

    /** 修改之前值 */
    @Field(type = FieldType.Auto, index = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition="text NOT NULL COMMENT '修改之前值'")
    private String beforeValue;

    /** 修改之后值 */
    @Field(type = FieldType.Auto, index = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition="text NOT NULL COMMENT '修改之后值'")
    private String afterValue;


}