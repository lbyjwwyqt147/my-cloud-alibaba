package pers.liujunyi.cloud.seata.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;

/***
 * 文件名称: SeataFeignClientInterceptor
 * 文件描述: FeignClient拦截
 * 内容摘要:
 * 其他说明: 使用FeignClient拦截到全局事务ID，然后绑定到请求头中去
 * 完成日期:2020/5/8 10:42
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public class SeataFeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            requestTemplate.header(RootContext.KEY_XID, xid);
        }
    }
}
