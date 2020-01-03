package pers.liujunyi.cloud.photo.repository.jpa.album;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.photo.entity.album.RollingPicture;


/***
 * 文件名称: RollingPictureRepository.java
 * 文件描述: 滚动图片管理 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface RollingPictureRepository extends BaseJpaRepository<RollingPicture, Long> {

    /**
     * 修改状态
     * @param status  0：展示 1：不展示
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    @Modifying(clearAutomatically = true)
    @Query("update RollingPicture u set u.status = ?1 where u.id = ?2")
    int setStatusByIds(Byte status, Long id);


    /**
     * 根据页面和位置删除数据
     * @param pageCode
     * @param pagePosition
     * @return
     */
    int deleteByPageCodeAndPagePosition(String pageCode, String pagePosition);

    /**
     * 根据业务ID删除数据
     * @param businessId  业务数据ID
     * @return
     */
    int deleteByBusinessId(Long businessId);
}