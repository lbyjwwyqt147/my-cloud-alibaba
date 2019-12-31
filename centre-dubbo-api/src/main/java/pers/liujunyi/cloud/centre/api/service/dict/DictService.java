package pers.liujunyi.cloud.centre.api.service.dict;

import java.util.List;
import java.util.Map;

/***
 * 数据字典
 * @author ljy
 */
public interface DictService {

    /**
     * 获取业务字典值
     * @param parentDictCode  父级字典代码
     * @param dictCode        需要转换的字典代码
     * @return
     */
    String getDictName(String parentDictCode, String dictCode);

    /**
     * 根据fullParentCode获取字典值 返回map
     * @param fullParentCode  父级 dict code
     * @return  返回 map   key = 字典代码   value = 字典名称
     */
    Map<String, String> getDictNameToMap(String fullParentCode);

    /**
     * 根据fullParentCodes 获取字典值 返回map
     * @param parentDictCodes  父级 dict code
     * @return  返回 map   key = 字典代码   value = map
     */
    Map<String, Map<String, String>> getDictNameToMapList(List<String> parentDictCodes);

}
