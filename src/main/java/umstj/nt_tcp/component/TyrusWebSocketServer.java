package umstj.nt_tcp.component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@ServerEndpoint("/ws") // WebSocket服务端的URL路径
@Component
public class TyrusWebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(TyrusWebSocketServer.class);
    /**
     * session
     */

    private ArrayList<Session> sessionArrayList = new ArrayList<>();
    /**
     * 线程池
     */
    private ScheduledThreadPoolExecutor threadPool;


    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened: " + session.getId());
        sessionArrayList.add(session);

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);

        // 可以在此处添加处理收到消息的逻辑，然后向客户端发送响应
        // 例如：session.getBasicRemote().sendText("Response: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket connection closed: " + session.getId());
    }
}
