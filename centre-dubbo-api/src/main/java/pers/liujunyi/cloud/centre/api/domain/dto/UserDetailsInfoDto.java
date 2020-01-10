package pers.liujunyi.cloud.centre.api.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户详情信息
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UserDetailsInfoDto implements Serializable {

    private static final long serialVersionUID = -2543033673944476698L;
    /** 用户id  */
    private Long userId;
    /** 用户帐号 */
    private String userAccounts;
    /** 用户编号  */
    private String userNumber;
    /** 用户密码 */
    private String userPassword;
    /** 绑定的手机号 */
    private String mobilePhone;
    /** 状态：0：正常  1：冻结 */
    private Byte userStatus;
    /** 用户类别   0：超级管理员 1：普通管理员  2：员工  3：顾客 */
    private Byte userCategory;
    /** 真实姓名 */
    private String userName;
    /** 昵称 */
    private String userNickName;
    /** 头像 */
    private String portrait;
    /** 租户ID */
    private Long lessee;
    /** 所属机构ID */
    private Long orgId;
    /** 所属机构名称 */
    private String orgName;
    /** token */
    private String token;
    /** 人员权限信息 */
    private String authorities;
    /** flase 未认证   true 认证通过 */
    private Boolean authenticated;
    private Object credentials;
    private Object details;
    private Object principal;
    private String secret;

}
