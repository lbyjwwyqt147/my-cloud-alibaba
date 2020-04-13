package pers.liujunyi.cloud.gateway.loadbanlance;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ljy
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
        // 轮询负载均衡规则  规则参考地址 https://blog.csdn.net/rickiyeat/article/details/64918756
        return new RoundRobinRule();
        // 权重负载均衡规则
        //return new NacosWeightedRule();
    }

}
