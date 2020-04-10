package pers.liujunyi.cloud.photo.controller.album;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.liujunyi.cloud.common.annotation.ApiVersion;
import pers.liujunyi.cloud.common.controller.BaseController;
import pers.liujunyi.cloud.common.dto.IdParamDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureDto;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureQueryDto;
import pers.liujunyi.cloud.photo.service.album.RollingPictureMongoService;
import pers.liujunyi.cloud.photo.service.album.RollingPictureService;

import javax.validation.Valid;

/***
 * 文件名称: RollingPictureController.java
 * 文件描述: 轮播图 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "轮播图 API")
@RestController
public class RollingPictureController extends BaseController {

    @Autowired
    private RollingPictureService rollingPictureService;
    @Autowired
    private RollingPictureMongoService rollingPictureMongoService;

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
    @PostMapping(value = "verify/carousel/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid RollingPictureDto param) {
        return this.rollingPictureService.saveRecord(param);
    }


    /**
     * 批量删除
     *
     * @param param 　 多个id 用 , 隔开
     * @return
     */
    @ApiOperation(value = "删除多条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @DeleteMapping(value = "verify/carousel/d/b")
    @ApiVersion(1)
    public ResultInfo batchDelete(@Valid IdParamDto param) {
        return this.rollingPictureService.deleteBatch(param.getIdList());
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
    @GetMapping(value = "table/carousel/g")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid RollingPictureQueryDto query) {
        return this.rollingPictureMongoService.findPageGird(query);
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
    @PutMapping(value = "verify/carousel/p")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.rollingPictureService.updateStatus(param.getStatus(), param.getId());
    }


    /**
     *  根据页面 和 页面位置 获取对应的图片
     *
     * @param pageCode
     * @param pagePosition
     *
     * @return
     */
    @ApiOperation(value = "根据页面 和 页面位置 获取对应的图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1"),
            @ApiImplicitParam(name = "pageCode", value = "pageCode", paramType = "query",   required = true, dataType = "String"),
            @ApiImplicitParam(name = "pagePosition", value = "pagePosition", paramType = "query",   required = true, dataType = "String")

    })
    @GetMapping(value = "table/carousel/picture")
    @ApiVersion(1)
    public ResultInfo findByBusinessCodeAndPositionOrderByPriority(String pageCode, String pagePosition) {
        if (StringUtils.isNotBlank(pageCode) || StringUtils.isNotBlank(pagePosition)) {
            return this.rollingPictureMongoService.findByPageCodeAndPagePositionOrderByPriorityAsc(pageCode, pagePosition );
        } else {
            return ResultUtil.fail();
        }
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
    @PostMapping(value = "verify/carousel/sync")
    @ApiVersion(1)
    public ResultInfo syncDataToMongo() {
        return this.rollingPictureService.syncDataToMongo();
    }
}
