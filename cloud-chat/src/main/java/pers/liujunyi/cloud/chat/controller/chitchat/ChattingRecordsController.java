package pers.liujunyi.cloud.chat.controller.chitchat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.liujunyi.cloud.chat.domain.chitchat.ChattingRecordsDto;
import pers.liujunyi.cloud.chat.service.chitchat.ChattingRecordsService;
import pers.liujunyi.cloud.common.annotation.ApiVersion;
import pers.liujunyi.cloud.common.controller.BaseController;
import pers.liujunyi.cloud.common.dto.IdParamDto;
import pers.liujunyi.cloud.common.restful.ResultInfo;

import javax.validation.Valid;

/***
 * 文件名称: ChattingRecordsController.java
 * 文件描述: 聊天纪录 Controller
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Api(tags = "聊天纪录 API")
@RestController
public class ChattingRecordsController extends BaseController {

    @Autowired
    private ChattingRecordsService chattingRecordsService;


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
    @PostMapping(value = "verify/chatting/records/s")
    @ApiVersion(1)
    public ResultInfo saveRecord(@Valid ChattingRecordsDto param) {
        return this.chattingRecordsService.saveRecord(param);
    }

    /**
     * 单条删除相册数据
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "单条删除相册数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @DeleteMapping(value = "verify/chatting/records/d")
    @ApiVersion(1)
    public ResultInfo singleDelete(@Valid IdParamDto param) {
        return this.chattingRecordsService.deleteSingle(param.getId());
    }



    /**
     *  修改数据状态
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "修改数据状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "版本号", paramType = "path",  dataType = "String", defaultValue = "v1")
    })
    @PutMapping(value = "verify/chatting/records/p")
    @ApiVersion(1)
    public ResultInfo updateDataStatus(@Valid IdParamDto param ) {
        return this.chattingRecordsService.updateStatus(param.getStatus(), param.getId());
    }

}
