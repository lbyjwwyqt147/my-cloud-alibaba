package pers.liujunyi.cloud.chat.entity.chitchat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;
import pers.liujunyi.cloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/***
 * 文件名称: ChattingRecords.java
 * 文件描述: 聊天纪录
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
@Table(appliesTo = "chatting_records", comment = "聊天记录")
public class ChattingRecords extends BaseEntity {


    private static final long serialVersionUID = -6805794521290515821L;


    /** 昵称 */
    @Column(length = 32, nullable = false, columnDefinition="varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称'")
    private String nickName;

    /** 发送者ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '发送者ID'")
    private Long sendUserId;

    /** 接收者ID */
    @Column(columnDefinition=" bigint(20) NOT NULL COMMENT '接收者ID'")
    private Long receiveUserId;

    /** 内容 */
    @Column(nullable = false, columnDefinition="varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容'")
    private String chatContent;

}