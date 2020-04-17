package pers.liujunyi.cloud.logs.repository.jpa.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.liujunyi.cloud.logs.entity.log.OperateLogRecords;

/***
 * 文件名称: OperateLogRecordsRepository.java
 * 文件描述: 操作日志记录 Repository
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2019年02月21日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Repository
public interface OperateLogRecordsRepository extends JpaRepository<OperateLogRecords, Long> {


}
