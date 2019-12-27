package pers.liujunyi.cloud.photo.repository.jpa.album;

import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.photo.entity.album.AlbumPicture;

import java.util.List;

/***
 * 文件名称: AlbumPictureRepository.java
 * 文件描述: 相册图片 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumPictureRepository extends BaseJpaRepository<AlbumPicture, Long> {


    /**
     * 根据相册ID 删除相册图片
     * @param albumId
     * @return
     */
    int deleteByAlbumId(Long albumId);

    /**
     * 根据文件Id 获取数据
     * @param pictureIds
     * @return
     */
    List<AlbumPicture> findByPictureIdIn(List<Long> pictureIds);
}
