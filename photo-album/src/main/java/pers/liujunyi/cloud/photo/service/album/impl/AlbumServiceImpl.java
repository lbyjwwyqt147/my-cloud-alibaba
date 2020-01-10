package pers.liujunyi.cloud.photo.service.album.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseJpaMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.common.util.RemoteCloudUtil;
import pers.liujunyi.cloud.common.util.UserContext;
import pers.liujunyi.cloud.photo.domain.album.AlbumDto;
import pers.liujunyi.cloud.photo.entity.album.Album;
import pers.liujunyi.cloud.photo.entity.album.AlbumPicture;
import pers.liujunyi.cloud.photo.repository.jpa.album.AlbumPictureRepository;
import pers.liujunyi.cloud.photo.repository.jpa.album.AlbumRepository;
import pers.liujunyi.cloud.photo.repository.mongo.album.AlbumPictureMongoRepository;
import pers.liujunyi.cloud.photo.service.album.AlbumMongoService;
import pers.liujunyi.cloud.photo.service.album.AlbumService;
import pers.liujunyi.cloud.photo.service.album.RollingPictureService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/***
 * 文件名称: AlbumServiceImpl.java
 * 文件描述: 相册管理 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class AlbumServiceImpl extends BaseJpaMongoServiceImpl<Album, Long> implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumMongoService albumMongoService;
    @Autowired
    private AlbumPictureRepository albumPictureRepository;
    @Autowired
    private AlbumPictureMongoRepository albumPictureMongoRepository;
    @Autowired
    private RemoteCloudUtil remoteCloudUtil;
    @Autowired
    private RollingPictureService rollingPictureService;

    public AlbumServiceImpl(BaseJpaRepository<Album, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public ResultInfo saveRecord(AlbumDto record) {
        Album album = DozerBeanMapperUtil.copyProperties(record, Album.class);
        boolean add = album.getId() == null ? true : false;
        if (add) {
            album.setAlbumNumber(String.valueOf(System.currentTimeMillis()));
            record.setDataVersion(1L);
        } else {
            record.setUpdateTime(new Date());
            record.setUpdateUserId(UserContext.currentUserId());
            record.setDataVersion(record.getDataVersion() + 1);
        }
        if (record.getAlbumPriority() == null) {
            album.setAlbumPriority((byte) 10);
        }
        Album saveObject = this.albumRepository.save(album);
        if (saveObject == null || saveObject.getId() == null) {
            return ResultUtil.fail();
        }
        List<AlbumPicture> albumPictureList = new LinkedList<>();
        JSONArray jsonArray = JSON.parseArray(record.getPictures());
        byte i = 1;
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            AlbumPicture albumPicture = new AlbumPicture();
            albumPicture.setAlbumId(saveObject.getId());
            albumPicture.setPictureId(jsonObject.getLong("id"));
            albumPicture.setPictureLocation(jsonObject.getString("fileCallAddress"));
            albumPicture.setPictureName(jsonObject.getString("fileName"));
            albumPicture.setPictureCategory(jsonObject.getByte("fileCategory"));
            albumPicture.setPictureSize(jsonObject.getLong("fileSize"));
            albumPicture.setPictureType(jsonObject.getString("fileSuffix"));
            albumPicture.setPicturePriority(i);
            i++;
            albumPictureList.add(albumPicture);
        }
        List<Long> pictureIds = albumPictureList.stream().map(AlbumPicture::getPictureId).collect(Collectors.toList());
        List<AlbumPicture> pictureList = this.albumPictureRepository.findByPictureIdIn(pictureIds);
        if (!CollectionUtils.isEmpty(pictureList)) {
            Map<Long, List<AlbumPicture>> pictureMap = pictureList.stream().collect(Collectors.groupingBy(AlbumPicture::getPictureId));
            Iterator<AlbumPicture> albumPictureIterator = albumPictureList.iterator();
            while (albumPictureIterator.hasNext()) {
                AlbumPicture albumPicture = albumPictureIterator.next();
                if (pictureMap.get(albumPicture.getPictureId()) != null) {
                    albumPictureIterator.remove();
                }
            }
        }
        if (albumPictureList.size() > 0) {
            List<AlbumPicture> albumPictures =  this.albumPictureRepository.saveAll(albumPictureList);
            this.albumPictureMongoRepository.saveAll(albumPictures);
        }
        this.albumMongoService.save(saveObject);
        return ResultUtil.success(saveObject.getId());
    }

    @Override
    public ResultInfo updateStatus(Byte status, Long id) {
        int count = this.albumRepository.setStatusById(status, id);
        if (count > 0) {
            if (status.byteValue() == 1) {
                // 删除轮播图数据
                this.rollingPictureService.deleteByBusinessId(id);
            }
            Map<Long, Map<String, Object>> sourceMap = new ConcurrentHashMap<>();
            Map<String, Object> docDataMap = new HashMap<>();
            docDataMap.put("albumStatus", status);
            docDataMap.put("updateTime", System.currentTimeMillis());
            sourceMap.put(id, docDataMap);
            super.updateMongoDataByIds(sourceMap);
            return ResultUtil.success();
        }
        return ResultUtil.fail();
    }


    @Override
    public ResultInfo deleteSingle(Long id) {
        Album album = this.albumMongoService.findById(id);
        if (album != null) {
            List<Long> uploadFileIds = new LinkedList<>();
            // 封面图
            Long cover = album.getSurfacePlotId();
            if (cover != null) {
                uploadFileIds.add(cover);
            }
            this.albumRepository.deleteById(id);
            this.rollingPictureService.deleteByBusinessId(id);
            List<AlbumPicture> pictureList = this.albumPictureMongoRepository.findByAlbumId(id);
            if (!CollectionUtils.isEmpty(pictureList)) {
                List<Long> fileIds = pictureList.stream().map(AlbumPicture::getId).collect(Collectors.toList());
                this.albumPictureRepository.deleteByIdIn(fileIds);
                this.albumPictureMongoRepository.deleteByIdIn(fileIds);
                uploadFileIds.addAll(pictureList.stream().map(AlbumPicture::getPictureId).collect(Collectors.toList()));
            }
            this.albumMongoService.deleteById(id);
            // 删除服务器上的文件
            this.remoteCloudUtil.deleteOssFileByIds(StringUtils.join(uploadFileIds, ","));
        }
        return ResultUtil.success();
    }

    @Override
    public ResultInfo syncDataToMongo() {
        super.syncDataMongoDb();
        // 同步相册照片信息
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<AlbumPicture> pictureList = this.albumPictureRepository.findAll(sort);
        if (!CollectionUtils.isEmpty(pictureList)) {
            this.albumPictureMongoRepository.deleteAll();
            // 限制条数
            int pointsDataLimit = 1000;
            int size = pictureList.size();
            //判断是否有必要分批
            if(pointsDataLimit < size){
                //分批数
                int part = size/pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    //1000条
                    List<AlbumPicture> partList = new LinkedList<>(pictureList.subList(0, pointsDataLimit));
                    //剔除
                    pictureList.subList(0, pointsDataLimit).clear();
                    this.albumPictureMongoRepository.saveAll(partList);
                }
                //表示最后剩下的数据
                if (!CollectionUtils.isEmpty(pictureList)) {
                    this.albumPictureMongoRepository.saveAll(pictureList);
                }
            } else {
                this.albumPictureMongoRepository.saveAll(pictureList);
            }
        } else {
            this.albumPictureMongoRepository.deleteAll();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultInfo deleteAlbumPictureById(Long pictureId) {
        AlbumPicture albumPicture = null;
        Optional<AlbumPicture> optional   = this.albumPictureMongoRepository.findById(pictureId);
        if (optional.isPresent()) {
            albumPicture = optional.get();
        }
        this.albumPictureRepository.deleteById(pictureId);
        if (albumPicture != null) {
            this.albumPictureMongoRepository.deleteById(pictureId);
            // 删除服务器上的文件
            this.remoteCloudUtil.deleteOssFileByIds(albumPicture.getPictureId().toString());
        }
        return ResultUtil.success();
    }
}
