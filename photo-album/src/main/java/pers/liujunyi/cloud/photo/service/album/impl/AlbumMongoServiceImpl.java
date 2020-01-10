package pers.liujunyi.cloud.photo.service.album.impl;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.centre.api.service.dict.DictService;
import pers.liujunyi.cloud.common.repository.mongo.BaseMongoRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.photo.domain.album.AlbumQueryDto;
import pers.liujunyi.cloud.photo.domain.album.AlbumVo;
import pers.liujunyi.cloud.photo.entity.album.Album;
import pers.liujunyi.cloud.photo.entity.album.AlbumPicture;
import pers.liujunyi.cloud.photo.repository.mongo.album.AlbumMongoRepository;
import pers.liujunyi.cloud.photo.repository.mongo.album.AlbumPictureMongoRepository;
import pers.liujunyi.cloud.photo.service.album.AlbumMongoService;
import pers.liujunyi.cloud.photo.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * 文件名称: AlbumMongoServiceImpl.java
 * 文件描述: 相册管理 Mongo Service impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class AlbumMongoServiceImpl extends BaseMongoServiceImpl<Album, Long> implements AlbumMongoService {

    @Autowired
    private AlbumMongoRepository albumMongoRepository;
    @Autowired
    private AlbumPictureMongoRepository albumPictureMongoRepository;
    /** org.apache.dubbo.config.annotation.Reference */
    @Reference
    DictService dictService;

    public AlbumMongoServiceImpl(BaseMongoRepository<Album, Long> baseMongoRepository) {
        super(baseMongoRepository);
    }

    @Override
    public ResultInfo findPageGird(AlbumQueryDto query) {
        Sort sort = Sort.by(Sort.Direction.ASC, "albumPriority");
        Pageable pageable = query.toPageable(sort);
        // 查询条件
        Query searchQuery = query.toSpecPageable(pageable);
        // 查询总记录条数
        long totalElements = this.mongoDbTemplate.count(searchQuery, Album.class);
        // 查询数据
        List<Album> searchPageResults =  this.mongoDbTemplate.find(searchQuery, Album.class);
        List<AlbumVo> resultDataList = new CopyOnWriteArrayList<>();
        if (!CollectionUtils.isEmpty(searchPageResults)) {
            searchPageResults.stream().forEach(item -> {
                AlbumVo albumVo = DozerBeanMapperUtil.copyProperties(item, AlbumVo.class);
                //获取相册图片信息
                List<AlbumPicture> albumPictures = this.albumPictureMongoRepository.findByAlbumId(item.getId());
                albumVo.setTotal(albumPictures.size());
                albumVo.setAlbumPictureData(albumPictures);
                albumVo.setStatus(item.getAlbumStatus());
                albumVo.setCover(item.getSurfacePlot());
                resultDataList.add(albumVo);
            });
        }
        ResultInfo result = ResultUtil.success(resultDataList);
        result.setTotal(totalElements);
        return  result;
    }

    @Override
    public ResultInfo details(Long id) {
        return ResultUtil.success(this.detailsById(id));
    }

    @Override
    public AlbumVo detailsById(Long id) {
        AlbumVo albumVo = null;
        Album album = this.findById(id);
        if (album != null) {
            albumVo = DozerBeanMapperUtil.copyProperties(album, AlbumVo.class);
            //获取相册图片信息
            List<AlbumPicture> albumPictures = this.albumPictureMongoRepository.findByAlbumId(albumVo.getId());
            albumVo.setTotal(albumPictures.size());
            albumVo.setPictures(JSON.toJSONString(albumPictures));
        }
        return albumVo;
    }

    @Override
    public Album findById(Long id) {
        Optional<Album> optional  = this.albumMongoRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public ResultInfo findAlbumPictureByAlbumId(Long albumId) {
        List<AlbumPicture> albumPictures = this.albumPictureMongoRepository.findByAlbumId(albumId);
        return ResultUtil.success(albumPictures);
    }

    @Override
    public ResultInfo albumComboBox() {
        List<Album> searchList = this.albumMongoRepository.findByAlbumStatusOrderByIdAsc(Constant.ENABLE_STATUS);
        List<Map<String, String>> resultData = new CopyOnWriteArrayList<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("id", "");
        map.put("text", "--请选择--");
        resultData.add(map);
        if (!CollectionUtils.isEmpty(searchList)) {
            searchList.stream().forEach(item -> {
                Map<String, String> albumMap = new HashMap<>();
                albumMap.put("id", item.getId().toString());
                albumMap.put("text", item.getAlbumName());
                resultData.add(albumMap);
            });
        }
        return ResultUtil.success(resultData);
    }
}
