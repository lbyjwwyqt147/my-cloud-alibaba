package pers.liujunyi.cloud.centre.api.service.user;

import pers.liujunyi.cloud.centre.api.domain.dto.UserDetailsInfoDto;

/***
 * 用户身份权限认证、
 *
 * @author ljy
 */
public interface CustomerAuthorityAuthenticationService {

    /**
     * 验证当前请求是否拥有访问权限
     * @param token  token值
     * @param requestUrl  请求地址
     * @return true:拥有权限  false:无权限
     */
    Boolean isAuthenticated(String token, String requestUrl);

    /**
     * 获取登录人ID
     * @param token
     * @return
     */
    Long getCurrentUserId(String token);

    /**
     * 获取登录人信息
     * @param token
     * @return
     */
    UserDetailsInfoDto getUserDetails(String token);
}
