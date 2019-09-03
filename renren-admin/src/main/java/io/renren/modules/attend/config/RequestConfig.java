package io.renren.modules.attend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RequestConfig
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 18:37
 **/
@Configuration
@Data
public class RequestConfig {
    @Value("${megvii.loginUrl}")
    private String loginUrl;
    @Value("${megvii.username}")
    private String username;
    @Value("${megvii.password}")
    private String password;
    @Value("${megvii.sessionDomain}")
    private String sessionDomain;
    @Value("${megvii.detailUrl}")
    private String detailUrl;
    @Value("${megvii.pictureUrl}")
    private String pictureUrl;



}
