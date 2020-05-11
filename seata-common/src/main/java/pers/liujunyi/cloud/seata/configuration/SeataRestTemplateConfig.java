package pers.liujunyi.cloud.seata.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import pers.liujunyi.cloud.seata.interceptor.SeataRestTemplateInterceptor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/***
 * 文件名称: SeataRestTemplateConfig
 * 文件描述: 
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/5/8 10:50
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
public class SeataRestTemplateConfig {

    @Autowired(
            required = false
    )
    private Collection<RestTemplate> restTemplates;

    public SeataRestTemplateConfig(Collection<RestTemplate> restTemplates) {
       this.restTemplates = restTemplates;
    }

    public SeataRestTemplateConfig() {
    }

    public SeataRestTemplateInterceptor seataRestTemplateInterceptor() {
        return new SeataRestTemplateInterceptor();
    }

    @PostConstruct
    public void init() {
        if (this.restTemplates != null && this.restTemplates.size() > 0) {
            Iterator var1 = this.restTemplates.iterator();
            while (var1.hasNext()) {
                RestTemplate restTemplate = (RestTemplate) var1.next();
                List<ClientHttpRequestInterceptor> interceptors = new ArrayList(restTemplate.getInterceptors());
                interceptors.add(this.seataRestTemplateInterceptor());
                restTemplate.setInterceptors(interceptors);
            }
        }
    }
}
