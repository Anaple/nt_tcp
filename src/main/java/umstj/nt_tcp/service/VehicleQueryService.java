package umstj.nt_tcp.service;

import umstj.nt_tcp.entry.DTO.CarConnectDTO;

import java.util.List;


public interface VehicleQueryService {
    List<CarConnectDTO> getAllCars();

}
