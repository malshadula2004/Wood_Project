package org.example.ggg.dto;

public class PurchaseOrdersDto {
    private String purchaseOrderId;
    private String itemId;
    private String customerId;
    private String orderDate;
    private String status;
    private String totalAmount;
    private String quantity;

    public PurchaseOrdersDto() {
    }

    public PurchaseOrdersDto(String purchaseOrderId, String itemId, String customerId, String orderDate, String status, String totalAmount, String quantity) {
        this.purchaseOrderId = purchaseOrderId;
        this.itemId = itemId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchaseOrdersDto{" +
                "purchaseOrderId='" + purchaseOrderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
