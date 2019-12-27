package pers.liujunyi.cloud.photo.domain.album;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.common.query.jpa.annotation.QueryCondition;
import pers.liujunyi.cloud.common.query.mongo.BaseMongoQuery;


/***
 * 文件名称: AlbumQueryDto.java
 * 文件描述: 相册 query dto
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月26日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumQueryDto extends BaseMongoQuery {
    private static final long serialVersionUID = 5223845406864513819L;


    /**  相册分类 例如：旅拍、活动 等 */
    @ApiModelProperty(value = "相册分类")
    @QueryCondition()
    private String albumClassify;

    /** 相册状态  0：已发布（可见）  1：不可见  2：草稿 */
    @ApiModelProperty(value = "状态")
    @QueryCondition()
    private Byte albumStatus;

}
