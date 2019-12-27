package pers.liujunyi.cloud.photo.repository.jpa.album;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.photo.entity.album.Album;

/***
 * 文件名称: AlbumRepository.java
 * 文件描述: 相册管理 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface AlbumRepository  extends BaseJpaRepository<Album, Long> {

    /**
     * 修改状态
     * @param albumStatus  0：已发布（可见）  1：不可见  2：草稿
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query(value = "update album u set u.album_status = ?1, u.update_time = now(), u.data_version = data_version+1 where u.id = ?2 ", nativeQuery = true)
    int setStatusById(Byte albumStatus, Long id);

}
