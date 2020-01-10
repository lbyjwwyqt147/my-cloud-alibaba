package pers.liujunyi.cloud.photo.service.album.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseJpaMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.common.util.RemoteCloudUtil;
import pers.liujunyi.cloud.photo.domain.album.RollingPictureDto;
import pers.liujunyi.cloud.photo.entity.album.RollingPicture;
import pers.liujunyi.cloud.photo.repository.jpa.album.RollingPictureRepository;
import pers.liujunyi.cloud.photo.service.album.RollingPictureMongoService;
import pers.liujunyi.cloud.photo.service.album.RollingPictureService;
import pers.liujunyi.cloud.photo.util.Constant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/***
 * 文件名称: RollingPictureServiceImpl.java
 * 文件描述: 轮播图 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class RollingPictureServiceImpl extends BaseJpaMongoServiceImpl<RollingPicture, Long> implements RollingPictureService {

    @Autowired
    private RollingPictureRepository rollingPictureRepository;
    @Autowired
    private RollingPictureMongoService rollingPictureMongoService;
    @Autowired
    private RemoteCloudUtil remoteCloudUtil;

    public RollingPictureServiceImpl(BaseJpaRepository<RollingPicture, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public ResultInfo saveRecord(RollingPictureDto record) {
        if (record.getPriority() == null) {
            record.setPriority((byte) 10);
        }
        List<RollingPicture> rollingPictures = new LinkedList<>();
        JSONArray jsonArray = JSON.parseArray(record.getPictures());
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            RollingPicture rollingPicture = DozerBeanMapperUtil.copyProperties(record, RollingPicture.class);
            rollingPicture.setPictureId(jsonObject.getLong("id"));
            rollingPicture.setPictureLocation(jsonObject.getString("fileCallAddress"));
            rollingPicture.setPictureCategory(jsonObject.getByte("fileCategory"));
            rollingPicture.setStatus(Constant.ENABLE_STATUS);
            rollingPictures.add(rollingPicture);
        }
        List<RollingPicture> save = this.rollingPictureRepository.saveAll(rollingPictures);
        if (save.size() > 0) {
            this.rollingPictureMongoService.saveAll(rollingPictures);
            return ResultUtil.success();
        } else {
            return ResultUtil.fail();
        }

    }

    @Override
    public ResultInfo updateStatus(Byte status, Long id) {
        int count = this.rollingPictureRepository.setStatusByIds(status, id);
        if (count > 0) {
            Map<Long, Map<String, Object>> sourceMap = new ConcurrentHashMap<>();
            Map<String, Object> docDataMap = new HashMap<>();
            docDataMap.put("status", status);
            docDataMap.put("updateTime", System.currentTimeMillis());
            sourceMap.put(id, docDataMap);
            super.updateMongoDataByIds(sourceMap);
            return ResultUtil.success();
        }
        return ResultUtil.fail();
    }

    @Override
    public ResultInfo deleteBatch(List<Long> ids) {
        List<RollingPicture> rollingPictureList = this.rollingPictureMongoService.findAllByIdIn(ids);
        long count = this.rollingPictureRepository.deleteByIdIn(ids);
        if (count > 0) {
            this.rollingPictureMongoService.deleteAllByIdIn(ids);
            if (!CollectionUtils.isEmpty(rollingPictureList)) {
                List<Long> uploadFileIds = rollingPictureList.stream().map(RollingPicture::getPictureId).collect(Collectors.toList());
                // 删除服务器上的文件
                this.remoteCloudUtil.deleteOssFileByIds(StringUtils.join(uploadFileIds, ","));
            }
            return ResultUtil.success();
        }
        return ResultUtil.fail();
    }

    @Override
    public ResultInfo deleteByPageCodeAndPagePosition(String pageCode, String pagePosition) {
        List<RollingPicture> rollingPictureList = this.rollingPictureMongoService.findByPageCodeAndPagePosition(pageCode, pagePosition);
        int count = this.rollingPictureRepository.deleteByPageCodeAndPagePosition(pageCode, pagePosition);
        if (count > 0) {
            this.rollingPictureMongoService.deleteByPageCodeAndPagePosition(pageCode, pagePosition);
            if (!CollectionUtils.isEmpty(rollingPictureList)) {
                List<Long> uploadFileIds = rollingPictureList.stream().map(RollingPicture::getPictureId).collect(Collectors.toList());
                // 删除服务器上的文件
                this.remoteCloudUtil.deleteOssFileByIds(StringUtils.join(uploadFileIds, ","));
            }
            return ResultUtil.success();
        }
        return ResultUtil.fail();
    }

    @Override
    public ResultInfo syncDataToMongo() {
        return ResultUtil.success();
    }

    @Override
    public int deleteByBusinessId(Long businessId) {
        int count = this.rollingPictureMongoService.deleteByBusinessId(businessId);
        count = this.rollingPictureRepository.deleteByBusinessId(businessId);
        return count;
    }


}
