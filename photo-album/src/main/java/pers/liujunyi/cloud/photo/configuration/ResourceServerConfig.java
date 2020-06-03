package pers.liujunyi.cloud.photo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.cors.CorsUtils;
import pers.liujunyi.cloud.common.configuration.IgnoreSecurityConfig;
import pers.liujunyi.cloud.common.util.SystemUtils;

import javax.servlet.http.HttpServletResponse;

/***
 * Resource服务配置
 * @author ljy
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /** 不需要权限认证的资源 */
    @Autowired
    private IgnoreSecurityConfig ignoreSecurityConfig;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;
    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(secret);
        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        return tokenService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId);
        resources.tokenServices(tokenService());
    }


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
