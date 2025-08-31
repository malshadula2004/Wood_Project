package org.example.ggg.model;

public class WoodInventoryDto {
    private String id; // Primary key
    private String supplierOrderId; // Supplier order ID
    private String woodId; // Wood ID
    private String woodName; // Wood name
    private String woodLength; // Length of the wood
    private String price; // Price of the wood

    // Constructor
    public WoodInventoryDto(String id, String supplierOrderId, String woodId, String woodName, String woodLength, String price) {
        this.id = id;
        this.supplierOrderId = supplierOrderId;
        this.woodId = woodId;
        this.woodName = woodName;
        this.woodLength = woodLength;
        this.price = price;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public String getWoodId() {
        return woodId;
    }

    public void setWoodId(String woodId) {
        this.woodId = woodId;
    }

    public String getWoodName() {
        return woodName;
    }

    public void setWoodName(String woodName) {
        this.woodName = woodName;
    }

    public String getWoodLength() {
        return woodLength;
    }

    public void setWoodLength(String woodLength) {
        this.woodLength = woodLength;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // toString method for debugging or logging
    @Override
    public String toString() {
        return "WoodInventoryDto{" +
                "id='" + id + '\'' +
                ", supplierOrderId='" + supplierOrderId + '\'' +
                ", woodId='" + woodId + '\'' +
                ", woodName='" + woodName + '\'' +
                ", woodLength='" + woodLength + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
