package io.renren.modules.attend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketServerConfig
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/6 15:03
 **/
@Configuration
public class WebSocketServerConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
