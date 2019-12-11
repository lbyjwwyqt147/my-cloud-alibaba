package pers.liujunyi.cloud.centre.service.user.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoQueryDto;
import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoVo;
import pers.liujunyi.cloud.centre.entity.user.UserDetailsInfo;
import pers.liujunyi.cloud.centre.repository.mongo.user.UserDetailsInfoMongoRepository;
import pers.liujunyi.cloud.centre.service.user.UserDetailsInfoMongoService;
import pers.liujunyi.cloud.centre.util.CentreUtils;
import pers.liujunyi.cloud.centre.util.Constant;
import pers.liujunyi.cloud.centre.util.DictConstant;
import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.common.util.RemoteCloudUtil;
import pers.liujunyi.cloud.dict.util.DictUtil;
import pers.liujunyi.cloud.security.entity.organizations.Organizations;
import pers.liujunyi.cloud.security.entity.user.UserAccounts;
import pers.liujunyi.cloud.security.service.organizations.StaffOrgMongoService;
import pers.liujunyi.cloud.security.service.user.UserAccountsMongoService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


/***
 * 文件名称: UserDetailsInfoMongoServiceImpl.java
 * 文件描述: 用户详细档案信息 Mongo Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月10日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class UserDetailsInfoMongoServiceImpl extends BaseMongoServiceImpl<UserDetailsInfo, Long> implements UserDetailsInfoMongoService {

    @Autowired
    private UserDetailsInfoMongoRepository userDetailsInfoMongoRepository;
    @Autowired
    private UserAccountsMongoService userAccountsMongoService;
    @Autowired
    private DictUtil dictUtil;
    @Autowired
    private RemoteCloudUtil remoteCloudUtil;
    @Autowired
    private StaffOrgMongoService staffOrgMongoService;

    public UserDetailsInfoMongoServiceImpl(BaseMongoRepository<UserDetailsInfo, Long> baseMongoRepository) {
        super(baseMongoRepository);
    }


    @Override
    public UserDetailsInfo findFirstByUserAccountsIdAndStaffStatus(Long staffAccountsId, Byte staffStatus) {
        return this.userDetailsInfoMongoRepository.findFirstByUserAccountsIdAndUserStatus(staffAccountsId, staffStatus);
    }

    @Override
    public UserDetailsInfo findUserDetails(Long staffAccountsId) {
        return this.userDetailsInfoMongoRepository.findFirstByUserAccountsId(staffAccountsId);
    }

    @Override
    public ResultInfo findPageGird(UserDetailsInfoQueryDto query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "entryDate");
        Pageable pageable = query.toPageable(sort);
        // 查询条件
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询总记录条数
        long totalElements = this.mongoDbTemplate.count(searchQuery, UserDetailsInfo.class);
        // 查询数据
        List<UserDetailsInfo> searchPageResults =  this.mongoDbTemplate.find(searchQuery, UserDetailsInfo.class);
        List<UserDetailsInfoVo> resultDataList = new CopyOnWriteArrayList<>();
        if (!CollectionUtils.isEmpty(searchPageResults)) {
            List<Long> userIds =  searchPageResults.stream().map(UserDetailsInfo::getId).distinct().collect(Collectors.toList());
            // 获取行政区划
            List<Long> districtList = searchPageResults.stream().map(UserDetailsInfo::getDistrict).distinct().collect(Collectors.toList());
            Map<Long, String> districtMap = this.remoteCloudUtil.getAreaNameToMap(districtList);
            // 获取组织机构信息
            Map<Long, List<Organizations>> orgInfoMap = this.staffOrgMongoService.getOrgInfoByStaffIdIn(userIds);
            searchPageResults.stream().forEach(item -> {
                UserDetailsInfoVo staffDetailsInfo = DozerBeanMapperUtil.copyProperties(item, UserDetailsInfoVo.class);
                if (!CollectionUtils.isEmpty(orgInfoMap)) {
                    List<Organizations> organizationsList = orgInfoMap.get(item.getId());
                    if (!CollectionUtils.isEmpty(organizationsList)) {
                        Organizations organizations = organizationsList.get(0);
                        staffDetailsInfo.setStaffOrgName(organizations.getFullName());
                        staffDetailsInfo.setOrgId(organizations.getId());
                    }
                }
                staffDetailsInfo.setAddressText(!CollectionUtils.isEmpty(districtMap) ? districtMap.get(item.getDistrict()) : "");
                staffDetailsInfo.setUserId(item.getUserAccountsId());
                resultDataList.add(staffDetailsInfo);
            });
        }
        ResultInfo result = ResultUtil.success(resultDataList);
        result.setTotal(totalElements);
        return  result;
    }

    @Override
    public ResultInfo findByUserAccountsId(Long staffAccountsId) {
        return ResultUtil.success(this.getStaffDetailsByUserAccountsId(staffAccountsId));
    }

    @Override
    public UserDetailsInfoVo getStaffDetailsByUserAccountsId(Long staffAccountsId) {
        return null;
    }


    @Override
    public UserDetailsInfoVo getUserDetailsById(Long id) {
        Optional<UserDetailsInfo> optional  = this.userDetailsInfoMongoRepository.findById(id);
        if (optional.isPresent()) {
            UserDetailsInfoVo detailsInfoVo = DozerBeanMapperUtil.copyProperties(optional.get(), UserDetailsInfoVo.class);
            // 获取数据字典值
            List<String> dictCodeList = new LinkedList<>();
            dictCodeList.add(DictConstant.STAFF_SKILL);
            dictCodeList.add(DictConstant.STAFF_POSITION);
            // 获取行政区划
            String district = this.remoteCloudUtil.getAreaName(detailsInfoVo.getDistrict());
            // 获取账户信息
            UserAccounts userAccounts = this.userAccountsMongoService.getOne(detailsInfoVo.getUserAccountsId());
            // 获取组织机构信息
            List<Organizations> orgInfo = this.staffOrgMongoService.getOrgInfoByStaffId(id);
            if (!CollectionUtils.isEmpty(orgInfo)) {
                Organizations organizations = orgInfo.get(0);
                detailsInfoVo.setStaffOrgName(organizations.getFullName());
                detailsInfoVo.setOrgId(organizations.getId());
            }
            if (StringUtils.isBlank(detailsInfoVo.getWorkingYears())) {
                detailsInfoVo.setWorkingYears(CentreUtils.getWorkingYears(detailsInfoVo.getEntryDate(), detailsInfoVo.getDimissionDate()));
            }
            if (detailsInfoVo.getBirthday() != null && detailsInfoVo.getUserAge() == null) {
                detailsInfoVo.setUserAge(CentreUtils.getAge(detailsInfoVo.getBirthday()));
            }
            detailsInfoVo.setAddressText(district + detailsInfoVo.getStreet());
            detailsInfoVo.setDataVersion(userAccounts.getDataVersion());
            detailsInfoVo.setUserId(detailsInfoVo.getUserAccountsId());
            return detailsInfoVo;
        }
        return null;
    }

    @Override
    public List<Map<String, String>> staffSelect(UserDetailsInfoQueryDto query) {
        query.setUserStatus(Constant.ENABLE_STATUS);
        List<Map<String, String>> result = new LinkedList<>();
        // 查询数据
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询数据
        List<UserDetailsInfo> searchPageResults =  this.mongoDbTemplate.find(searchQuery, UserDetailsInfo.class);
        if (!CollectionUtils.isEmpty(searchPageResults)) {
            searchPageResults.stream().forEach(item -> {
                Map<String, String> map = new ConcurrentHashMap<>();
                map.put("id", item.getId().toString());
                map.put("text", item.getUserFullName());
                result.add(map);
            });
        }
        return result;
    }

    @Override
    public Map<Long, String> getStaffNameMap(List<Long> ids) {
        List<UserDetailsInfo> userDetailsInfoList = this.findAllByIdIn(ids);
        if (!CollectionUtils.isEmpty(userDetailsInfoList)) {
            Map<Long, String> nameMap = new ConcurrentHashMap<>();
            userDetailsInfoList.stream().forEach(item -> {
                nameMap.put(item.getId(), item.getUserFullName());
            });
            return nameMap;
        }
        return null;
    }

    @Override
    public Map<Long, UserDetailsInfo> getDetailsInfoMap(List<Long> ids) {
        List<UserDetailsInfo> userDetailsInfoList = this.findAllByIdIn(ids);
        if (!CollectionUtils.isEmpty(userDetailsInfoList)) {
            return userDetailsInfoList.stream().collect(Collectors.toMap(UserDetailsInfo::getId, detailsInfo -> detailsInfo));
        }
        return null;
    }


}
