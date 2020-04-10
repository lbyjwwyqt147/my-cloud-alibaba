package pers.liujunyi.cloud.chat.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/***
 * 文件名称: ActivitiesJobHandler.java
 * 文件描述:  最新活动任务调度
 * 公 司:
 * 内容摘要:
 * 其他说明: 任务Handler示例（Bean模式）
 *  开发步骤：
 *   1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 *   2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 *   3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *   4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * 完成日期:2019年10月08日
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@JobHandler(value="newActivitiesJobHandler")
@Component
public class ActivitiesJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("XXL-JOB, 执行检查最新活动是否过期.");
        String msg = "";
        XxlJobLogger.log(" >>>>>> " + msg);
        return SUCCESS;
    }



}
