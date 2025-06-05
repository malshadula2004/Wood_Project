package org.example.ggg.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MonthlyProfitOrLossDto {

    private final StringProperty id;
    private final StringProperty orderTotal;
    private final StringProperty paymentTotal;
    private final StringProperty lossOrProfitAmount;
    private final StringProperty finalStatus;

    public MonthlyProfitOrLossDto(String id, String orderTotal, String paymentTotal, String lossOrProfitAmount, String finalStatus) {
        this.id = new SimpleStringProperty(id);
        this.orderTotal = new SimpleStringProperty(orderTotal);
        this.paymentTotal = new SimpleStringProperty(paymentTotal);
        this.lossOrProfitAmount = new SimpleStringProperty(lossOrProfitAmount);
        this.finalStatus = new SimpleStringProperty(finalStatus);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getOrderTotal() {
        return orderTotal.get();
    }

    public StringProperty orderTotalProperty() {
        return orderTotal;
    }

    public String getPaymentTotal() {
        return paymentTotal.get();
    }

    public StringProperty paymentTotalProperty() {
        return paymentTotal;
    }

    public String getLossOrProfitAmount() {
        return lossOrProfitAmount.get();
    }

    public StringProperty lossOrProfitAmountProperty() {
        return lossOrProfitAmount;
    }

    public String getFinalStatus() {
        return finalStatus.get();
    }

    public StringProperty finalStatusProperty() {
        return finalStatus;
    }

    public String toString() {
        return "MonthlyProfitOrLossDto{" +
                "id='" + id.get() + '\'' +
                ", orderTotal='" + orderTotal.get() + '\'' +
                ", paymentTotal='" + paymentTotal.get() + '\'' +
                ", lossOrProfitAmount='" + lossOrProfitAmount.get() + '\'' +
                ", finalStatus='" + finalStatus.get() + '\'' +
                '}';
    }
}
