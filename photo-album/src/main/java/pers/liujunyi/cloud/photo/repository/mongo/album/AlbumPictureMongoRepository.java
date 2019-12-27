package pers.liujunyi.cloud.photo.repository.mongo.album;

import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.photo.entity.album.AlbumPicture;

import java.util.List;

/***
 * 文件名称: AlbumPictureMongoRepository.java
 * 文件描述: 相册图片 Mongo Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月26日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumPictureMongoRepository extends BaseMongoRepository<AlbumPicture, Long> {

    /**
     * 根据相册ID 获取相册图片信息
     * @param albumId
     * @return
     */
    List<AlbumPicture> findByAlbumId(Long albumId);


    /**
     * 根据相册ID 获取相册图片信息
     * @param albumIds
     * @return
     */
    List<AlbumPicture> findByAlbumIdIn(List<Long> albumIds);


    /**
     * 根据相册ID 删除相册图片
     * @param albumId
     * @return
     */
    int deleteByAlbumId(Long albumId);
}
