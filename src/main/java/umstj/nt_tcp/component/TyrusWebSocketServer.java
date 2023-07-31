package umstj.nt_tcp.component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket") // WebSocket服务端的URL路径
public class TyrusWebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened: " + session.getId());
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
