package pers.liujunyi.cloud.photo.service.album;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseMongoService;
import pers.liujunyi.cloud.photo.domain.album.AlbumQueryDto;
import pers.liujunyi.cloud.photo.domain.album.AlbumVo;
import pers.liujunyi.cloud.photo.entity.album.Album;


/***
 * 文件名称: AlbumMongoService.java
 * 文件描述: 相册 Mongo Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumMongoService extends BaseMongoService<Album, Long> {

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(AlbumQueryDto query);

    /**
     * 根据ID 获取详细数据(包含相册图片信息)
     * @param id
     * @return
     */
    ResultInfo details(Long id);

    /**
     * 根据ID 获取详细数据(不包含相册图片信息)
     * @param id
     * @return
     */
    AlbumVo detailsById(Long id);


    /**
     * 相册图片信息
     * @param albumId
     * @return
     */
    ResultInfo findAlbumPictureByAlbumId(Long albumId);

    /**
     * 相册下拉框信息
     * @return
     */
    ResultInfo albumComboBox();
}