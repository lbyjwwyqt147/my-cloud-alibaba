package pers.liujunyi.cloud.centre.service.rpc;

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

    @Override
    public Map<String, String> getDictNameToMap(String fullParentCode) {
        return dictUtil.getDictNameToMap(fullParentCode);
    }

    @Override
    public Map<String, Map<String, String>> getDictNameToMapList(List<String> parentDictCodes) {
        return dictUtil.getDictNameToMapList(parentDictCodes);
    }
}
