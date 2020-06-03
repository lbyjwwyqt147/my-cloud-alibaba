package pers.liujunyi.cloud.chat.domain.chitchat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.dto.BaseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/***
 * 文件名称: ChattingRecordsDto.java
 * 文件描述: 聊天记录
 * 公 司:
 * 内容摘要:
 *
 * 完成日期:2019年03月11日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class ChattingRecordsDto extends BaseDto {


    private static final long serialVersionUID = -2383661234232464558L;


    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称必须填写")
    private String nickName;

    /** 发送者ID */
    @ApiModelProperty(value = "发送者ID")
    @NotNull(message = "发送者必须填写")
    private Long sendUserId;

    /** 接收者ID */
    @ApiModelProperty(value = "接收者ID")
    @NotNull(message = "接收者必须填写")
    private Long receiveUserId;

    /** 内容 */
    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容必须填写")
    private String chatContent;
}
