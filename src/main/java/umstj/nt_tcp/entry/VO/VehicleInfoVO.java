package umstj.nt_tcp.entry.VO;



public class VehicleInfoVO {
    private String ipAddress;
    private int vehicleNumber;

    public VehicleInfoVO(String ipAddress, int vehicleNumber) {
        this.ipAddress = ipAddress;
        this.vehicleNumber = vehicleNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    // Getters and setters
}
