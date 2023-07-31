package umstj.nt_tcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umstj.nt_tcp.component.TCPServer;
import umstj.nt_tcp.component.UPnpServer;

@RestController
public class MessageController {

    private TCPServer tcpServer;

    private UPnpServer uPnpServer;

    @Autowired
    public MessageController(TCPServer tcpServer ,UPnpServer uPnpServer) {
        this.tcpServer = tcpServer;
        this.uPnpServer = uPnpServer;
    }

    @GetMapping("/")
    public String getDefaultMessage(){
        return "HTTP OK";
    }

    @GetMapping("/send")
    public String sendMessageToClients(@RequestParam("id") int id,@RequestParam("node") int node) {
        // Send the message to all connected clients
        tcpServer.broadcastNavNode(node,id);

        return "Message sent to clients: navid ";
    }


}
