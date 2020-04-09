package pers.liujunyi.cloud.centre.service.rpc;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.liujunyi.cloud.centre.api.service.dict.DictService;
import pers.liujunyi.cloud.dict.util.DictUtil;

import java.util.List;
import java.util.Map;

/***
 * 文件名称: DictServiceImpl.java
 * 文件描述: 数据字典 RPC Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明: @Service 包为 org.apache.dubbo.config.annotation.Service
 * 完成日期:2019年12月31日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
@Service(timeout = 10000)
public class DictServiceImpl implements DictService {

    @Autowired
    private DictUtil dictUtil;

    @Override
    public String getDictName(String parentDictCode, String dictCode) {
        return dictUtil.getDictName(parentDictCode, dictCode);
    }

    @SentinelResource(value = "dictNameToMap", blockHandler = "blockHandler", fallback = "fallbackHandler")
    @Override
    public Map<String, String> getDictNameToMap(String fullParentCode) {
        return dictUtil.getDictNameToMap(fullParentCode);
    }

    /**
     * @SentinelResource 熔断限流配置
     * value：资源名称，必需项（不能为空）
     * blockHandler 可选项 blockHandler对应处理 BlockException 的函数名称
     * fallback fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑
     * @param parentDictCodes  父级 dict code
     * @return
     */
    @SentinelResource(value = "dictNameToMapList", blockHandler = "blockHandler", fallback = "fallbackHandler")
    @Override
    public Map<String, Map<String, String>> getDictNameToMapList(List<String> parentDictCodes) {
        return dictUtil.getDictNameToMapList(parentDictCodes);
    }

    /**
     * 限流与阻塞处理
     * @param obj
     * @param ex
     */
    public Object blockHandler(Object obj, BlockException ex) {
        return null;
    }

    /**
     * 熔断与降级处理
     * @param obj
     */
    public Object fallbackHandler(Object obj, Throwable ex) {
        return null;
    }
}
