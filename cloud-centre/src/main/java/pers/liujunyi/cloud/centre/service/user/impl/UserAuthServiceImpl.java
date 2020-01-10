package pers.liujunyi.cloud.centre.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.centre.api.domain.dto.UserDetailsInfoDto;
import pers.liujunyi.cloud.centre.service.user.UserAuthService;
import pers.liujunyi.cloud.common.redis.RedisTemplateUtils;
import pers.liujunyi.cloud.common.util.SecurityLocalContext;
import pers.liujunyi.cloud.common.vo.BaseRedisKeys;
import pers.liujunyi.cloud.security.entity.authorization.MenuResource;
import pers.liujunyi.cloud.security.entity.authorization.RoleInfo;
import pers.liujunyi.cloud.security.repository.mongo.authorization.RoleInfoMongoRepository;
import pers.liujunyi.cloud.security.security.filter.CustomInvocationSecurityMetadataSource;
import pers.liujunyi.cloud.security.service.authorization.RoleResourceMongoService;
import pers.liujunyi.cloud.security.util.SecurityConstant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/***
 * 文件名称: UserAuthServiceImpl.java
 * 文件描述:  用户权限 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Log4j2
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private RedisTemplateUtils redisTemplateUtil;
    @Autowired
    private RoleInfoMongoRepository roleInfoMongoRepository;
    @Autowired
    private RoleResourceMongoService roleResourceMongoService;

    @Override
    public Boolean isAuthenticated(String token, String requestUrl) {
        // 加载资源配置对象
        this.loadResourceDefine();
        AtomicInteger index = new AtomicInteger(requestUrl.indexOf("v", 1));
        String url = requestUrl.replace(requestUrl.substring(index.get(), index.addAndGet(2)), "*");
        if (resourceMap != null && resourceMap.get(url) != null) {
            // 请求拥有的角色授权码
            Iterator<ConfigAttribute> iterator = resourceMap.get(url).iterator();
            while (iterator.hasNext()){
                ConfigAttribute configAttribute = iterator.next();
                String needRole = configAttribute.getAttribute();
                // 当前登录人的角色权限授权码
                String[] authorities = SecurityLocalContext.getAuthorities();
                for(String authoritie : authorities){
                    //authoritie 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
                    //判断两个请求的url的权限和用户具有的权限是否相同，如相同，允许访问 权限就是那些以ROLE_为前缀的角色
                    if (needRole.trim().equals(authoritie.trim())){
                        //匹配到对应的角色，则允许通过
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public UserDetailsInfoDto getUserDetails(String token) {
        pers.liujunyi.cloud.centre.api.domain.dto.UserDetailsInfoDto currentUser = null;
        if (StringUtils.isNotBlank(token)) {
            // 从token中获取用户
            Object redisAuthentication = this.redisTemplateUtil.hget(BaseRedisKeys.USER_LOGIN_TOKNE, token);
            if (redisAuthentication != null) {
                currentUser = JSONObject.parseObject(redisAuthentication.toString(), UserDetailsInfoDto.class);
            }
        }
        return currentUser;
    }

    /**
     * 初始化资源 ,提取系统中的所有权限，加载所有url和权限（或角色）的对应关系， 以便拦截无权放访问的用户请求。  web容器启动就会执行
     */
    private void loadResourceDefine() {
        resourceMap = CustomInvocationSecurityMetadataSource.resourceMap;
        if (resourceMap == null) {
            //应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
            resourceMap = new ConcurrentHashMap<>();
            String prefix = SecurityConstant.ROLE_PREFIX;
            // 获取系统中的所有角色信息
            List<RoleInfo> roleList = this.roleInfoMongoRepository.findAll();
            if (!CollectionUtils.isEmpty(roleList)) {
                List<Long> roleIds = roleList.stream().map(RoleInfo::getId).collect(Collectors.toList());
                // 获取角色分配的的功能按钮资源
                Map<Long, List<MenuResource>> buttonResourceMap = this.roleResourceMongoService.getResourceByRoleIdIn(roleIds, (byte)3);
                roleList.stream().forEach(role -> {
                    if (StringUtils.isNotBlank(role.getRoleAuthorizationCode())) {
                        //角色授权码
                        String authorizedCode = prefix + role.getRoleAuthorizationCode().trim().toUpperCase();
                        ConfigAttribute configAttributes = new SecurityConfig(authorizedCode);
                        List<MenuResource> resourceList = buttonResourceMap.get(role.getId());
                        if (!CollectionUtils.isEmpty(resourceList)) {
                            List<String> urlPatch = resourceList.stream().filter(r -> StringUtils.isNotBlank(r.getMenuPath())).map(MenuResource::getMenuPath).distinct().collect(Collectors.toList());
                            urlPatch.stream().forEach(item -> {
                                // 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
                                if (resourceMap.containsKey(item)) {
                                    Collection<ConfigAttribute> value = resourceMap.get(item);
                                    value.add(configAttributes);
                                    resourceMap.put(item, value);
                                } else {
                                    Collection<ConfigAttribute> atts = new ArrayList<>();
                                    atts.add(configAttributes);
                                    resourceMap.put(item, atts);
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}
