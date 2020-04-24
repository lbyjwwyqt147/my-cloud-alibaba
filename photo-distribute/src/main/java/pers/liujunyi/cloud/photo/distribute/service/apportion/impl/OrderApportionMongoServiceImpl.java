package pers.liujunyi.cloud.photo.distribute.service.apportion.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pers.liujunyi.cloud.centre.api.service.dict.DictService;
import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseMongoServiceImpl;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionQueryDto;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionVo;
import pers.liujunyi.cloud.photo.distribute.entity.apportion.OrderApportion;
import pers.liujunyi.cloud.photo.distribute.repository.mongo.apportion.OrderApportionMongoRepository;
import pers.liujunyi.cloud.photo.distribute.service.apportion.OrderApportionMongoService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * 文件名称: OrderApportionMongoServiceImpl.java
 * 文件描述: 订单分配 Mongo Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class OrderApportionMongoServiceImpl extends BaseMongoServiceImpl<OrderApportion, Long> implements OrderApportionMongoService {

    @Autowired
    private OrderApportionMongoRepository orderApportionMongoRepository;

    /** org.apache.dubbo.config.annotation.Reference */
    @Reference
    DictService dictService;

    public OrderApportionMongoServiceImpl(BaseMongoRepository<OrderApportion, Long> baseMongoRepository) {
        super(baseMongoRepository);
    }

    @Override
    public ResultInfo findPageGird(OrderApportionQueryDto query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "albumPriority");
        Pageable pageable = query.toPageable(sort);
        // 查询条件
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询总记录条数
        long totalElements = this.mongoDbTemplate.count(searchQuery, OrderApportion.class);
        // 查询数据
        List<OrderApportion> searchPageResults =  this.mongoDbTemplate.find(searchQuery, OrderApportion.class);
        List<OrderApportionVo> resultDataList = new CopyOnWriteArrayList<>();

        ResultInfo result = ResultUtil.success(resultDataList);
        result.setTotal(totalElements);
        return  result;
    }

    @Override
    public ResultInfo details(Long id) {
        return ResultUtil.success(this.findById(id));
    }


    @Override
    public OrderApportion findById(Long id) {
        Optional<OrderApportion> optional  = this.orderApportionMongoRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


}
