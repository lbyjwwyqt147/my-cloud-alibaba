package pers.liujunyi.cloud.gateway.loadbanlance;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/***
 * 文件名称: RibbonClientConfig
 * 文件描述: 
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/4/13 16:46
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class RibbonClientConfig {
}
