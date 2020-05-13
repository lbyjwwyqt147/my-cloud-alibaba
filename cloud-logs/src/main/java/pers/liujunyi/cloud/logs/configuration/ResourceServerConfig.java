package pers.liujunyi.cloud.logs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;
import pers.liujunyi.cloud.common.configuration.IgnoreSecurityConfig;
import pers.liujunyi.cloud.common.util.SystemUtils;

import javax.servlet.http.HttpServletResponse;

/***
 * Resource服务配置
 * @EnableWebSecurity 优先级要高于  @EnableResourceServer
 * @author ljy
 */
@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /** 不需要权限认证的资源 */
    @Autowired
    private IgnoreSecurityConfig ignoreSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/api/**").authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 配置不需要权限认证的资源
                .antMatchers(SystemUtils.antMatchers(ignoreSecurityConfig.getAntMatchers())).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
                // 其他资源都需要保护
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .httpBasic();
    }
}
