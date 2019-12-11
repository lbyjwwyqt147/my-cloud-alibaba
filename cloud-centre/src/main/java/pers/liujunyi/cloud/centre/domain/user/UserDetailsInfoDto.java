package pers.liujunyi.cloud.centre.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import pers.liujunyi.cloud.common.dto.BaseDto;
import pers.liujunyi.cloud.common.util.RegexpUtils;

import javax.validation.constraints.*;
import java.util.Date;

/***
 * 文件名称: UserDetailsInfoDto.java
 * 文件描述: 用户详细档案 dto
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
public class UserDetailsInfoDto extends BaseDto {

    private static final long serialVersionUID = -3568694500607340797L;
    /** 用户编号 */
    @ApiModelProperty(value = "用户编号")
    @NotBlank(message = "编号 必须填写")
    @Length(min = 1, max = 20, message = "编号 最多可以输入20个字符")
    @Pattern(regexp = RegexpUtils.CODE_REGEXP, message = "编号 " + RegexpUtils.CODE_MSG)
    private String userNumber;

    /** 用户帐号 */
    private Long userAccountsId;

    /** 用户真实姓名 */
    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "真实姓名 必须填写")
    @Length(min = 1, max = 32, message = "真实姓名 最多可以输入32个字符")
    @Pattern(regexp = RegexpUtils.NAME_REGEXP, message = "真实姓名 " + RegexpUtils.NAME_MSG)
    private String userFullName;

    /** 用户昵称 */
    @ApiModelProperty(value = "昵称")
    @Length(min = 0, max = 32, message = "昵称 最多可以输入32个字符")
    @Pattern(regexp = RegexpUtils.NAME_REGEXP, message = "昵称 " + RegexpUtils.NAME_MSG)
    private String userNickName;

    /** 性别 0:男  1:女 */
    @ApiModelProperty(value = "性别")
    @NotNull(message = "性别 必须选择")
    @Min(value = 0, message = "性别 值必须大于0")
    @Max(value = 127, message = "性别 最大值不能大于127")
    private Byte userSex;

    /** 绑定的手机号 */
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号 必须填写")
    @Length(min = 1, max = 11, message = "手机号 位数为11位")
    @Pattern(regexp = RegexpUtils.MOBILE_PHONE_REGEXP, message = RegexpUtils.MOBILE_PHONE_MSG)
    private String mobilePhone;

    /** 用户类别   0：超级管理员 1：普通管理员 2：内部职工 3：普通用户   */
    @ApiModelProperty(value = "用户类别")
    @NotNull(message = "用户类别 必须选择")
    @Min(value = 0, message = "类别 值必须大于0")
    @Max(value = 127, message = "类别 最大值不能大于127")
    private Byte userCategory;

    /** 用户职位 */
    @ApiModelProperty(value = "职位")
    @NotNull(message = "职位 必须选择")
    private Long userPosition;

    /** 所在省份 */
    @ApiModelProperty(value = "所在省份")
    private Long province;

    /** 所在城市 */
    @ApiModelProperty(value = "所在城市")
    private Long city;

    /** 所在行政区 */
    @ApiModelProperty(value = "所在行政区")
    private Long district;

    /** 所在行政区街道 */
    @ApiModelProperty(value = "所在行政区街道")
    @Length(min = 0, max = 50, message = "街道 最多可以输入50个字符")
    @Pattern(regexp = RegexpUtils.NAME_REGEXP, message = "街道 " + RegexpUtils.NAME_MSG)
    private String street;

    /**  身份证号  */
    @ApiModelProperty(value = "身份证号")
    @Pattern(regexp = RegexpUtils.IDENTIFICATIONCARD_REGEXP, message =  RegexpUtils.IDENTIFICATIONCARD_MSG)
    private String userIdentiyCard;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期")
    @NotNull(message = "入职日期 必须填写")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date entryDate;

    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱")
    @Email(message = "电子邮箱格式错误")
    @Length(min = 0, max = 65, message = "电子邮箱 最多可以输入65个字符")
    private String userEmail;

    /** 生日 */
    @ApiModelProperty(value = "出生日期")
    @NotNull(message = "出生日期 必须填写")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    /** 年龄 */
    @ApiModelProperty(value = "年龄")
    @Min(value = 10, message = "年龄 最小为10岁")
    @Max(value = 127, message = "年龄 不能大于127 岁")
    private Byte userAge;
    
    /** 个人介绍 */
    @ApiModelProperty(value = "个人介绍")
    @Length(min = 0, max = 500, message = "个人简介 最多可以输入500个字符")
    private String userIntro;

    /** 状态：0：正常  1：冻结  2：离职 */
    @ApiModelProperty(value = "状态")
    @Min(value = 0, message = "状态 最小为0")
    @Max(value = 127, message = "状态 不能大于127")
    private Byte userStatus;

    /** 头像 */
    private String userPortrait;

    /** 头像id  */
    private Long userPortraitId;

    /** 试用期 状态 1：试用期职工   2：正式职工 */
    private String probationStatus;

    /** 在职年限 (月) */
    private Integer workingAge;

    /** 在职年限 年月日 格式 */
    private String  workingYears;

    /** 离职日期 */
    private Date dimissionDate;

    private Long userId;

    /** 部门父id */
    private String orgParentId;
    /** 部门id */
    private Long orgId;
    /** 部门编号 */
    private String orgNumber;

}
