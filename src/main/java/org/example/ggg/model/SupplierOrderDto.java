package org.example.ggg.model;

public class SupplierOrderDto {
    private String supplierOrderId;
    private String date;
    private String supplierId;

    public SupplierOrderDto() {
    }

    public SupplierOrderDto(String supplierOrderId, String date, String supplierId) {
        this.supplierOrderId = supplierOrderId;
        this.date = date;
        this.supplierId = supplierId;
    }

    public String getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(String supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "SupplierOrderDto{" +
                "supplierOrderId='" + supplierOrderId + '\'' +
                ", date='" + date + '\'' +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}
