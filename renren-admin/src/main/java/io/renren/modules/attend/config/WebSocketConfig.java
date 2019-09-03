package io.renren.modules.attend.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.attend.dto.message.HeartDto;
import io.renren.modules.attend.dto.subject.SubjectDTO;
import io.renren.modules.attend.dto.unrecognized.UnrecognizedDto;
import io.renren.modules.attend.dto.video.VideoDTO;
import io.renren.modules.attend.entity.AttendUserEntity;
import io.renren.modules.attend.service.AttendUserService;
import io.renren.modules.attend.service.HttpClientService;
import io.renren.modules.attend.websocket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketConfig
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/30 16:22
 */

@Slf4j
//@Component
public class WebSocketConfig {
    @Autowired
    private AttendConfig attendConfig;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private AttendUserService attendUserService;

    @Autowired
    private RequestConfig requestConfig;

    @Resource
    WebSocketServer webSocketServer;

    //@Bean
    public WebSocketClient webSocketClient() {
        try {
            //主机地址
            String wsUrl = attendConfig.getWsUrl();
            //视频流地址
            String rtspUrl = attendConfig.getRtspUrl();
            //websocket连接
            String ws = wsUrl + "?url=" + URLEncoder.encode(rtspUrl, "utf-8");
            WebSocketClient webSocketClient = new WebSocketClient(new URI(ws), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    log.info("[websocket] 连接成功");
                    loginMegvii();
                    log.info("登录[megvii]成功");
                }

                @Override
                public void onMessage(String message) {
                    //log.info("原始消息"+message);
                    VideoDTO videoDTO = JSONObject.parseObject(message, VideoDTO.class);
                    String typeValue = videoDTO.getType();
                    if (typeValue.equals("recognized")) {
                        log.info("[websocket] 收到消息={}", message);
                        try {
                            String attendUserName = videoDTO.getPerson().getName();
                            String resultSubject = httpClientService.doGet("http://192.168.1.50/subject/" + videoDTO.getPerson().getId());
                            SubjectDTO subjectDTO = JSONObject.parseObject(resultSubject, SubjectDTO.class);
                            int subjectType = videoDTO.getPerson().getSubject_type();
                            System.out.println("s::::"+subjectType);
                            AttendUserEntity attendUserEntity = new AttendUserEntity();
                            attendUserEntity.setAttendUsertype(String.valueOf(videoDTO.getPerson().getSubject_type()));
                            attendUserEntity.setAttendUssname(attendUserName);
                            attendUserEntity.setAttendSignstate("0");
                            attendUserEntity.setAttendCreatetime(new Date());
                            attendUserEntity.setAttendPictureUrl("http://192.168.1.50"+subjectDTO.getData().getPhotos().get(0).getUrl());
                            //根据用户名查询,如果已存在,略过保存
                            List<AttendUserEntity> attendUserEntityList = attendUserService.queryByAttendUserName(attendUserName);
                            if (attendUserEntityList.size() == 0 || null == attendUserEntityList) {
                                log.info("attendUserName:{},subjectType:{}", attendUserName + "在当前系统中不存在,即将创建新的记录,当前时间为:" + new Date(), subjectType);
                                attendUserService.save(attendUserEntity);
                                HeartDto heartDto = new HeartDto();
                                heartDto.setType("1");
                                heartDto.setDescription("保存新员工");
                                String messageToPage = JSON.toJSONString(heartDto);
                                try {
                                    webSocketServer.sendMessage(messageToPage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            log.info("attendUserName:{},subjectType:{}", attendUserName + "已在系统中存在,当前时间为:" + new Date(), subjectType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(typeValue.equals("unrecognized")){
                        //陌生人只展示识别照片 识别时间 用户类型信息
                        UnrecognizedDto unrecognizedDto = JSONObject.parseObject(message, UnrecognizedDto.class);
                        String base64Img = unrecognizedDto.getData().getFace().getImage();
                        AttendUserEntity attendUserEntity = new AttendUserEntity();
                        attendUserEntity.setAttendUsertype("4");
                        attendUserEntity.setAttendSignstate("2");
                        attendUserEntity.setAttendCreatetime(new Date());
                        attendUserEntity.setAttendPictureUrl("data:image/jpeg;base64," + base64Img);
                        attendUserService.save(attendUserEntity);
                        HeartDto heartDto = new HeartDto();
                        heartDto.setType("1");
                        heartDto.setDescription("保存新员工");
                        String messageToPage = JSON.toJSONString(heartDto);
                        try {
                            webSocketServer.sendMessage(messageToPage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        heartDto.setType("0");
                        heartDto.setDescription("需要新录入");
                        messageToPage = JSON.toJSONString(heartDto);
                        try {
                            webSocketServer.sendMessage(messageToPage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("[websocket] 退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}", ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @return
     * @Author Finder
     * @Description //TODO 为了获取cookie
     * @Date 16:09 2019/7/29
     * @Param
     */
    public String loginMegvii() {
        Map<String, String> params = new ConcurrentHashMap<>();
        params.put("username", requestConfig.getUsername());
        params.put("password", requestConfig.getPassword());
        String result = httpClientService.doPost(requestConfig.getLoginUrl(), params);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        result = jsonObject.get("code").toString();
        log.info("result:{登录返回状态码:=}" + result);
        log.info("result:{}", result);
        return result;
    }


}
