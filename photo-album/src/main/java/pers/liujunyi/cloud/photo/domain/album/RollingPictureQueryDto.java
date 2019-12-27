package pers.liujunyi.cloud.photo.domain.album;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.jpa.annotation.QueryCondition;
import pers.liujunyi.cloud.common.query.mongo.BaseMongoQuery;


/***
 * 文件名称: RollingPictureQueryDto.java
 * 文件描述: 轮播图片 query dto
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年09月16日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class RollingPictureQueryDto extends BaseMongoQuery {

    private static final long serialVersionUID = 3424740041227057L;


    /** 页面代码 例如： 1001：首页 等 */
    @ApiModelProperty(value = "所属页面")
    @QueryCondition()
    private String pageCode;

    /** 页面中所在位置 */
    @ApiModelProperty(value = "页面位置")
    @QueryCondition()
    private String pagePosition;


    /** 状态  0：已发布（可见）  1：草稿  */
    @ApiModelProperty(value = "状态")
    @QueryCondition()
    private Byte status;
}
