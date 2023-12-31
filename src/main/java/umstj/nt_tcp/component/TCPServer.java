package umstj.nt_tcp.component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import umstj.nt_tcp.entry.DTO.CarConnectDTO;

@Component
public class TCPServer {

    private final List<ClientHandler> connectedClients = new ArrayList<>();
    public final Logger logger = LoggerFactory.getLogger(TCPServer.class);

    private List<CarConnectDTO> carConnectList = new ArrayList<>();

    @Value("${tcp.server.port}")
    private int port;
    private ServerSocket serverSocket;

    @PostConstruct
    public void start(){

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                logger.info("TCP Server started. Listening on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    connectedClients.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String socketRead(byte[] data, int bytesRead) {
        // 包头[FF AA]
        // 长度 第三 第四位
        Map<String, Object> maps = new HashMap<>();
        if (data[0] == (byte) 0xFF && data[1] == (byte) 0xAA) {
            // 获取长度
            int len = ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);

            // 截取数据
            byte[] jsonByte = Arrays.copyOfRange(data, 4, 4 + len);

            // 转换为JsonMap
            String jsonStr = new String(jsonByte);
            return jsonStr;
        }
        if (bytesRead == -1){
            return new String();
        }
        return new String(data, 0, bytesRead);


    }

    public static byte[] createByte(String jsonStr, boolean isU3d) {
        byte[] jsonByte = jsonStr.getBytes(StandardCharsets.UTF_8);
        int jsonByteLen = jsonByte.length;
        byte[] data = new byte[jsonByteLen + 4];
        data[0] = (byte) 0xFF;
        data[1] = isU3d ? (byte) 0xBB : (byte) 0xAA;

        if (jsonByteLen <= 255) {
            data[2] = 0x00;
            data[3] = (byte) jsonByteLen;
        } else {
            String hexString = Integer.toHexString(jsonByteLen);
            if (hexString.length() == 4) {
                data[2] = (byte) Integer.parseInt(hexString.substring(0, 2), 16);
                data[3] = (byte) Integer.parseInt(hexString.substring(2), 16);
            } else {
                data[2] = (byte) Integer.parseInt(hexString.substring(0, 1), 16);
                data[3] = (byte) Integer.parseInt(hexString.substring(1), 16);
            }
        }

        System.arraycopy(jsonByte, 0, data, 4, jsonByteLen);
        return data;
    }

    public void sendMessages(Socket socket,String message)  {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(createByte(message,false));
        }catch (IOException  e){
            e.printStackTrace();
        }
    }

    public List<CarConnectDTO> getAllCarInfo(){
        return carConnectList;
    }


    public void broadcastBaseMethods(String message){
        for (CarConnectDTO carConnectDTO : carConnectList){
            sendMessages(carConnectDTO.getClientSocket(),message);
        }

    }

    public void sendMessageBaseMethods(int vehicleNumber ,String message){
        for (CarConnectDTO carConnectDTO : carConnectList){
            if (carConnectDTO.getVehicleNumber() == vehicleNumber){
                sendMessages(carConnectDTO.getClientSocket(),message);
            }
        }

    }


    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream in;
        private DataOutputStream out;
        private String clientId;

        Logger logger = LoggerFactory.getLogger(ClientHandler.class);

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.clientId = clientSocket.getInetAddress().getHostAddress();
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(clientSocket.getInputStream());
                byte[] buffer = new byte[1024];
                int bytesRead;
                bytesRead = in.read(buffer);
                String message_rev = socketRead(buffer,bytesRead);
                logger.info("Received message from client: " + message_rev);
                boolean isNew = true;
                CarConnectDTO carConnectDTO = new CarConnectDTO(0,"0");
                try {
                    carConnectDTO =  JSON.parseObject(message_rev, CarConnectDTO.class);
                    for (CarConnectDTO car:carConnectList) {
                        if(car.getVehicleNumber() == carConnectDTO.getVehicleNumber()&&car.getIpAddress() == carConnectDTO.getIpAddress()){
                            isNew = false;
                            break;
                        }
                    }
                    if (isNew){
                        carConnectDTO.setIpAddress(this.clientId);
                        carConnectDTO.setClientSocket(this.clientSocket);
                        carConnectList.add(carConnectDTO);
                        logger.info(carConnectList.toString());
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                while ((bytesRead) != -1) {
                    // Process the received data here
                    // ...
                }
                logger.info("disconnect "+clientSocket.getInetAddress().getHostAddress());
                carConnectList.remove(carConnectDTO);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
