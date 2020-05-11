package pers.liujunyi.cloud.seata.interceptor;

import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/***
 * 文件名称: SeataRestTemplateInterceptor
 * 文件描述:  RestTemplate 请求传递全局事物xid
 * 公 司:
 * 内容摘要:
 * 其他说明: 使用Ribbon拦截到全局事务ID，然后绑定到请求头中
 * 完成日期:2020/5/8 10:47
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public class SeataRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    /**
     * RestTemplate请求拦截器
     * 在头部设置全局事务ID
     * @param request request
     * @param body 书序
     * @param execution ClientHttpRequestExecution
     * @return ClientHttpResponse
     * @throws IOException 异常
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            requestWrapper.getHeaders().add(RootContext.KEY_XID, xid);
        }
        return execution.execute(requestWrapper, body);
    }

}
