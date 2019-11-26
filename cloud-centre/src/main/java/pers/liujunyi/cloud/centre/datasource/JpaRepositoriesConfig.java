package pers.liujunyi.cloud.centre.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;


/***
 * 文件名称: JpaRepositoriesConfig.java
 * 文件描述: 默认数据源 主库持久化配置
 * 公 司:
 * 内容摘要:
 * 其他说明:
 *            @EntityScan 扫描实体类位置
 *            @EnableTransactionManagement  开启注解事物   @EnableJpaRepositories 开启JPA存储库扫描
 *            EnableJpaRepositories 和 EnableMongoRepositories 不能在同一包下面
 *            @EntityScan 配置 实体类所在的路径  解决： Not a managed type:
 * 完成日期:2019年01月17日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
@EntityScan(basePackages = {"pers.liujunyi.cloud.centre.entity", "pers.liujunyi.cloud.security.entity"})
@EnableJpaRepositories(basePackages = {"pers.liujunyi.cloud.centre.repository.jpa", "pers.liujunyi.cloud.security.repository.jpa"})
@EnableMongoRepositories(basePackages = {"pers.liujunyi.cloud.centre.repository.mongo",  "pers.liujunyi.cloud.security.repository.mongo"})
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaRepositoriesConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;


    /**
     * 　DataSource　事物
     *
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * jpa 事物
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws SQLException {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
