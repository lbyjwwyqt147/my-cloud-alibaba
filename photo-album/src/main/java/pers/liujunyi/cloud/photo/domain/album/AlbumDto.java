package pers.liujunyi.cloud.photo.domain.album;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import pers.liujunyi.cloud.common.dto.BaseDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/***
 * 文件名称: AlbumDto.java
 * 文件描述: 相册
 * 公 司:
 * 内容摘要:
 *
 * 完成日期:2019年03月11日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumDto extends BaseDto {

    private static final long serialVersionUID = -1197543425348740669L;

    /** 相册编号 */
    private String albumNumber;

    /** 相册名称 */
    @ApiModelProperty(value = "相册名称")
    @NotBlank(message = "名称必须填写")
    @Length(min = 0, max = 32, message = "名称 最多可以输入32个字符")
    private String albumName;

    /** 相册分类 例如：旅拍、活动 等 */
    @ApiModelProperty(value = "相册分类")
    @NotBlank(message = "分类必须填写")
    @Length(min = 0, max = 10, message = "分类 最多可以输入10个字符")
    private String albumClassify;

    /** 相册状态  0：已发布（可见）  1：不可见  2：草稿 */
    @NotNull(message = "状态 必须填写")
    @Min(value = 0, message = "状态 必须是数字类型")
    private Byte albumStatus;

    /** 相册描述 */
    @ApiModelProperty(value = "相册描述")
    @Length(min = 0, max = 255, message = "描述 最多可以输入255个字符")
    private String albumDescription;

    /** 背景音乐地址 */
    @ApiModelProperty(value = "背景音乐")
    @Length(min = 0, max = 255, message = "背景音乐 最多可以输入255个字符")
    private String albumMusicAddress;

    /** 版权设置 */
    @ApiModelProperty(value = "版权设置")
    @Length(min = 0, max = 10, message = "版权设置 最多可以输入10个字符")
    private String albumCopyright;

    /** 作者(摄影师)  */
    @ApiModelProperty(value = "作者(摄影师)")
    @Length(min = 0, max = 32, message = "作者(摄影师) 最多可以输入32个字符")
    private String albumPhotographyAuthor;


    /** 拍摄地点 */
    @ApiModelProperty(value = "拍摄地点")
    @NotBlank(message = "拍摄地点")
    @Length(min = 0, max = 32, message = "拍摄地点 最多可以输入32个字符")
    private String spotForPhotography;

    /** 排序值 数字从小到大排列 */
    @ApiModelProperty(value = "排序值")
    @Min(value = 0, message = "排序值 必须是数字类型")
    private Byte albumPriority;

    @ApiModelProperty(value = "图片")
    @NotBlank(message = "缺少上传图片")
    private String pictures;

    /** 拍摄时间 */
    @ApiModelProperty(value = "拍摄时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date shootingsDate;

    /** 封面图 */
    private String surfacePlot;

    /** 封面图ID */
    private Long surfacePlotId;
}
