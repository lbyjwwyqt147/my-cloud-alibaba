package pers.liujunyi.cloud.centre;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import pers.liujunyi.cloud.centre.util.SentinelExceptionHandler;
import pers.liujunyi.cloud.common.configuration.MySQLUpperCaseStrategy;
import pers.liujunyi.cloud.common.encrypt.annotation.EnableEncrypt;

/***
 * exclude = DataSourceAutoConfiguration.class 解决 ：Consider defining a bean of type 'javax.sql.DataSource' in your configuration.
 * 开启增强代理 @EnableAspectJAutoProxy
 * 开启加解密自动配置 @EnableEncrypt
 * Spring boot 在加载配置顺序：本地配置文件 --> Config Server -->application
 * @EnableDiscoveryClient  表明是一个Nacos客户端，该注解是 SpringCloud 提供的原生注解。注册服务至Nacos。
 * @author
 */
@EnableDiscoveryClient
@EnableEncrypt
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"pers.liujunyi.cloud"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MySQLUpperCaseStrategy.class}))
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,  RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
public class CentreApplication {

    /**
     * 监听器：监听HTTP请求事件
     * 解决RequestContextHolder.getRequestAttributes()空指针问题
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    /**
     * SentinelResource 注解支持的配置Bean
     * @return
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


    /**
     * blockHandler
     * 限流后处理的方法
     *
     * blockHandlerClass
     * 限流后处理的类
     *
     * fallback
     * 熔断后处理的方法 用于在抛出异常的时候提供 fallback 处理逻辑
     *
     * fallbackClass
     * 熔断后处理的类
     *
     * @return RestTemplate
     */
    @Bean
    @SentinelRestTemplate(fallback = "fallback", fallbackClass = SentinelExceptionHandler.class, blockHandler = "handleException", blockHandlerClass = SentinelExceptionHandler.class)
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        // 解决 Factory method 'elasticsearchClient' threw exception; nested exception is java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
        //原因：程序的其他地方使用了Netty，这里指redis。这影响在实例化传输客户端之前初始化处理器的数量。 实例化传输客户端时，我们尝试初始化处理器的数量。 由于在其他地方使用Netty，因此已经初始化并且Netty会对此进行防范，因此首次实例化会因看到的非法状态异常而失败。
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        // 解决 WARN No Root logger was configured, creating default ERROR-level Root logger with Console appender
        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(CentreApplication.class, args);
    }

}
