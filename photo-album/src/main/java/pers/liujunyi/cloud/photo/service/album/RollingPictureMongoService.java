package pers.liujunyi.cloud.photo.service.album;

import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.service.BaseMongoService;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureQueryDto;
import pers.liujunyi.cloud.photo.entity.album.RollingPicture;

import java.util.List;


/***
 * 文件名称: RollingPictureMongoService.java
 * 文件描述: 轮播图 Mongo Service
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月27日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface RollingPictureMongoService extends BaseMongoService<RollingPicture, Long> {

    /**
     * 分页列表
     * @param query
     * @return
     */
    ResultInfo findPageGird(RollingPictureQueryDto query);


    /**
     *
     * @param pageCode
     * @param pagePosition
     * @return
     */
    ResultInfo findByPageCodeAndPagePositionOrderByPriorityAsc(String pageCode, String pagePosition);

    /**
     *
     * @param pageCode
     * @param pagePosition
     * @return
     */
    List<RollingPicture> findByPageCodeAndPagePosition(String pageCode, String pagePosition);

    /**
     * 根据页面和位置删除
     * @param pageCode  页面
     * @param pagePosition 位置
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
