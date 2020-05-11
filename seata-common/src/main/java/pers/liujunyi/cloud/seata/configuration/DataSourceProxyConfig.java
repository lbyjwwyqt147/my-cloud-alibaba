package pers.liujunyi.cloud.seata.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/***
 * 文件名称: DataSourceProxyConfig
 * 文件描述: 因为使用了依赖包 seata-spring-boot-starter 会自动去代理数据源，和全局事物扫描，所以这里的手动代理数据源配置就不需要了
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/5/6 14:19
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Deprecated
//@Configuration
public class DataSourceProxyConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }


}
