package org.example.ggg.model;

public class VehicleDetailsDto {
    private String vehicleId;
    private String vehicleNumber;
    private String type;
    private String capacity;
    private String status;
    private String userId;

    public VehicleDetailsDto() {
    }

    public VehicleDetailsDto(String vehicleId, String vehicleNumber, String type, String capacity, String status, String userId) {
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.userId = userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VehicleDetailsDto{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", type='" + type + '\'' +
                ", capacity='" + capacity + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
