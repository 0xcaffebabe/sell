package wang.ismy.sell.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author MY
 * @date 2020/2/4 15:33
 */
@Component
@ServerEndpoint("/ws")
@Slf4j
public class WebSocket {

    private static List<Session> sessionList = new CopyOnWriteArrayList<>();

    @OnOpen
    public void open(Session session){
        sessionList.add(session);
        log.info("ws 连接 :{}",session);
    }

    @OnClose
    public void close(Session session){
        sessionList.remove(session);
        log.info("ws关闭 {}",session);
    }

    @OnMessage
    public void message(Session session,String message){
        log.info("{} 发送消息 :{}",session,message);

    }

    public void sendMessage(String msg){
        for (Session s : sessionList) {
            try {
                s.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
