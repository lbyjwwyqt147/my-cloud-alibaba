package pers.liujunyi.cloud.chat.service.chitchat.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.liujunyi.cloud.chat.domain.chitchat.ChattingRecordsDto;
import pers.liujunyi.cloud.chat.entity.chitchat.ChattingRecords;
import pers.liujunyi.cloud.chat.repository.jpa.chitchat.ChattingRecordsRepository;
import pers.liujunyi.cloud.chat.service.chitchat.ChattingRecordsService;
import pers.liujunyi.cloud.common.repository.jpa.BaseJpaRepository;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;
import pers.liujunyi.cloud.common.service.impl.BaseJpaMongoServiceImpl;
import pers.liujunyi.cloud.common.util.DozerBeanMapperUtil;
import pers.liujunyi.cloud.common.util.UserContext;

import java.util.Date;

/***
 * 文件名称: ChattingRecordsServiceImpl.java
 * 文件描述: 聊天纪录 Service Impl
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年03月04日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class ChattingRecordsServiceImpl extends BaseJpaMongoServiceImpl<ChattingRecords, Long> implements ChattingRecordsService {

    @Autowired
    private ChattingRecordsRepository chattingRecordsRepository;
    @Autowired
    private RestTemplate restTemplate;


    public ChattingRecordsServiceImpl(BaseJpaRepository<ChattingRecords, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public ResultInfo saveRecord(ChattingRecordsDto record) {
        ChattingRecords chattingRecord = DozerBeanMapperUtil.copyProperties(record, ChattingRecords.class);
        boolean add = chattingRecord.getId() == null ? true : false;
        if (add) {
            chattingRecord.setDataVersion(1L);
        } else {
            chattingRecord.setUpdateTime(new Date());
            chattingRecord.setUpdateUserId(UserContext.currentUserId());
            chattingRecord.setDataVersion(record.getDataVersion() + 1);
        }
      /*  ChattingRecords saveObject = this.chattingRecordsRepository.save(chattingRecord);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("logContent", 18);
        String url = "http://localhost:18087/ignore/logs/records/s";
        int i = 0/ 0;
        String result = restTemplate.postForObject(url, hashMap, String.class);
        System.out.println(" == " + result);
        if (saveObject == null || saveObject.getId() == null) {
            return ResultUtil.fail();
        }*/
        return ResultUtil.success();
    }

    @Override
    public ResultInfo updateStatus(Byte status, Long id) {
        //int count = this.chattingRecordsRepository.setStatusById(status, id);

        return ResultUtil.fail();
    }


    @Override
    public ResultInfo deleteSingle(Long id) {
      //  ChattingRecords album = this.chattingRecordsRepository.findById(id);

        return ResultUtil.success();
    }


}
