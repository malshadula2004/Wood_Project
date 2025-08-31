package org.example.ggg.model;

import javafx.beans.property.*;

public class MonthlyProfitLossDto {
    private final StringProperty id;
    private final StringProperty month;
    private final IntegerProperty orderAmount;
    private final DoubleProperty payment;
    private final DoubleProperty profitLoss;
    private final DoubleProperty finalAmount;

    public MonthlyProfitLossDto(String id, String month, int orderAmount, double payment, double profitLoss, double finalAmount) {
        this.id = new SimpleStringProperty(id);
        this.month = new SimpleStringProperty(month);
        this.orderAmount = new SimpleIntegerProperty(orderAmount);
        this.payment = new SimpleDoubleProperty(payment);
        this.profitLoss = new SimpleDoubleProperty(profitLoss);
        this.finalAmount = new SimpleDoubleProperty(finalAmount);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public int getOrderAmount() {
        return orderAmount.get();
    }

    public IntegerProperty orderAmountProperty() {
        return orderAmount;
    }

    public double getPayment() {
        return payment.get();
    }

    public DoubleProperty paymentProperty() {
        return payment;
    }

    public double getProfitLoss() {
        return profitLoss.get();
    }

    public DoubleProperty profitLossProperty() {
        return profitLoss;
    }

    public double getFinalAmount() {
        return finalAmount.get();
    }

    public DoubleProperty finalAmountProperty() {
        return finalAmount;
    }
}
