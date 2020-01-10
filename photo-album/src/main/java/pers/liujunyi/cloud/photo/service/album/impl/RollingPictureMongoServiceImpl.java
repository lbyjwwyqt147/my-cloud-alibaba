package pers.liujunyi.cloud.photo.service.album.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.centre.api.service.dict.DictService;
import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureQueryDto;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureVo;
import pers.liujunyi.cloud.photo.entity.album.RollingPicture;
import pers.liujunyi.cloud.photo.repository.mongo.album.RollingPictureMongoRepository;
import pers.liujunyi.cloud.photo.service.album.RollingPictureMongoService;
import pers.liujunyi.cloud.photo.util.Constant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * 文件名称: RollingPictureMongoServiceImpl.java
 * 文件描述: 轮播图 Mongo Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class RollingPictureMongoServiceImpl extends BaseMongoServiceImpl<RollingPicture, Long> implements RollingPictureMongoService {

    @Autowired
    private RollingPictureMongoRepository rollingPictureMongoRepository;

    @Reference
    DictService dictService;

    public RollingPictureMongoServiceImpl(BaseMongoRepository<RollingPicture, Long> baseMongoRepository) {
        super(baseMongoRepository);
    }

    @Override
    public ResultInfo findPageGird(RollingPictureQueryDto query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "priority");
        Pageable pageable = query.toPageable(sort);
        // 查询条件
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询总记录条数
        long totalElements = this.mongoDbTemplate.count(searchQuery, RollingPicture.class);
        // 查询数据
        List<RollingPicture> searchPageResults =  this.mongoDbTemplate.find(searchQuery, RollingPicture.class);
        List<RollingPictureVo> resultDataList = new CopyOnWriteArrayList<>();
        if (!CollectionUtils.isEmpty(searchPageResults)) {
            searchPageResults.stream().forEach(item -> {
                RollingPictureVo RollingPictureVo = DozerBeanMapperUtil.copyProperties(item, RollingPictureVo.class);

                resultDataList.add(RollingPictureVo);
            });
        }
        ResultInfo result = ResultUtil.success(resultDataList);
        result.setTotal(totalElements);
        return  result;
    }


    @Override
    public ResultInfo findByPageCodeAndPagePositionOrderByPriorityAsc(String pageCode, String pagePosition){
        return ResultUtil.success(this.rollingPictureMongoRepository.findByPageCodeAndPagePositionAndStatusOrderByPriorityAsc(pageCode, pagePosition, Constant.ENABLE_STATUS));
    }

    @Override
    public List<RollingPicture> findByPageCodeAndPagePosition(String pageCode, String pagePosition) {
        return this.rollingPictureMongoRepository.findByPageCodeAndPagePositionOrderByPriorityAsc(pageCode, pagePosition);
    }


    @Transactional(
            value = "mongoTransactionManager",
            rollbackFor = {RuntimeException.class, Exception.class}
    )
    @Override
    public int deleteByPageCodeAndPagePosition(String pageCode, String pagePosition) {
        return 0;
    }

    @Transactional(
            value = "mongoTransactionManager",
            rollbackFor = {RuntimeException.class, Exception.class}
    )
    @Override
    public int deleteByBusinessId(Long businessId) {
        return 0;
    }


}
