package umstj.nt_tcp.entry.DTO;

import com.alibaba.fastjson.annotation.JSONField;

import java.net.Socket;

public class CarConnectDTO {

    @JSONField(alternateNames = "car_id")
    private int vehicleNumber;
    private String type;

    private String ipAddress;

    private Socket clientSocket;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public CarConnectDTO(int vehicleNumber, String type) {
        vehicleNumber = vehicleNumber;
        this.type = type;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int car_id) {
        this.vehicleNumber = car_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CarConnectDTO{" +
                "car_id=" + vehicleNumber +
                ", type='" + type + '\'' +
                '}';
    }

}
