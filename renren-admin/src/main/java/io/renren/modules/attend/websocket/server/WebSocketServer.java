package io.renren.modules.attend.websocket.server;

import com.alibaba.fastjson.JSON;
import io.renren.modules.attend.dto.message.HeartDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSocketServer
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/6 15:06
 **/
@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            try {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        HeartDto heartDto = new HeartDto();
                        heartDto.setType("2");
                        heartDto.setDescription("heart-attack");
                        String message = JSON.toJSONString(heartDto);
                        try {
                            sendMessage(session,message);
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                };
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(timerTask,0,2000);
            } catch (Exception e) {
                //e.printStackTrace();
            }

        } catch (Exception e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        for (WebSocketServer webSocketServer : webSocketSet) {
            try {
                webSocketServer.sendMessage("服务器的心跳回应-HeartBeat" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        for (WebSocketServer webSocketServer : webSocketSet) {
            webSocketServer.session.getBasicRemote().sendText(message);
        }
    }

    public void sendMessage(Session session,String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


}
