package pers.liujunyi.cloud.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 *
 * @author ljy
 */
//@Configuration
@EnableWebFluxSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig  {

    /**
     * 禁止csrf
     * @param http
     * @throws Exception
     */
    protected void configure(ServerHttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.csrf().disable();

        return http.build();
    }

}
