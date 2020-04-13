package pers.liujunyi.cloud.gateway.loadbanlance;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/***
 * 文件名称: HealthExamination
 * 文件描述: 健康检查
 * 公 司:
 * 内容摘要:
 * 其他说明:
 * 完成日期:2020/4/13 17:11
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Log4j2
public class HealthExamination implements IPing {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean isAlive(Server server) {
        String url = "http://" + server.getId() + "/heath";
        try {
            ResponseEntity<String> heath = restTemplate.getForEntity(url, String.class);
            if (heath.getStatusCode() == HttpStatus.OK) {
                log.info("健康(心跳)检查 ping " + url + " success and response is " + heath.getBody());
                return true;
            }
            log.info("健康(心跳)检查 ping " + url + " error and response is " + heath.getBody());
            return false;
        } catch (Exception e) {
            log.info("健康(心跳)检查  ping " + url + " failed");
            return false;
        }
    }

}
