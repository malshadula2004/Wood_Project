package org.example.ggg.model;

import javafx.scene.control.Button;

public class woodOrderDto {
    private String woodId;
    private String name;
    private String quantity;
    private String price;
    private String total;
    private Button removeButton;

    public woodOrderDto(String woodId, String name, String quantity, String price, String total, Button removeButton) {
        this.woodId = woodId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.removeButton = removeButton;
    }

    // Getters and setters
    public String getWoodId() {
        return woodId;
    }

    public void setWoodId(String woodId) {
        this.woodId = woodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }
}
