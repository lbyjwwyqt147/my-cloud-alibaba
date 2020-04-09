package pers.liujunyi.cloud.photo.repository.mongo.album;

import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.photo.entity.album.Album;

import java.util.List;

/***
 * 文件名称: AlbumMongoRepository.java
 * 文件描述: 相册 Mongo Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月26日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumMongoRepository extends BaseMongoRepository<Album, Long> {

    /**
     *
     * @param status
     * @return
     */
    List<Album> findByAlbumStatusOrderByIdDesc(Byte status);
}
