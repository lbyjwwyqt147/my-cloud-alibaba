package pers.liujunyi.cloud.centre.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoDto;
import pers.liujunyi.cloud.centre.domain.user.UserDetailsInfoQueryDto;
import pers.liujunyi.cloud.centre.service.user.UserAuthService;
import pers.liujunyi.cloud.centre.service.user.UserDetailsInfoMongoService;
import pers.liujunyi.cloud.centre.service.user.UserDetailsInfoService;
import pers.liujunyi.cloud.common.annotation.ApiVersion;
import pers.liujunyi.cloud.common.controller.BaseController;
import pers.liujunyi.cloud.common.dto.IdParamDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;

import javax.validation.Valid;
import java.util.Date;

/***
 * 文件名称: UserDetailsInfoController.java
 * 文件描述: 用户详细档案 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月22日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "用户详细档案 API")
@RestController
public class UserDetailsInfoController extends BaseController {

    @Autowired
    private UserDetailsInfoService userDetailsInfoService;
    @Autowired
    private UserDetailsInfoMongoService userDetailsInfoMongoService;
    @Autowired
    private UserAuthService userAuthService;

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
    @PostMapping(value = "verify/staff/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid UserDetailsInfoDto param) {
        return this.userDetailsInfoService.saveRecord(param);
    }


    /**
     * 批量删除
     *
     * @param param 　 多个id 用 , 隔开
     * @return
     */
    @ApiOperation(value = "删除多条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "ids", value = "ids",  required = true, dataType = "String"),
            @ApiImplicitParam(name = "otherIds", value = "账户id",  required = true, dataType = "String")
    })
    @DeleteMapping(value = "verify/staff/d/b")
    @ApiVersion(1)
    public ResultInfo batchDelete(@Valid IdParamDto param) {
        return this.userDetailsInfoService.deleteBatch(param.getIdList(), param.getOtherIdList());
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
    @GetMapping(value = "table/staff/g")
    @ApiVersion(1)
    public ResultInfo findPageGrid(@Valid UserDetailsInfoQueryDto query) {
        return this.userDetailsInfoMongoService.findPageGird(query);
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
            @ApiImplicitParam(name = "ids", value = "ids",  required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "status",  required = true, dataType = "integer"),
            @ApiImplicitParam(name = "otherIds", value = "账户id",  required = true, dataType = "integer")
    })
    @PutMapping(value = "verify/staff/p/b")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.userDetailsInfoService.updateStatus(param.getStatus(), param.getIdList(), param.getOtherIdList(), param.getPutParams());
    }

    /**
     * 根据id 获取详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id 获取详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/staff/{id}")
    @ApiVersion(1)
    public ResultInfo findById(@PathVariable(name = "id") Long id) {
        return  ResultUtil.success(this.userDetailsInfoMongoService.findById(id));
    }

    /**
     * 用户下拉框数据
     * @param query
     * @return
     */
    @ApiOperation(value = "用户下拉框数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
    })
    @GetMapping(value = "table/staff/select")
    @ApiVersion(1)
    public ResultInfo staffSelect(@Valid UserDetailsInfoQueryDto query) {
        return ResultUtil.success(this.userDetailsInfoMongoService.staffSelect(query));
    }

    /**
     * 根据 账户id 获取详细信息
     * @param staffAccountsId
     * @return
     */
    @ApiOperation(value = "根据账户id  获取详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "staffAccountsId", value = "staffAccountsId", paramType = "path",   required = true, dataType = "Long")
    })
    @GetMapping(value = "table/staff/g/accounts/{staffAccountsId}")
    @ApiVersion(1)
    public ResultInfo findByStaffAccountsId(@PathVariable(name = "staffAccountsId") Long staffAccountsId) {
        return this.userDetailsInfoMongoService.findByUserAccountsId(staffAccountsId);
    }

    /**
     * 设置头像
     * @param id
     * @param portrait
     * @param portraitId
     * @return
     */
    @ApiOperation(value = "根据账户id  获取详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "query",   required = true, dataType = "Long"),
            @ApiImplicitParam(name = "portrait", value = "头像地址", paramType = "query",   required = true, dataType = "String"),
            @ApiImplicitParam(name = "portraitId", value = "头像ID", paramType = "query",   required = true, dataType = "Long")
    })
    @PutMapping(value = "ignore/staff/p/portrait")
    @ApiVersion(1)
    public ResultInfo setPortrait(Long id, String portrait, Long portraitId) {
        return this.userDetailsInfoService.setPortrait(id, portrait, portraitId);
    }

    /**
     * 设置离职信息
     * @param id
     * @param userId
     * @param date
     * @param dimissionReason
     * @param dataVersion
     * @return
     */
    @ApiOperation(value = "根据id  设置离职信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
            @ApiImplicitParam(name = "id", value = "id", paramType = "query",   required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "userId", paramType = "query",   required = true, dataType = "String"),
            @ApiImplicitParam(name = "date", value = "离职日期", paramType = "query",   required = true, dataType = "String"),
            @ApiImplicitParam(name = "dimissionReason", value = "离职原因", paramType = "query",   required = true, dataType = "String"),
            @ApiImplicitParam(name = "dataVersion", value = "数据版本号", paramType = "query",   required = true, dataType = "Long")
    })
    @PutMapping(value = "verify/staff/p/dimission")
    @ApiVersion(1)
    public ResultInfo setCurDimissionInfo(Long id, Long userId, Date date, String dimissionReason, Long dataVersion) {
        return this.userDetailsInfoService.setCurDimissionInfo(id, userId, date, dimissionReason, dataVersion);
    }

    /**
     *  API接口权限校验
     * @param
     * @return
     */
    @ApiOperation(value = "API接口权限校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
    })
    @GetMapping(value = "ignore/authority/authentication")
    @ApiVersion(1)
    public ResultInfo isAuthenticated(String token, String requestUrl) {
        ResultInfo resultInfo = ResultUtil.success();
        boolean isAuth = this.userAuthService.isAuthenticated(token, requestUrl);
        resultInfo.setSuccess(isAuth);
        return resultInfo;
    }

    /**
     *  获取用户详细信息
     * @param
     * @return
     */
    @ApiOperation(value = "获取用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "query", required = true, dataType = "integer", defaultValue = "v1"),
    })
    @GetMapping(value = "ignore/user/details/{token}")
    @ApiVersion(5)
    public ResultInfo getUserDetails(@PathVariable(name = "token")String token) {
        ResultInfo resultInfo = ResultUtil.success();
        resultInfo.setData(this.userAuthService.getUserDetails(token));
        return resultInfo;
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
    @PostMapping(value = "verify/staff/sync")
    @ApiVersion(1)
    public ResultInfo syncDataToMongo() {
        return this.userDetailsInfoService.syncDataToMongo();
    }
}
