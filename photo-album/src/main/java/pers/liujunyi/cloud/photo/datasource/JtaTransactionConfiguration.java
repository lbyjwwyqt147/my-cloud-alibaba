package pers.liujunyi.cloud.photo.datasource;

import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import pers.liujunyi.cloud.common.configuration.JtaTransactionImp;
import pers.liujunyi.cloud.common.util.BaseConstant;
import pers.liujunyi.cloud.common.util.MongoUtils;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.Properties;

/***
 * 文件名称: TransactionConfiguration
 * 文件描述: Mongo DB 事物和MYsql事物 一致性配置  目前遇到问题有时可以回滚有时不能 原因还在研究中，如果需要使用则放开@Configuration 并注释掉 JpaRepositoriesConfig中的事物配置
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/4/27 10:24
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
//@Configuration
public class JtaTransactionConfiguration {


    @Autowired
    private Environment env;


    /**
     * MongoDb 事物
     * @param factory
     * @return
     */
    @Bean(name = BaseConstant.MONGO_DB_MANAGER)
    public MongoTransactionManager mongoTransactionManager(MongoDbFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * MySql 数据源事物
     * @param dataSource
     * @param transactionManagerCustomizers
     * @return
     */
    public DataSourceTransactionManager transactionManager(DataSource dataSource, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }

    /**
     * mysql 数据源
     * mysql-connector-java 必须是8.0.11 如果版本高于8.0.11 则会报异常 java.lang.NoSuchMethodException: PropertySet.getBooleanReadableProperty(java.lang.String),
     * @return
     * @throws Exception
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws Exception {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("mysqlDataSource");
        ds.setPoolSize(5);
        ds.setXaProperties(build("spring.datasource."));
        return ds;
    }

    /**
     * JTA 事务管理
     * @param factory
     * @param mongoUtils
     * @return
     */
    @Bean(name = BaseConstant.TRANSACTION_MANAGER)
    @Primary
    @Autowired
    public PlatformTransactionManager transactionManager(MongoDbFactory factory, MongoUtils mongoUtils) {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new JtaTransactionImp(this.mongoTransactionManager(factory), mongoUtils);
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }


    /**
     * 构建mysql 连接属性
     * @param prefix
     * @return
     */
    private Properties build(String prefix) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name"));
        prop.put("initialSize", env.getProperty(prefix + "druid.initial-size"));
        prop.put("minIdle", env.getProperty(prefix + "druid.min-idle"));
        prop.put("maxActive", env.getProperty(prefix + "druid.max-active"));
        prop.put("maxWait", env.getProperty(prefix + "druid.max-wait"));
        prop.put("timeBetweenEvictionRunsMillis", env.getProperty(prefix + "druid.time-between-eviction-runs-millis"));
        prop.put("validationQuery", env.getProperty(prefix + "druid.validation-query"));
        prop.put("testWhileIdle", env.getProperty(prefix + "druid.test-while-idle"));
        prop.put("testOnBorrow", env.getProperty(prefix + "druid.test-on-borrow"));
        prop.put("testOnReturn", env.getProperty(prefix + "druid.test-on-return"));
        prop.put("filters", env.getProperty(prefix + "druid.filters"));
        return prop;
    }

}
