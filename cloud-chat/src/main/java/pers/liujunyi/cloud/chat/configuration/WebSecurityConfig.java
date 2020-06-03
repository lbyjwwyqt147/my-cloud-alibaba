package pers.liujunyi.cloud.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pers.liujunyi.cloud.common.configuration.IgnoreSecurityConfig;
import pers.liujunyi.cloud.common.util.SystemUtils;

/**
 * @EnableWebSecurity 优先级要高于  @EnableResourceServer
 * @author ljy
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(2)
@Deprecated
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 不需要权限认证的资源 */
    @Autowired
    private IgnoreSecurityConfig ignoreSecurityConfig;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SystemUtils.antMatchers(ignoreSecurityConfig.getAntMatchers()));
    }

    /**
     * 禁止csrf
     * @param http
     * @throws Exception
     */
    @Override
    public  void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().csrf().disable();
    }


}
