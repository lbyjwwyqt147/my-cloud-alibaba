package pers.liujunyi.cloud.centre.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 * 文件名称: UserDetailsInfoVo.java
 * 文件描述: 用户详细档案 vo
 * 公 司:
 * 内容摘要:
 *
 * 完成日期:2019年03月22日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailsInfoVo extends UserDetailsInfoDto {

    private static final long serialVersionUID = 562898741873474891L;

    @ApiModelProperty(value = "职位")
    private String userPositionText;

    /** 所在行政区街道 */
    @ApiModelProperty(value = "所在行政区街道")
    private String street;
    @ApiModelProperty(value = "地址")
    private String addressText;


    /** 所属组织机构名称 */
    @ApiModelProperty(value = "组织机构")
    private String staffOrgName;


}
