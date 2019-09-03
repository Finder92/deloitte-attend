package io.renren.modules.attend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.attend.config.RequestConfig;
import io.renren.modules.attend.dao.AttendUserDao;
import io.renren.modules.attend.dto.history.DataDto;
import io.renren.modules.attend.dto.history.HistoryDataDTO;
import io.renren.modules.attend.dto.history.SubjectDto;
import io.renren.modules.attend.dto.message.HeartDto;
import io.renren.modules.attend.entity.AttendUserEntity;
import io.renren.modules.attend.service.FetchFaceHistoryService;
import io.renren.modules.attend.websocket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FetchFaceHistoryServiceImpl
 * @Description TODO
 * @Author Finder zhangzhili92@163.com
 * @Date 2019/8/23 16:57
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FetchFaceHistoryServiceImpl implements FetchFaceHistoryService {
    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    private AttendUserDao attendUserDao;

    @Resource
    WebSocketServer webSocketServer;

    @Override
    public void fetchFaceHistoryProcess(String result) {
        HistoryDataDTO historyDataDTO = JSONObject.parseObject(result, HistoryDataDTO.class);
        List<DataDto> dataDtoList = historyDataDTO.getData();
        if (dataDtoList.size() > 0) {
            log.info("获取" + "{}", dataDtoList.size() + "条识别结果");
            for (DataDto dataDto : dataDtoList) {
                SubjectDto subjectDto = dataDto.getSubject();
                if (subjectDto == null) {
                    log.info("您识别到陌生人");
                    //陌生人只展示识别照片 识别时间 用户类型信息
                    AttendUserEntity attendUserEntity = new AttendUserEntity();
                    attendUserEntity.setAttendUsertype("4");
                    attendUserEntity.setAttendSignstate("2");
                    attendUserEntity.setAttendCreatetime(new Date());
                    attendUserEntity.setAttendPictureUrl(requestConfig.getPictureUrl() +dataDto.getPhoto());
                    attendUserDao.insert(attendUserEntity);
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
                else if (subjectDto != null) {
                    String attendUserName = subjectDto.getName();
                    String subjectType = String.valueOf(subjectDto.getSubject_type());
                    AttendUserEntity attendUserEntity = new AttendUserEntity();
                    attendUserEntity.setAttendUsertype(subjectType);
                    attendUserEntity.setAttendUssname(attendUserName);
                    attendUserEntity.setAttendSignstate("0");
                    attendUserEntity.setAttendCreatetime(new Date());
                    attendUserEntity.setAttendPictureUrl(requestConfig.getPictureUrl() + subjectDto.getPhotos().get(0).getUrl());
                    List<AttendUserEntity> attendUserEntityList = attendUserDao.queryByAttendUserName(attendUserEntity.getAttendUssname());
                    if (attendUserEntityList.size() == 0 || null == attendUserEntityList) {
                        log.info("attendUserName:{},subjectType:{}", attendUserName + "在当前系统中不存在,即将创建新的记录,当前时间为:" + new Date(), subjectType);
                        attendUserDao.insert(attendUserEntity);
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
                }
            }
        } else {
            //此次没有获取到任何识别记录
            //log.info("此次没有获取到任何识别记录:" + LocalDateTime.now());
        }
    }
}
