package pers.liujunyi.cloud.photo.distribute.datasource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


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
@EntityScan(basePackages = {"pers.liujunyi.cloud.photo.distribute.entity"})
@EnableJpaRepositories(basePackages = {"pers.liujunyi.cloud.photo.distribute.repository.jpa"})
@EnableMongoRepositories(basePackages = {"pers.liujunyi.cloud.photo.distribute.repository.mongo"})
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaRepositoriesConfig {

   /* @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;*/

    /**
     * mongoDb 事物
     * 使用MongoDB 事物首先要配置好副本集,并且提前先创建好集合
     * 如果没有配置副本集就使用事物 则报错"Sessions are not supported by the MongoDB cluster to which this client is connected"
     * 如果没有先创建好集合使用事物 则报错"Cannot create namespace 数据库名.集合名称 in multi-document transaction."
     * @param factory
     * @return
     */
  /*  @Bean(name = BaseConstant.MONGO_DB_MANAGER)
    public MongoTransactionManager mongoTransactionManager(MongoDbFactory factory){
        return new MongoTransactionManager(factory);
    }*/

    /**
     * jpa 事物
     *
     * @param entityManagerFactory
     * @return
     */
  /*  @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws SQLException {
        return new JpaTransactionManager(entityManagerFactory);
    }
*/

}
