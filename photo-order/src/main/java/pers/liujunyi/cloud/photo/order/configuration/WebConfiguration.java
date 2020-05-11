package pers.liujunyi.cloud.photo.order.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pers.liujunyi.cloud.common.configuration.CustomRequestMappingHandlerMapping;
import pers.liujunyi.cloud.common.configuration.DateConverterConfig;
import pers.liujunyi.cloud.seata.interceptor.SeataHandlerInterceptor;

import javax.annotation.PostConstruct;

/***
 * 文件名称: WebConfiguration.java
 * 文件描述:
 * 公 司:
 * 内容摘要:
 * 其他说明: 在一个项目中WebMvcConfigurationSupport只能存在一个，多个的时候，只有一个会生效
 * 完成日期:2019年01月17日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 注册资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注册swagger 资源  不然访问swagger-ui.html 是404
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }


    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return handlerMapping;
    }


    /**
     *  前端传递的日期字符串 自动转换为 Data 类型
     * @return
     */
    @PostConstruct
    public void initEditableAvlidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new DateConverterConfig());
        }
    }



    /**
     *  配置拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 配置SeataHandlerInterceptor拦截器拦截所有的  如果不使用Seata 则不需要配置SeataHandlerInterceptor
        // pers.liujunyi.cloud.seata.interceptor.SeataHandlerInterceptor
        registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}