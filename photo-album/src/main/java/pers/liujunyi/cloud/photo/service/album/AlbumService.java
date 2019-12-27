package pers.liujunyi.cloud.photo.service.album;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseJpaMongoService;
import pers.liujunyi.cloud.photo.domain.album.AlbumDto;
import pers.liujunyi.cloud.photo.entity.album.Album;

/***
 * 文件名称: AlbumService.java
 * 文件描述:  相册 Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumService extends BaseJpaMongoService<Album, Long> {

    /**
     * 保存数据
     * @param record
     * @return
     */
    ResultInfo saveRecord(AlbumDto record);

    /**
     * 修改状态
     * @param status   0：已发布（可见）  1：不可见  2：草稿
     * @param id
     * @return
     */
    ResultInfo updateStatus(Byte status, Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultInfo deleteSingle(Long id);

    /**
     * 同步数据到es中
     * @return
     */
    ResultInfo syncDataToMongo();


    /**
     * 根据图片ID 删除图片
     * @param pictureId  图片ID
     * @return
     */
    ResultInfo deleteAlbumPictureById(Long pictureId);
}
