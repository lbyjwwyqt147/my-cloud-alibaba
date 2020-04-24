package pers.liujunyi.cloud.photo.order.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.liujunyi.cloud.common.annotation.ApiVersion;
import pers.liujunyi.cloud.common.controller.BaseController;
import pers.liujunyi.cloud.common.dto.IdParamDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.photo.order.domain.order.OrderDto;
import pers.liujunyi.cloud.photo.order.domain.order.OrderQueryDto;
import pers.liujunyi.cloud.photo.order.service.order.OrderMongoService;
import pers.liujunyi.cloud.photo.order.service.order.OrderService;

import javax.validation.Valid;

/***
 * 文件名称: OrderController.java
 * 文件描述: 订单管理 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "订单管理 API")
@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMongoService orderMongoService;

    /**
     * 保存数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "保存数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @PostMapping(value = "verify/order/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid OrderDto param) {
        return this.orderService.saveRecord(param);
    }



    /**
     * 分页列表数据
     *
     * @param query
     * @return
     */
    @ApiOperation(value = "分页列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @GetMapping(value = "table/order/g")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid OrderQueryDto query) {
        return this.orderMongoService.findPageGird(query);
    }


    /**
     *  修改数据状态
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "修改数据状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
    })
    @PutMapping(value = "verify/order/p")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.orderService.updateStatus(param.getStatus(), param.getId());
    }




    /**
     *  根据ID 获取数据详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID 获取数据详情 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/order/{id}")
    @ApiVersion(1)
    public ResultInfo detailsById(@PathVariable(name = "id") Long id) {
        return this.orderMongoService.details(id);
    }




    /**
     *  同步数据到Mongo中
     * @param
     * @return
     */
    @ApiOperation(value = "同步数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
    })
    @PostMapping(value = "verify/order/sync")
    @ApiVersion(1)
    public ResultInfo syncDataToMongo() {
        return this.orderService.syncDataToMongo();
    }

}
