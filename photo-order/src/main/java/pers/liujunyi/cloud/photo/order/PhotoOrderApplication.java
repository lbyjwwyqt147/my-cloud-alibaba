package pers.liujunyi.cloud.photo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import pers.liujunyi.cloud.common.configuration.MySQLUpperCaseStrategy;
import pers.liujunyi.cloud.common.encrypt.annotation.EnableEncrypt;
import pers.liujunyi.cloud.photo.order.util.SentinelExceptionHandler;

/**
 * @EnableDiscoveryClient 表明是一个Nacos客户端
 * @author ljy
 */
@EnableAsync
@EnableDiscoveryClient
@EnableEncrypt
@EnableJpaAuditing
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableFeignClients
@ComponentScan(basePackages = {"pers.liujunyi.cloud"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MySQLUpperCaseStrategy.class}))
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,  RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
public class PhotoOrderApplication {

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
     * blockHandler
     * 限流后处理的方法
     *
     * blockHandlerClass
     * 限流后处理的类
     *
     * fallback
     * 熔断后处理的方法
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
        //解决 WARN No Root logger was configured, creating default ERROR-level Root logger with Console appender
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(PhotoOrderApplication.class, args);
    }

}
