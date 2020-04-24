package pers.liujunyi.cloud.photo.distribute.controller.apportion;

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
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionDto;
import pers.liujunyi.cloud.photo.distribute.domain.apportion.OrderApportionQueryDto;
import pers.liujunyi.cloud.photo.distribute.service.apportion.OrderApportionMongoService;
import pers.liujunyi.cloud.photo.distribute.service.apportion.OrderApportionService;

import javax.validation.Valid;

/***
 * 文件名称: OrderApportionController.java
 * 文件描述: 订单分配 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "订单分配 API")
@RestController
public class OrderApportionController extends BaseController {

    @Autowired
    private OrderApportionService orderApportionService;
    @Autowired
    private OrderApportionMongoService orderApportionMongoService;

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
    @PostMapping(value = "verify/order/apportion/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid OrderApportionDto param) {
        return this.orderApportionService.saveRecord(param);
    }

    /**
     * 单条删除相册数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "单条删除相册数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path", dataType = "String", defaultValue = "v1")
    })
    @DeleteMapping(value = "verify/order/apportion/d")
    @ApiVersion(1)
    public ResultInfo singleDelete(@Valid IdParamDto param) {
        return this.orderApportionService.deleteSingle(param.getId());
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
    @GetMapping(value = "table/order/apportion/g")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid OrderApportionQueryDto query) {
        return this.orderApportionMongoService.findPageGird(query);
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
    @PutMapping(value = "verify/order/apportion/p")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.orderApportionService.updateStatus(param.getStatus(), param.getId());
    }



    /**
     *  根据ID 获取数据详情（包含图片信息）
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID 获取数据详情 （包含图片信息）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/order/apportion/{id}")
    @ApiVersion(1)
    public ResultInfo detailsById(@PathVariable(name = "id") Long id) {
        return this.orderApportionMongoService.details(id);
    }



    /**
     *  同步数据到es中
     * @param
     * @return
     */
    @ApiOperation(value = "同步数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
    })
    @PostMapping(value = "verify/order/apportion/sync")
    @ApiVersion(1)
    public ResultInfo syncDataToMongo() {
        return this.orderApportionService.syncDataToMongo();
    }


}
