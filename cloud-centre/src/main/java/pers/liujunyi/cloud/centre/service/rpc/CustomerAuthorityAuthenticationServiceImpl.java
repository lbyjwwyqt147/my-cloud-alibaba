package pers.liujunyi.cloud.centre.service.rpc;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.liujunyi.cloud.centre.api.domain.dto.UserDetailsInfoDto;
import pers.liujunyi.cloud.centre.api.service.user.CustomerAuthorityAuthenticationService;
import pers.liujunyi.cloud.centre.service.user.UserAuthService;

/***
 * 文件名称: CustomerAuthorityAuthenticationServiceImpl.java
 * 文件描述: 用户身份权限认证 RPC Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明: @Service 包为 org.apache.dubbo.config.annotation.Service
 * 完成日期:2019年12月31日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
@Service(timeout = 10000)
public class CustomerAuthorityAuthenticationServiceImpl implements CustomerAuthorityAuthenticationService {

    @Autowired
    private UserAuthService userAuthService;


    @Override
    public Boolean isAuthenticated(String token, String requestUrl) {
        return userAuthService.isAuthenticated(token, requestUrl);
    }

    @Override
    public Long getCurrentUserId(String token) {
        UserDetailsInfoDto currentUser = this.getUserDetails(token);
        if (currentUser != null) {
            return currentUser.getUserId();
        }
        return 1L;
    }

    @Override
    public UserDetailsInfoDto getUserDetails(String token) {
        return userAuthService.getUserDetails(token);
    }

}
