package pers.liujunyi.cloud.photo.controller.album;

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
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.photo.domain.album.AlbumDto;
import pers.liujunyi.cloud.photo.domain.album.AlbumQueryDto;
import pers.liujunyi.cloud.photo.service.album.AlbumMongoService;
import pers.liujunyi.cloud.photo.service.album.AlbumService;

import javax.validation.Valid;

/***
 * 文件名称: AlbumController.java
 * 文件描述: 相册管理 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "相册管理 API")
@RestController
public class AlbumController  extends BaseController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumMongoService albumMongoService;

    /**
     * 保存数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "保存数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1")
    })
    @PostMapping(value = "verify/album/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid AlbumDto param) {
        return this.albumService.saveRecord(param);
    }

    /**
     * 单条删除相册数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "单条删除相册数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1")
    })
    @DeleteMapping(value = "verify/album/d")
    @ApiVersion(1)
    public ResultInfo singleDelete(@Valid IdParamDto param) {
        return this.albumService.deleteSingle(param.getId());
    }

    /**
     * 单条删除图片数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "单条删单条删除图片数据除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1")
    })
    @DeleteMapping(value = "ignore/album/d/picture")
    @ApiVersion(1)
    public ResultInfo singleDeleteAlbumPicture(@Valid IdParamDto param) {
        return this.albumService.deleteAlbumPictureById(param.getId());
    }


    /**
     * 分页列表数据
     *
     * @param query
     * @return
     */
    @ApiOperation(value = "分页列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1")
    })
    @GetMapping(value = "table/album/g")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid AlbumQueryDto query) {
        return this.albumMongoService.findPageGird(query);
    }


    /**
     *  修改数据状态
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "修改数据状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
    })
    @PutMapping(value = "verify/album/p")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.albumService.updateStatus(param.getStatus(), param.getId());
    }

    /**
     *  根据ID 获取相册基础信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID 获取相册基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/album/{id}")
    @ApiVersion(1)
    public ResultInfo findById(@PathVariable(name = "id") Long id) {
        return ResultUtil.success(this.albumMongoService.detailsById(id));
    }


    /**
     *  根据ID 获取数据详情（包含图片信息）
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID 获取数据详情 （包含图片信息）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/album/picture/{id}")
    @ApiVersion(1)
    public ResultInfo detailsById(@PathVariable(name = "id") Long id) {
        return this.albumMongoService.details(id);
    }


    /**
     *  根据相册ID 获取图片数据详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据相册ID 获取图片数据详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/album/picture")
    @ApiVersion(1)
    public ResultInfo findAlbumPictureByAlbumId(Long id) {
        return this.albumMongoService.findAlbumPictureByAlbumId(id);
    }

    /**
     *  同步数据到es中
     * @param
     * @return
     */
    @ApiOperation(value = "同步数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
    })
    @PostMapping(value = "verify/album/sync")
    @ApiVersion(1)
    public ResultInfo syncDataToMongo() {
        return this.albumService.syncDataToMongo();
    }


    /**
     *  相册下拉框信息
     * @return
     */
    @ApiOperation(value = "相册下拉框信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1")

    })
    @GetMapping(value = "table/album/comboBox")
    @ApiVersion(1)
    public ResultInfo albumComboBox() {
        return this.albumMongoService.albumComboBox();
    }

}
