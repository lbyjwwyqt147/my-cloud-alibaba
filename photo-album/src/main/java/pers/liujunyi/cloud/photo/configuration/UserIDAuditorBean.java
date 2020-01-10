package pers.liujunyi.cloud.photo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.liujunyi.cloud.common.util.UserContext;

import java.util.Optional;

/***
 * AuditorAware　设置当前用户ＩＤ
 * @author ljy
 */
@Configuration
public class UserIDAuditorBean implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long currentUserId = UserContext.currentUserId();
        if (currentUserId == null) {
            SecurityContext sc = SecurityContextHolder.getContext();
            Authentication auth = sc.getAuthentication();
            //UserDetails user = (UserDetails) auth.getPrincipal();
            currentUserId = 1L;
        }
        return Optional.ofNullable(currentUserId);
    }
}
