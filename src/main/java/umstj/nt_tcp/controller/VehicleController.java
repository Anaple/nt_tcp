package umstj.nt_tcp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umstj.nt_tcp.component.TCPServer;
import umstj.nt_tcp.entry.DTO.CarConnectDTO;
import umstj.nt_tcp.entry.VO.VehicleInfoVO;
import umstj.nt_tcp.service.VehicleControlService;
import umstj.nt_tcp.service.VehicleQueryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleQueryService vehicleQueryService;

    private VehicleControlService vehicleControlService;

    private TCPServer tcpServer;

    // Constructor to inject the VehicleQueryService instance
    public VehicleController(VehicleQueryService vehicleQueryService ,VehicleControlService vehicleControlService ,TCPServer tcpServer) {
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleControlService = vehicleControlService;
        this.tcpServer = tcpServer;
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleInfoVO>> getAllCars() {
        CarConnectDTO[] allCars = vehicleQueryService.getAllCars().toArray(new CarConnectDTO[0]);
        List<VehicleInfoVO> vehicleInfoVOList = Arrays.stream(allCars)
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleInfoVOList);
    }
    @GetMapping("/send")
    public String sendMsg(@RequestParam("id") int id, @RequestParam("msg") String msg ){
        tcpServer.sendMessageBaseMethods(id, msg);

        return msg;
    }

    private VehicleInfoVO convertToVO(CarConnectDTO vehicleInfo) {
        return new VehicleInfoVO(vehicleInfo.getIpAddress(), vehicleInfo.getVehicleNumber());
    }
}
