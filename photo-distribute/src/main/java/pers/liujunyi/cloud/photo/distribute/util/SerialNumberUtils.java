package pers.liujunyi.cloud.photo.distribute.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.liujunyi.cloud.common.redis.RedisTemplateUtils;

/***
 *
 * 编号 工具类
 * @author Administrator
 */
@Log4j2
@Component
public class SerialNumberUtils {

    @Autowired
    private  RedisTemplateUtils redisTemplateUtils;


}
