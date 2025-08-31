package org.example.ggg.model;

public class PaymentsDto {
    private String paymentId;
    private String purchaseOrdersId;
    private String customerId;
    private String paymentDate;
    private String amount;
    private String paymentMethod;

    public PaymentsDto() {}

    public PaymentsDto(String paymentId, String purchaseOrdersId, String customerId, String paymentDate, String amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.purchaseOrdersId = purchaseOrdersId;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getPurchaseOrdersId() { return purchaseOrdersId; }
    public void setPurchaseOrdersId(String purchaseOrdersId) { this.purchaseOrdersId = purchaseOrdersId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public String toString() {
        return "PaymentsDto{" +
                "paymentId='" + paymentId + '\'' +
                ", purchaseOrdersId='" + purchaseOrdersId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", amount='" + amount + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
