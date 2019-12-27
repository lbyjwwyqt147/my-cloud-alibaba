package pers.liujunyi.cloud.photo.domain.album;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.liujunyi.cloud.photo.entity.album.AlbumPicture;

import java.util.List;

/**
 * 相册信息 vo
 * @author ljy
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumVo extends AlbumDto {

    private static final long serialVersionUID = 7791998095818472113L;
    /** 相册照片数量 */
    @ApiModelProperty(value = "相册照片数量")
    private Integer total;
    /** 相册图片 */
    @ApiModelProperty(value = "相册图片")
    private List<AlbumPicture> albumPictureData;
    /** 类型名称 */
    @ApiModelProperty(value = "类型名称")
    private String albumClassifyText;
    /** 封面 */
    private String cover;
}
