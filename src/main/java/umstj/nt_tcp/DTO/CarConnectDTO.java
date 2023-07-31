package umstj.nt_tcp.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import umstj.nt_tcp.component.TCPServer;

import java.net.Socket;

public class CarConnectDTO {

    @JSONField(alternateNames = "car_id")
    private int carId;
    private String type;

    private String host;

    private Socket clientSocket;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public CarConnectDTO(int carId, String type) {
        carId = carId;
        this.type = type;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int car_id) {
        this.carId = car_id;
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
                "car_id=" + carId +
                ", type='" + type + '\'' +
                '}';
    }

}
