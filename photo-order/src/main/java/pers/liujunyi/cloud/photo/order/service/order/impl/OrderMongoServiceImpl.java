package pers.liujunyi.cloud.photo.order.service.order.impl;

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
import pers.liujunyi.cloud.photo.order.domain.order.OrderQueryDto;
import pers.liujunyi.cloud.photo.order.domain.order.OrderVo;
import pers.liujunyi.cloud.photo.order.entity.order.OrderInfo;
import pers.liujunyi.cloud.photo.order.repository.mongo.order.OrderMongoRepository;
import pers.liujunyi.cloud.photo.order.service.order.OrderMongoService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * 文件名称: OrderMongoServiceImpl.java
 * 文件描述: 订单 Mongo Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class OrderMongoServiceImpl extends BaseMongoServiceImpl<OrderInfo, Long> implements OrderMongoService {

    @Autowired
    private OrderMongoRepository orderMongoRepository;

    /** org.apache.dubbo.config.annotation.Reference */
    @Reference
    DictService dictService;

    public OrderMongoServiceImpl(BaseMongoRepository<OrderInfo, Long> baseMongoRepository) {
        super(baseMongoRepository);
    }

    @Override
    public ResultInfo findPageGird(OrderQueryDto query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "albumPriority");
        Pageable pageable = query.toPageable(sort);
        // 查询条件
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询总记录条数
        long totalElements = this.mongoDbTemplate.count(searchQuery, OrderInfo.class);
        // 查询数据
        List<OrderInfo> searchPageResults =  this.mongoDbTemplate.find(searchQuery, OrderInfo.class);
        List<OrderVo> resultDataList = new CopyOnWriteArrayList<>();

        ResultInfo result = ResultUtil.success(resultDataList);
        result.setTotal(totalElements);
        return  result;
    }

    @Override
    public ResultInfo details(Long id) {
        return ResultUtil.success(this.findById(id));
    }


    @Override
    public OrderInfo findById(Long id) {
        Optional<OrderInfo> optional  = this.orderMongoRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
