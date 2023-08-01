package umstj.nt_tcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umstj.nt_tcp.component.TCPServer;
import umstj.nt_tcp.component.UPnpServer;

@RestController
public class MessageController {

    private TCPServer tcpServer;



    @Autowired
    public MessageController(TCPServer tcpServer) {
        this.tcpServer = tcpServer;

    }

    @GetMapping("/")
    public String getDefaultMessage(){
        return "HTTP OK";
    }




}
