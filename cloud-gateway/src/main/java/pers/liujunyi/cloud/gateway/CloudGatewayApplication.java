package pers.liujunyi.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"pers.liujunyi.cloud"})
public class CloudGatewayApplication {

    public static void main(String[] args) {
        //解决 WARN No Root logger was configured, creating default ERROR-level Root logger with Console appender
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

}
