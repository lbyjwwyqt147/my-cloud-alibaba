package pers.liujunyi.cloud.centre.datasource;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.sentinel.SentinelProperties;
import org.springframework.cloud.alibaba.sentinel.datasource.config.NacosDataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/***
 * 文件名称: SentinelNacosDataSourceConfig
 * 文件描述: Sentinel 规则持久化
 * 公 司:
 * 内容摘要: Sentinel控制台不再是调用客户端的API推送规则数据，而是将规则推送到Nacos或其他远程配置中心
 *          Sentinel客户端通过连接Nacos，来获取规则配置；并监听Nacos配置变化，如发生变化，就更新本地缓存（从而让本地缓存总是和Nacos一致）
 *          Sentinel控制台也监听Nacos配置变化，如发生变化就更新本地缓存（从而让Sentinel控制台的本地缓存总是和Nacos一致）
 * 其他说明:
 * 完成日期:2020/4/9 10:44
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
public class SentinelNacosDataSourceConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelNacosDataSourceConfig init() throws Exception {

        // NacosSource初始化,从Nacos中获取熔断规则
        sentinelProperties.getDatasource().entrySet().stream().filter(map -> {
            return map.getValue().getNacos() != null;
        }).forEach(map -> {
            NacosDataSourceProperties nacos = map.getValue().getNacos();
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(nacos.getServerAddr(),
                    nacos.getGroupId(), nacos.getDataId(), source -> JSON.parseObject(source,
                    new TypeReference<List<FlowRule>>() {}));
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        });
        return new SentinelNacosDataSourceConfig();
    }

}
