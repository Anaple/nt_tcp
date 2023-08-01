package umstj.nt_tcp.service;

public interface VehicleControlService {
    void sendNav(int node,int vehicleNumber);
    void sendRun(int vehicleNumber);
    void sendStop(int vehicleNumber);
}
