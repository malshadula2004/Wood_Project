package org.example.ggg.model;

public class DeliveryDto {
    private String deliveryId;
    private String purchaseOrdersId;
    private String deliveryDate;
    private String employeeId;
    private String deliveryStatus;

    // Default constructor
    public DeliveryDto() {
    }

    // Parameterized constructor
    public DeliveryDto(String deliveryId, String purchaseOrdersId, String deliveryDate, String employeeId, String deliveryStatus) {
        this.deliveryId = deliveryId;
        this.purchaseOrdersId = purchaseOrdersId;
        this.deliveryDate = deliveryDate;
        this.employeeId = employeeId;
        this.deliveryStatus = deliveryStatus;
    }

    // Getters and setters
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(String purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // Override toString method for better representation
    @Override
    public String toString() {
        return "DeliveryDto{" +
                "deliveryId='" + deliveryId + '\'' +
                ", purchaseOrdersId='" + purchaseOrdersId + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
