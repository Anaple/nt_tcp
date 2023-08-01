package umstj.nt_tcp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umstj.nt_tcp.component.TCPServer;
import umstj.nt_tcp.service.VehicleControlService;

@Service
public class BaseControlImpl implements VehicleControlService {

    private TCPServer tcpServer;

    @Autowired
    public BaseControlImpl(TCPServer tcpServer) {
        this.tcpServer = tcpServer;

    }
    @Override
    public void sendNav(int node, int vehicleNumber) {


        tcpServer.sendMessageBaseMethods(vehicleNumber,"");

    }

    @Override
    public void sendRun(int vehicleNumber) {
        tcpServer.sendMessageBaseMethods(vehicleNumber,"");

    }

    @Override
    public void sendStop(int vehicleNumber) {
        tcpServer.sendMessageBaseMethods(vehicleNumber,"");

    }
}
