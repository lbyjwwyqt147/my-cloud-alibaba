package pers.liujunyi.cloud.centre.service.user;


import pers.liujunyi.cloud.centre.api.domain.dto.UserDetailsInfoDto;

/***
 * 文件名称: UserAuthService.java
 * 文件描述:  用户权限 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface UserAuthService {

    /**
     * 验证当前请求是否拥有访问权限
     * @param token  token值
     * @param requestUrl  请求地址
     * @return true:拥有权限  false:无权限
     */
    Boolean isAuthenticated(String token, String requestUrl);

    /**
     * 获取登录人信息
     * @param token
     * @return
     */
    UserDetailsInfoDto getUserDetails(String token);
}
