package io.renren.modules.attend.task;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.attend.config.RequestConfig;
import io.renren.modules.attend.service.FetchFaceHistoryService;
import io.renren.modules.attend.service.HttpClientService;
import io.renren.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DeloitteVisitorTask
 * @Description TODO
 * @Author Finder zhangzhili92@163.com
 * @Date 2019/8/23 16:44
 **/
@Slf4j
@Component("deloitteVisitorTask")
public class DeloitteVisitorTask implements ITask {
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private RequestConfig requestConfig;

    @Autowired
    private FetchFaceHistoryService fetchFaceHistoryService;

    private int loginState = 1;

    @Override
    public void run(String params) {
        while (loginState != 0) {
            log.info("执行登录...");
            Map<String, String> paramsMap = new ConcurrentHashMap<>();
            paramsMap.put("username", requestConfig.getUsername());
            paramsMap.put("password", requestConfig.getPassword());
            String result = httpClientService.doPost(requestConfig.getLoginUrl(), paramsMap);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            result = jsonObject.get("code").toString();
            log.info("result:{登录返回状态码:=}" + result);
            log.info("result:{}", result);
            loginState = Integer.valueOf(result);
        }
        try {
            //如果系统时间比网络时间快的话 我要去减去快的时间
            //如果系统时间比网络时间慢的话  就会出现延时
            Map<String, String> paramsMap = new ConcurrentHashMap<>();
            //定时每次取当前时间的十分钟前
            //将取到的时间戳转为秒(取到的为毫秒)
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, -3);
            long start = calendar.getTimeInMillis()/1000;
            paramsMap.put("start", String.valueOf(start));
            paramsMap.put("end", Long.toString(new Date().getTime()/1000-1));
            //paramsMap.put("user_role", String.valueOf(0));
            String result = httpClientService.doGet(requestConfig.getDetailUrl(), paramsMap);

            //获取历史记录
            fetchFaceHistoryService.fetchFaceHistoryProcess(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
