package pers.liujunyi.cloud.photo.datasource;

import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * 文件描述: 
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/4/27 10:24
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
public class TransactionConfiguration {


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
        return prop;
    }

}
