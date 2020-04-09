package pers.liujunyi.cloud.centre.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.alibaba.sentinel.rest.SentinelClientHttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;

/**
 * Sentinel 处理类
 * @author ljy
 */
@Slf4j
public class SentinelExceptionHandler {

    /**
     * 限流后处理的方法
     * @param request
     * @param body
     * @param execution
     * @param ex
     * @return
     */
    public static SentinelClientHttpResponse handleException(HttpRequest request,
                                                             byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        log.error("sentinel 限流处理 远程调用被限流/降级 : " + ex.getClass().getCanonicalName());
        ResultInfo response = ResultUtil.error(500, "服务器繁忙,请稍候再试!");
        response.setExtend("sentinel 限流与阻塞处理");
        return new SentinelClientHttpResponse(JSON.toJSONString(response));
    }

    /**
     * 熔断降级后处理的方法
     * 用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了exceptionsToIgnore里面排除掉的异常类型）进行处理
     * @param request
     * @param body
     * @param execution
     * @param
     * @return
     */
    public static SentinelClientHttpResponse fallback(HttpRequest request,
                                                      byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        log.error("进入 sentinel 熔断处理 fallback: " + ex.getClass().getCanonicalName());
        ResultInfo response = ResultUtil.error(500, "服务器繁忙,请稍候再试!");
        response.setExtend("sentinel 熔断降级处理");
        return new SentinelClientHttpResponse(JSON.toJSONString(response));
    }
}
