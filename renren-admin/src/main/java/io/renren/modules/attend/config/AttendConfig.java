package io.renren.modules.attend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AttendConfig
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/2 14:23
 **/
@Data
@Configuration
public class AttendConfig {
    //websocket主机地址
    @Value("${websocket.wsUrl}")
    private String wsUrl;

    //视频流地址
    @Value("${websocket.rtspUrl}")
    private String rtspUrl;
}
