package pers.liujunyi.cloud.seata.interceptor;

import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 文件名称: SeataHandlerInterceptor
 * 文件描述: 拦截所有的头部的全局事务ID, 分布式全局事物XID 传递
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/5/8 10:51
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public class SeataHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 拦截前处理
     * 将全局事务ID绑定到上下文中
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler handler
     * @return 是否继续下一步
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String currentXid = RootContext.getXID();
        String globalXid = request.getHeader(RootContext.KEY_XID);
        if (StringUtils.isBlank(currentXid) && StringUtils.isNotBlank(globalXid)) {
            RootContext.bind(globalXid);
        }
        return true;
    }

    /**
     * 拦截后处理
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler handler
     * @param ex 异常
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String globalXid = request.getHeader(RootContext.KEY_XID);
        if (StringUtils.isBlank(globalXid)) {
            return;
        }
        String unBindXid = RootContext.unbind();
        //在事务期间被更改过
        if (!globalXid.equalsIgnoreCase(unBindXid)) {
            RootContext.bind(unBindXid);
        }
    }

}
