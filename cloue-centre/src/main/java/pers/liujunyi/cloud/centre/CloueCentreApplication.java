package pers.liujunyi.cloud.centre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient 开启Spring Cloud的服务注册与发现
 *
 * @author ljy
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloueCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloueCentreApplication.class, args);
    }

}
