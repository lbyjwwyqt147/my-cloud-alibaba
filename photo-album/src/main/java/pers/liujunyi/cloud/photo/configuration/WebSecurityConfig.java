package pers.liujunyi.cloud.photo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author ljy
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 禁止csrf
     * @param http
     * @throws Exception
     */
    @Override
    public  void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }


}
