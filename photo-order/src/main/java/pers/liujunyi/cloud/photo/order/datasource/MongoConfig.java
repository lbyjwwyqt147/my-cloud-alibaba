package pers.liujunyi.cloud.photo.order.datasource;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.common.util.AnnotationUtil;
import pers.liujunyi.cloud.common.util.ClassUtil;
import pers.liujunyi.cloud.common.util.CollectionUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/***
 * Mongo 配置
 * @author ljy
 */
@Log4j2
@Component
public class MongoConfig {

    @Resource
    protected MongoTemplate mongoTemplate;

    /**
     * 项目启动后自动创建MongoDb 集合
     */
    @PostConstruct
    public void createCollection() {
        // 获取实体类所在的包位置
        List<Class<?>> entityScan = new ArrayList<>();
        entityScan.add(JpaRepositoriesConfig.class);
        Set<String> entityScanBasePackages = AnnotationUtil.getEntityScanBasePackages(entityScan);
        // 获取包下所有实体类
        List<Class<?>> entityClassList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(entityScanBasePackages)) {
            for (String pack : entityScanBasePackages) {
                List<Class<?>> entityClass = ClassUtil.getClasses(pack);
                entityClassList.addAll(entityClass);
            }
        }
        // 获取指定的MongoDb 集合名称
        if (!CollectionUtils.isEmpty(entityClassList)) {
            log.info(" >>>>>>>>>>>> 开始检测MongoDb库中是否存在指定的集合..");
            Set<String> entityCollectionNames = AnnotationUtil.getMongoDocumentCollection(entityClassList);
            if (!CollectionUtils.isEmpty(entityCollectionNames)) {
                //获取MongoDb库中中的所有集合名称
                Set<String>  mongoCollectionNames = this.mongoTemplate.getCollectionNames();
                List<String> collectionNames = (List<String>) CollectionUtil.getDiffent(entityCollectionNames, mongoCollectionNames);
                for (String collectionName : collectionNames) {
                    this.mongoTemplate.createCollection(collectionName);
                }
                int collectionSize = collectionNames.size();
                if (collectionSize > 0) {
                    log.info(" >>>>>>>>>>>> 自动创建MongoDb库中的集合数：" + collectionNames.size());
                } else {
                    log.info(" >>>>>>>>>>>> MongoDb库中的集合已经全部存在,忽略创建" );
                }
            }
        }
    }
}
