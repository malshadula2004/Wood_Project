package org.example.ggg.dto;

public class WoodInventoryDto {
    private String supplierOrderId;
    private String woodId;
    private double woodLength;

    public WoodInventoryDto(String supplierOrderId, String woodId, double woodLength) {
        this.supplierOrderId = supplierOrderId;
        this.woodId = woodId;
        this.woodLength = woodLength;
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

    public double getWoodLength() {
        return woodLength;
    }

    public void setWoodLength(double woodLength) {
        this.woodLength = woodLength;
    }

    @Override
    public String toString() {
        return "WoodInventoryDto{" +
                "supplierOrderId='" + supplierOrderId + '\'' +
                ", woodId='" + woodId + '\'' +
                ", woodLength=" + woodLength +
                '}';
    }
}
