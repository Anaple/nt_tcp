package umstj.nt_tcp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umstj.nt_tcp.component.TCPServer;
import umstj.nt_tcp.entry.DTO.CarConnectDTO;
import umstj.nt_tcp.service.VehicleQueryService;

import java.util.List;
@Service
public class BaseQueryImpl implements VehicleQueryService {
    private TCPServer tcpServer;

    @Autowired
    public BaseQueryImpl(TCPServer tcpServer) {
        this.tcpServer = tcpServer;

    }

    // Implementing the getAllCars method from VehicleQueryService
    @Override
    public List<CarConnectDTO> getAllCars() {
        return tcpServer.getAllCarInfo();
    }


}
