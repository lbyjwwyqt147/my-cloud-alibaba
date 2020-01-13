package pers.liujunyi.cloud.photo.repository.mongo.album;

import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.photo.entity.album.RollingPicture;

import java.util.List;

/***
 * 文件名称: RollingPictureMongoRepository.java
 * 文件描述: 轮播图片 Mongo Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月26日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface RollingPictureMongoRepository extends BaseMongoRepository<RollingPicture, Long> {

    /***
     *
     * @param pageCode
     * @param pagePosition
     * @return
     */
    List<RollingPicture> findByPageCodeAndPagePositionAndStatusOrderByPriorityAsc(String pageCode, String pagePosition, Byte status);

    /***
     *
     * @param pageCode
     * @param pagePosition
     * @return
     */
    List<RollingPicture> findByPageCodeAndPagePositionOrderByPriorityAsc(String pageCode, String pagePosition);


    /**
     * 根据页面和位置删除
     * @param pageCode  页面
     * @param pagePosition 位置
     * @return
     */
    int deleteByPageCodeAndPagePosition(String pageCode, String pagePosition);

}
