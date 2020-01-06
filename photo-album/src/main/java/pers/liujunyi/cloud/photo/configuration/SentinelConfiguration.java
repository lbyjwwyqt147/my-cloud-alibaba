package pers.liujunyi.cloud.photo.configuration;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.sentinel.SentinelProperties;
import org.springframework.cloud.alibaba.sentinel.datasource.config.NacosDataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/***
 * 熔断规则
 * @author ljy
 */
@Slf4j
@Configuration
public class SentinelConfiguration {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelConfiguration init() throws Exception {
        log.info("[NacosSource初始化,从Nacos中获取熔断规则]");
        sentinelProperties.getDatasource().entrySet().stream().filter(map -> {
            return map.getValue().getNacos() != null;
        }).forEach(map -> {
            NacosDataSourceProperties nacos = map.getValue().getNacos();
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(nacos.getServerAddr(),
                    nacos.getGroupId(), nacos.getDataId(),
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                    }));
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        });
        return new SentinelConfiguration();
    }

}
