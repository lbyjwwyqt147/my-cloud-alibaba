package pers.liujunyi.cloud.seata.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.liujunyi.cloud.seata.interceptor.SeataFeignClientInterceptor;

/***
 * 文件名称: CustomerInterceptorConfig
 * 文件描述: 
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/5/11 14:23
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
public class CustomerInterceptorConfig  {

    /**
     * 将SeataFeignClientInterceptor 加入IoC的容器中
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new SeataFeignClientInterceptor();
    }

}
